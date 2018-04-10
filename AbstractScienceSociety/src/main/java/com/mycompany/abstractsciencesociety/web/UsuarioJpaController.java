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
import com.mycompany.abstractsciencesociety.model.Tema;
import com.mycompany.abstractsciencesociety.model.Usuario;
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
            Collection<Tema> attachedTemaCollectionNew = new ArrayList<Tema>();
            for (Tema temaCollectionNewTemaToAttach : temaCollectionNew) {
                temaCollectionNewTemaToAttach = em.getReference(temaCollectionNewTemaToAttach.getClass(), temaCollectionNewTemaToAttach.getIdtema());
                attachedTemaCollectionNew.add(temaCollectionNewTemaToAttach);
            }
            temaCollectionNew = attachedTemaCollectionNew;
            usuario.setTemaCollection(temaCollectionNew);
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

    public Usuario findUsuario(String correo, String contraseña) {
        EntityManager em = getEntityManager();
        Query q = em.createNamedQuery("Usuario.findByCorreoAndContraseña")
                .setParameter(1, correo)
                .setParameter(2, contraseña);
        if (q.getResultList().isEmpty()) {
            return null;
        }
        return (Usuario) q.getSingleResult();
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
