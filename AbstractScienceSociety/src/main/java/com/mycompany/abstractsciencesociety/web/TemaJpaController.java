/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.abstractsciencesociety.model.Categoria;
import com.mycompany.abstractsciencesociety.model.Tema;
import com.mycompany.abstractsciencesociety.model.Usuario;
import com.mycompany.abstractsciencesociety.web.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author aide
 */
public class TemaJpaController implements Serializable {

    /**
     * emf.
     */
    private EntityManagerFactory emf;

    /**
     * TemaJpaController.
     *
     * @param emfAux
     */
    public TemaJpaController(final EntityManagerFactory emfAux) {
        this.emf = emfAux;
    }

    /**
     * getEntityManager.
     *
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * create.
     *
     * @param tema
     */
    public void create(Tema tema) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria idcategoria = tema.getIdcategoria();
            if (idcategoria != null) {
                idcategoria = em.getReference(idcategoria.getClass(), idcategoria.getIdcategoria());
                tema.setIdcategoria(idcategoria);
            }
            Usuario idusuario = tema.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
                tema.setIdusuario(idusuario);
            }
            em.persist(tema);
            if (idcategoria != null) {
                idcategoria.getTemaCollection().add(tema);
                idcategoria = em.merge(idcategoria);
            }
            if (idusuario != null) {
                idusuario.getTemaCollection().add(tema);
                idusuario = em.merge(idusuario);
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
     *
     * @param tema
     * @throws NonexistentEntityException
     * @throws Exception
     */
    public void edit(Tema tema) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tema persistentTema = em.find(Tema.class, tema.getIdtema());
            Categoria idcategoriaOld = persistentTema.getIdcategoria();
            Categoria idcategoriaNew = tema.getIdcategoria();
            Usuario idusuarioOld = persistentTema.getIdusuario();
            Usuario idusuarioNew = tema.getIdusuario();
            if (idcategoriaNew != null) {
                idcategoriaNew = em.getReference(idcategoriaNew.getClass(), idcategoriaNew.getIdcategoria());
                tema.setIdcategoria(idcategoriaNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                tema.setIdusuario(idusuarioNew);
            }
            tema = em.merge(tema);
            if (idcategoriaOld != null && !idcategoriaOld.equals(idcategoriaNew)) {
                idcategoriaOld.getTemaCollection().remove(tema);
                idcategoriaOld = em.merge(idcategoriaOld);
            }
            if (idcategoriaNew != null && !idcategoriaNew.equals(idcategoriaOld)) {
                idcategoriaNew.getTemaCollection().add(tema);
                idcategoriaNew = em.merge(idcategoriaNew);
            }
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getTemaCollection().remove(tema);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getTemaCollection().add(tema);
                idusuarioNew = em.merge(idusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tema.getIdtema();
                if (findTema(id) == null) {
                    throw new NonexistentEntityException("The tema with id " + id + " no longer exists.");
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
     *
     * @param id
     * @throws NonexistentEntityException
     */
    public void destroy(Integer id) throws NonexistentEntityException {
        System.out.println(id);
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tema tema;
            try {
                System.out.println("1");
                tema = em.getReference(Tema.class, id);
                System.out.println("1.1");
                tema.getIdtema();
                System.out.println("2");
            } catch (EntityNotFoundException enfe) {
                System.out.println("3");
                throw new NonexistentEntityException("The tema with id " + id + " no longer exists.", enfe);
            }
            Categoria idcategoria = tema.getIdcategoria();
            if (idcategoria != null) {
                System.out.println("4");
                idcategoria.getTemaCollection().remove(tema);
                idcategoria = em.merge(idcategoria);
            }
            Usuario idusuario = tema.getIdusuario();
            if (idusuario != null) {
                System.out.println("5");
                idusuario.getTemaCollection().remove(tema);
                idusuario = em.merge(idusuario);
            }

            em.remove(tema);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * findTemaEntities.
     *
     * @return
     */
    public List<Tema> findTemaEntities() {
        return findTemaEntities(true, -1, -1);
    }

    /**
     * findTemaEntities.
     *
     * @param maxResults
     * @param firstResult
     * @return
     */
    public List<Tema> findTemaEntities(int maxResults, int firstResult) {
        return findTemaEntities(false, maxResults, firstResult);
    }

    /**
     * findTemaEntities.
     *
     * @param all
     * @param maxResults
     * @param firstResult
     * @return
     */
    private List<Tema> findTemaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tema.class));
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
     * findTema.
     *
     * @param id
     * @return
     */
    public Tema findTema(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tema.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * getTemaCount.
     *
     * @return tema count.
     */
    public int getTemaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tema> rt = cq.from(Tema.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
