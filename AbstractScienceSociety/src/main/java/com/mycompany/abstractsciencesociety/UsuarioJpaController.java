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
import com.mycompany.abstractsciencesociety.model.Tema;
import java.util.ArrayList;
import java.util.Collection;
import com.mycompany.abstractsciencesociety.model.Comentario;
import com.mycompany.abstractsciencesociety.model.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author edervs
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getTemaCollection() == null) {
            usuario.setTemaCollection(new ArrayList<Tema>());
        }
        if (usuario.getComentarioCollection() == null) {
            usuario.setComentarioCollection(new ArrayList<Comentario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Tema> attachedTemaCollection = new ArrayList<Tema>();
            for (Tema temaCollectionTemaToAttach : usuario.getTemaCollection()) {
                temaCollectionTemaToAttach = em.getReference(temaCollectionTemaToAttach.getClass(), temaCollectionTemaToAttach.getIdtema());
                attachedTemaCollection.add(temaCollectionTemaToAttach);
            }
            usuario.setTemaCollection(attachedTemaCollection);
            Collection<Comentario> attachedComentarioCollection = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionComentarioToAttach : usuario.getComentarioCollection()) {
                comentarioCollectionComentarioToAttach = em.getReference(comentarioCollectionComentarioToAttach.getClass(), comentarioCollectionComentarioToAttach.getIdcomentario());
                attachedComentarioCollection.add(comentarioCollectionComentarioToAttach);
            }
            usuario.setComentarioCollection(attachedComentarioCollection);
            em.persist(usuario);
            for (Tema temaCollectionTema : usuario.getTemaCollection()) {
                Usuario oldIdusuarioOfTemaCollectionTema = temaCollectionTema.getIdusuario();
                temaCollectionTema.setIdusuario(usuario);
                temaCollectionTema = em.merge(temaCollectionTema);
                if (oldIdusuarioOfTemaCollectionTema != null) {
                    oldIdusuarioOfTemaCollectionTema.getTemaCollection().remove(temaCollectionTema);
                    oldIdusuarioOfTemaCollectionTema = em.merge(oldIdusuarioOfTemaCollectionTema);
                }
            }
            for (Comentario comentarioCollectionComentario : usuario.getComentarioCollection()) {
                Usuario oldIdusuarioOfComentarioCollectionComentario = comentarioCollectionComentario.getIdusuario();
                comentarioCollectionComentario.setIdusuario(usuario);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
                if (oldIdusuarioOfComentarioCollectionComentario != null) {
                    oldIdusuarioOfComentarioCollectionComentario.getComentarioCollection().remove(comentarioCollectionComentario);
                    oldIdusuarioOfComentarioCollectionComentario = em.merge(oldIdusuarioOfComentarioCollectionComentario);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdusuario());
            Collection<Tema> temaCollectionOld = persistentUsuario.getTemaCollection();
            Collection<Tema> temaCollectionNew = usuario.getTemaCollection();
            Collection<Comentario> comentarioCollectionOld = persistentUsuario.getComentarioCollection();
            Collection<Comentario> comentarioCollectionNew = usuario.getComentarioCollection();
            Collection<Tema> attachedTemaCollectionNew = new ArrayList<Tema>();
            for (Tema temaCollectionNewTemaToAttach : temaCollectionNew) {
                temaCollectionNewTemaToAttach = em.getReference(temaCollectionNewTemaToAttach.getClass(), temaCollectionNewTemaToAttach.getIdtema());
                attachedTemaCollectionNew.add(temaCollectionNewTemaToAttach);
            }
            temaCollectionNew = attachedTemaCollectionNew;
            usuario.setTemaCollection(temaCollectionNew);
            Collection<Comentario> attachedComentarioCollectionNew = new ArrayList<Comentario>();
            for (Comentario comentarioCollectionNewComentarioToAttach : comentarioCollectionNew) {
                comentarioCollectionNewComentarioToAttach = em.getReference(comentarioCollectionNewComentarioToAttach.getClass(), comentarioCollectionNewComentarioToAttach.getIdcomentario());
                attachedComentarioCollectionNew.add(comentarioCollectionNewComentarioToAttach);
            }
            comentarioCollectionNew = attachedComentarioCollectionNew;
            usuario.setComentarioCollection(comentarioCollectionNew);
            usuario = em.merge(usuario);
            for (Tema temaCollectionOldTema : temaCollectionOld) {
                if (!temaCollectionNew.contains(temaCollectionOldTema)) {
                    temaCollectionOldTema.setIdusuario(null);
                    temaCollectionOldTema = em.merge(temaCollectionOldTema);
                }
            }
            for (Tema temaCollectionNewTema : temaCollectionNew) {
                if (!temaCollectionOld.contains(temaCollectionNewTema)) {
                    Usuario oldIdusuarioOfTemaCollectionNewTema = temaCollectionNewTema.getIdusuario();
                    temaCollectionNewTema.setIdusuario(usuario);
                    temaCollectionNewTema = em.merge(temaCollectionNewTema);
                    if (oldIdusuarioOfTemaCollectionNewTema != null && !oldIdusuarioOfTemaCollectionNewTema.equals(usuario)) {
                        oldIdusuarioOfTemaCollectionNewTema.getTemaCollection().remove(temaCollectionNewTema);
                        oldIdusuarioOfTemaCollectionNewTema = em.merge(oldIdusuarioOfTemaCollectionNewTema);
                    }
                }
            }
            for (Comentario comentarioCollectionOldComentario : comentarioCollectionOld) {
                if (!comentarioCollectionNew.contains(comentarioCollectionOldComentario)) {
                    comentarioCollectionOldComentario.setIdusuario(null);
                    comentarioCollectionOldComentario = em.merge(comentarioCollectionOldComentario);
                }
            }
            for (Comentario comentarioCollectionNewComentario : comentarioCollectionNew) {
                if (!comentarioCollectionOld.contains(comentarioCollectionNewComentario)) {
                    Usuario oldIdusuarioOfComentarioCollectionNewComentario = comentarioCollectionNewComentario.getIdusuario();
                    comentarioCollectionNewComentario.setIdusuario(usuario);
                    comentarioCollectionNewComentario = em.merge(comentarioCollectionNewComentario);
                    if (oldIdusuarioOfComentarioCollectionNewComentario != null && !oldIdusuarioOfComentarioCollectionNewComentario.equals(usuario)) {
                        oldIdusuarioOfComentarioCollectionNewComentario.getComentarioCollection().remove(comentarioCollectionNewComentario);
                        oldIdusuarioOfComentarioCollectionNewComentario = em.merge(oldIdusuarioOfComentarioCollectionNewComentario);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdusuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdusuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Collection<Tema> temaCollection = usuario.getTemaCollection();
            for (Tema temaCollectionTema : temaCollection) {
                temaCollectionTema.setIdusuario(null);
                temaCollectionTema = em.merge(temaCollectionTema);
            }
            Collection<Comentario> comentarioCollection = usuario.getComentarioCollection();
            for (Comentario comentarioCollectionComentario : comentarioCollection) {
                comentarioCollectionComentario.setIdusuario(null);
                comentarioCollectionComentario = em.merge(comentarioCollectionComentario);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
