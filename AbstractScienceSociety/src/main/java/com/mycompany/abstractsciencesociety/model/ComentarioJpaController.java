/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.model;

import com.mycompany.abstractsciencesociety.exceptions.NonexistentEntityException;
import com.mycompany.abstractsciencesociety.model.Comentario;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.mycompany.abstractsciencesociety.model.Tema;
import com.mycompany.abstractsciencesociety.model.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author edervs
 */
public class ComentarioJpaController implements Serializable {

    public ComentarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comentario comentario) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tema idtema = comentario.getIdtema();
            if (idtema != null) {
                idtema = em.getReference(idtema.getClass(), idtema.getIdtema());
                comentario.setIdtema(idtema);
            }
            Usuario idusuario = comentario.getIdusuario();
            if (idusuario != null) {
                idusuario = em.getReference(idusuario.getClass(), idusuario.getIdusuario());
                comentario.setIdusuario(idusuario);
            }
            em.persist(comentario);
            if (idtema != null) {
                idtema.getComentarioCollection().add(comentario);
                idtema = em.merge(idtema);
            }
            if (idusuario != null) {
                idusuario.getComentarioCollection().add(comentario);
                idusuario = em.merge(idusuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comentario comentario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comentario persistentComentario = em.find(Comentario.class, comentario.getIdcomentario());
            Tema idtemaOld = persistentComentario.getIdtema();
            Tema idtemaNew = comentario.getIdtema();
            Usuario idusuarioOld = persistentComentario.getIdusuario();
            Usuario idusuarioNew = comentario.getIdusuario();
            if (idtemaNew != null) {
                idtemaNew = em.getReference(idtemaNew.getClass(), idtemaNew.getIdtema());
                comentario.setIdtema(idtemaNew);
            }
            if (idusuarioNew != null) {
                idusuarioNew = em.getReference(idusuarioNew.getClass(), idusuarioNew.getIdusuario());
                comentario.setIdusuario(idusuarioNew);
            }
            comentario = em.merge(comentario);
            if (idtemaOld != null && !idtemaOld.equals(idtemaNew)) {
                idtemaOld.getComentarioCollection().remove(comentario);
                idtemaOld = em.merge(idtemaOld);
            }
            if (idtemaNew != null && !idtemaNew.equals(idtemaOld)) {
                idtemaNew.getComentarioCollection().add(comentario);
                idtemaNew = em.merge(idtemaNew);
            }
            if (idusuarioOld != null && !idusuarioOld.equals(idusuarioNew)) {
                idusuarioOld.getComentarioCollection().remove(comentario);
                idusuarioOld = em.merge(idusuarioOld);
            }
            if (idusuarioNew != null && !idusuarioNew.equals(idusuarioOld)) {
                idusuarioNew.getComentarioCollection().add(comentario);
                idusuarioNew = em.merge(idusuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comentario.getIdcomentario();
                if (findComentario(id) == null) {
                    throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.");
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
            Comentario comentario;
            try {
                comentario = em.getReference(Comentario.class, id);
                comentario.getIdcomentario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentario with id " + id + " no longer exists.", enfe);
            }
            Tema idtema = comentario.getIdtema();
            if (idtema != null) {
                idtema.getComentarioCollection().remove(comentario);
                idtema = em.merge(idtema);
            }
            Usuario idusuario = comentario.getIdusuario();
            if (idusuario != null) {
                idusuario.getComentarioCollection().remove(comentario);
                idusuario = em.merge(idusuario);
            }
            em.remove(comentario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comentario> findComentarioEntities() {
        return findComentarioEntities(true, -1, -1);
    }

    public List<Comentario> findComentarioEntities(int maxResults, int firstResult) {
        return findComentarioEntities(false, maxResults, firstResult);
    }

    private List<Comentario> findComentarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comentario.class));
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

    public Comentario findComentario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comentario.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comentario> rt = cq.from(Comentario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    /**
     * findComentarios.
     * @param idTema
     * @return usuario
     */
    public List<Comentario> findComentarios(String idTema) {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Comentario.findByIdtema")
                .setParameter(1, Integer.valueOf(idTema));
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (List<Comentario>) q.getResultList();
    }
    
}
