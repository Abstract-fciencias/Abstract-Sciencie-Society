/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import java.util.Locale;
import java.util.Date;
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
    private List<Comentario> comentarios;

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
        ControladorTemas temasControlador = new ControladorTemas();
        comentario.setIdtema(temasControlador.getTemaById(idTema));
        FacesContext context = FacesContext.getCurrentInstance();
        // Usuario 
        com.mycompany.abstractsciencesociety.model.Usuario usuario = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        comentario.setIdusuario(usuario);
        System.out.println("USUARIO");
        System.out.println(usuario);
        System.out.println(comentario.getIdusuario());
        // Fecha
        Date d = new Date();
        comentario.setFechapublicacion(d);

        EntityManagerFactory emf = EntityProvider.provider();
        ComentarioJpaController comentarioJpaC = new ComentarioJpaController(emf);
        comentarioJpaC.create(comentario);
        return "ver-comentario.html";
    }

    public String setComentarioIdTema(String idTema) {
        return null;
    }

}
