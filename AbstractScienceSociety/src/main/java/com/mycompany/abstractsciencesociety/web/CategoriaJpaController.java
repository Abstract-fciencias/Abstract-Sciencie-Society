/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import com.mycompany.abstractsciencesociety.model.Categoria;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.abstractsciencesociety.model.Tema;
import com.mycompany.abstractsciencesociety.web.exceptions.IllegalOrphanException;
import com.mycompany.abstractsciencesociety.web.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aide
 */
public class CategoriaJpaController implements Serializable {

    /**
     * emf.
     */
    private EntityManagerFactory emf;

    /**
     * Constructor.
     * @param emfAux
     */
    public CategoriaJpaController(final EntityManagerFactory emfAux) {
        this.emf = emfAux;
    }

    /**
     * getEntityManager.
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * create.
     * @param categoria
     */
    public void create(Categoria categoria) {
        if (categoria.getTemaCollection() == null) {
            categoria.setTemaCollection(new ArrayList<Tema>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Tema> attachedTemaCollection = new ArrayList<Tema>();
            for (Tema temaCollectionTemaToAttach : categoria.getTemaCollection()) {
                temaCollectionTemaToAttach = em.getReference(temaCollectionTemaToAttach.getClass(), temaCollectionTemaToAttach.getIdtema());
                attachedTemaCollection.add(temaCollectionTemaToAttach);
            }
            categoria.setTemaCollection(attachedTemaCollection);
            em.persist(categoria);
            for (Tema temaCollectionTema : categoria.getTemaCollection()) {
                Categoria oldIdcategoriaOfTemaCollectionTema = temaCollectionTema.getIdcategoria();
                temaCollectionTema.setIdcategoria(categoria);
                temaCollectionTema = em.merge(temaCollectionTema);
                if (oldIdcategoriaOfTemaCollectionTema != null) {
                    oldIdcategoriaOfTemaCollectionTema.getTemaCollection().remove(temaCollectionTema);
                    oldIdcategoriaOfTemaCollectionTema = em.merge(oldIdcategoriaOfTemaCollectionTema);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * edit.
     * @param categoria
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(Categoria categoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getIdcategoria());
            Collection<Tema> temaCollectionOld = persistentCategoria.getTemaCollection();
            Collection<Tema> temaCollectionNew = categoria.getTemaCollection();
            List<String> illegalOrphanMessages = null;
            for (Tema temaCollectionOldTema : temaCollectionOld) {
                if (!temaCollectionNew.contains(temaCollectionOldTema)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tema " + temaCollectionOldTema + " since its idcategoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Tema> attachedTemaCollectionNew = new ArrayList<Tema>();
            for (Tema temaCollectionNewTemaToAttach : temaCollectionNew) {
                temaCollectionNewTemaToAttach = em.getReference(temaCollectionNewTemaToAttach.getClass(), temaCollectionNewTemaToAttach.getIdtema());
                attachedTemaCollectionNew.add(temaCollectionNewTemaToAttach);
            }
            temaCollectionNew = attachedTemaCollectionNew;
            categoria.setTemaCollection(temaCollectionNew);
            categoria = em.merge(categoria);
            for (Tema temaCollectionNewTema : temaCollectionNew) {
                if (!temaCollectionOld.contains(temaCollectionNewTema)) {
                    Categoria oldIdcategoriaOfTemaCollectionNewTema = temaCollectionNewTema.getIdcategoria();
                    temaCollectionNewTema.setIdcategoria(categoria);
                    temaCollectionNewTema = em.merge(temaCollectionNewTema);
                    if (oldIdcategoriaOfTemaCollectionNewTema != null && !oldIdcategoriaOfTemaCollectionNewTema.equals(categoria)) {
                        oldIdcategoriaOfTemaCollectionNewTema.getTemaCollection().remove(temaCollectionNewTema);
                        oldIdcategoriaOfTemaCollectionNewTema = em.merge(oldIdcategoriaOfTemaCollectionNewTema);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = categoria.getIdcategoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * destroy.
     * @param id
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException
     */
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getIdcategoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Tema> temaCollectionOrphanCheck = categoria.getTemaCollection();
            for (Tema temaCollectionOrphanCheckTema : temaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Tema " + temaCollectionOrphanCheckTema + " in its temaCollection field has a non-nullable idcategoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * findCategoriaEntities.
     * @return categorias
     */
    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    /**
     * findCategoriaEntities.
     * @param maxResults
     * @param firstResult
     * @return categorias
     */
    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    /**
     * findCategoriaEntities.
     * @param all
     * @param maxResults
     * @param firstResult
     * @return categorias
     */
    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * findCategoria.
     * @param id
     * @return categoria
     */
    public Categoria findCategoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * getCategoriaCount.
     * @return categoria
     */
    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
