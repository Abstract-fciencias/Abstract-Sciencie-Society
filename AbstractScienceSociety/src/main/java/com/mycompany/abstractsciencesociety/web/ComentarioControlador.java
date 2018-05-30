/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import java.util.Locale;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import com.mycompany.abstractsciencesociety.model.Comentario;
import com.mycompany.abstractsciencesociety.model.EntityProvider;
import com.mycompany.abstractsciencesociety.model.ComentarioJpaController;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abstract
 */

@ManagedBean(name = "comentarioControlador")
@RequestScoped
public class ComentarioControlador {
    /**
     * comentario.
     */
    private Comentario comentario;
    /**
     * comentarios.
     */
    private LinkedList<Comentario> comentarios;

    /**
     * getComentario.
     * @return comentario
     */
    public Comentario getComentario() {
        return comentario;
    }

    /**
     * setComentario.
     * @param comentario
     */
    public void setComentario(final Comentario comentario) {
        this.comentario = comentario;
    }

    /**
     * getComentarios.
     * @return comentarios
     */
    public LinkedList<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * Constructor.
     */
    public ComentarioControlador() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        this.comentario = new Comentario();
    }

    /**
     * agregarComentario.
     * @return ver-comentario redirect
     */
    public String agregarComentario(){
        // Tema
        String idTema = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idtema");
        // Usuario 
        FacesContext context = FacesContext.getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario usuario = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        comentario.setIdusuario(usuario);
        if (usuario == null) {
            return "ver-tema?faces-redirect=true&id=" + idTema;
        }
        if (idTema == null) {
            return "index?faces-redirect=true";
        }
        ControladorTemas temasControlador = new ControladorTemas();
        comentario.setIdtema(temasControlador.getTemaById(idTema));
        // Fecha
        Date d = new Date();
        comentario.setFechapublicacion(d);

        EntityManagerFactory emf = EntityProvider.provider();
        ComentarioJpaController comentarioJpaC = new ComentarioJpaController(emf);
        comentarioJpaC.create(comentario);
        return "ver-tema?faces-redirect=true&agregado=1&id=" + idTema;
    }

    /**
     * allComentarios.
     */
    public String allComentarios(String idTema) {
        comentarios = new LinkedList(); 
        EntityManagerFactory emf = EntityProvider.provider();
        ComentarioJpaController comentarioJpaC = new ComentarioJpaController(emf);

        List<Comentario> c = comentarioJpaC.findComentarios(idTema);
        if (c == null || c.isEmpty()) {
            return null;
        }
        for (Comentario comentarioAux : c) {
           comentarios.add(comentarioAux);
        }

        return null;
    }
}
