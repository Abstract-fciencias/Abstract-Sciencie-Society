/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety;

import com.mycompany.abstractsciencesociety.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.abstractsciencesociety.model.Categoria;
import com.mycompany.abstractsciencesociety.model.Usuario;
import com.mycompany.abstractsciencesociety.model.Comentario;
import com.mycompany.abstractsciencesociety.model.Tema;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author edervs
 */
public class TemaJpaController implements Serializable {

    public TemaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tema tema) {
        if (tema.getComentarioCollection() == null) {
            tema.setComentarioCollection(new ArrayList<Comentario>());
        }
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
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : tema.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getIdcomentario());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            tema.setComentarioCollection(attachedComentarioCollection);
            em.persist(tema);
            if (idcategoria != null) {
                idcategoria.getTemaCollection().add(tema);
                idcategoria = em.merge(idcategoria);
            }
            if (idusuario != null) {
                idusuario.getTemaCollection().add(tema);
                idusuario = em.merge(idusuario);
            }
            for (Comentario comentarioCollectionComentario : tema.getComentarioCollection()) {
                Tema oldIdtemaOfComentarioCollectionComentario = comentarioCollectionComentario.getIdtema();
                comentarioCollectionComentario.setIdtema(tema);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldIdtemaOfComentarioCollectionComentario != null) {
                    oldIdtemaOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldIdtemaOfComentarioCollectionComentario = em.merge(oldIdtemaOfComentarioCollectionComentario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

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
            Collection<Comentario> comentarioCollectionOld = persistentTema.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = tema.getComentarioCollection();
            if (idcategoriaNew != null) {
                idcategoriaNew = em.getReference(idcategoriaNew.getClass(), idcategoriaNew.getIdcategoria());
                tema.setIdcategoria(idcategoriaNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                tema.setIdusuario(idusuarioNew);
            }
            Collection<Comentario> attachedComentarioCollectionNew = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionNewComentarioToAttach : comentarioCollectionNew) {
                comentarioCollectionNewComentarioToAttach = em.getReference(comentarioCollectionNewComentarioToAttach.getClass(), comentarioCollectionNewComentarioToAttach.getIdcomentario());
                attachedComentarioCollectionNew.add(comentarioCollectionNewComentarioToAttach);
            }
            comentarioCollectionNew = attachedComentarioCollectionNew;
            tema.setComentarioCollection(comentarioCollectionNew);
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
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    comentarioCollectionOldComentario.setIdtema(null);
                    comentarioCollectionOldComentario = em.merge(comentarioCollectionOldComentario);
                }
            }
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    Tema oldIdtemaOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getIdtema();
                    comentarioCollectionNewComentario.setIdtema(tema);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldIdtemaOfComentarioCollectionNewComentario != null && !oldIdtemaOfComentarioCollectionNewComentario.equals(tema)) {
                        oldIdtemaOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldIdtemaOfComentarioCollectionNewComentario = em.merge(oldIdtemaOfComentarioCollectionNewComentario);
                    }
                }
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tema tema;
            try {
                tema = em.getReference(Tema.class, id);
                tema.getIdtema();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tema with id " + id + " no longer exists.", enfe);
            }
            Categoria idcategoria = tema.getIdcategoria();
            if (idcategoria != null) {
                idcategoria.getTemaCollection().remove(tema);
                idcategoria = em.merge(idcategoria);
            }
            Usuario idusuario = tema.getIdusuario();
            if (idusuario != null) {
                idusuario.getTemaCollection().remove(tema);
                idusuario = em.merge(idusuario);
            }
            Collection<Comentario> comentarioCollection = tema.getComentarioCollection();
            for (Comentario comentarioCollectionComentario : comentarioCollection) {
                comentarioCollectionComentario.setIdtema(null);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
            }
            em.remove(tema);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tema> findTemaEntities() {
        return findTemaEntities(true, -1, -1);
    }

    public List<Tema> findTemaEntities(int maxResults, int firstResult) {
        return findTemaEntities(false, maxResults, firstResult);
    }

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

    public Tema findTema(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tema.class, id);
        } finally {
            em.close();
        }
    }

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
