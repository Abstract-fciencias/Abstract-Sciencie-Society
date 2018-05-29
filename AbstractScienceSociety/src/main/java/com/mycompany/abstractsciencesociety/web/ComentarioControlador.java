/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author abstract
 */

@ManagedBean(name = "comentarioControlador")
@RequestScoped
public class ComentarioControlador {
    /**
     * user.
     */
    private Usuario user;
    /**
     * comentario.
     */
    private String comentario;

    /**
     * getUser.
     * @return usuario
     */
    public Usuario getUser() {
        return user;
    }

    /**
     * setUser.
     * @param user
     */
    public void setUser(final Usuario user) {
        this.user = user;
    }

    /**
     * getComentario.
     * @return comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * setComentario.
     * @param comentario
     */
    public void setComentario(final String comentario) {
        this.comentario = comentario;
    }

    /**
     * Constructor.
     */
    public ComentarioControlador() {
        user = new Usuario();
        comentario = "";
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }

    /**
     * agregarComentario.
     * @return ver-comentario redirect
     */
    public String agregarComentario(){
        //implementar codigo
        return "ver-comentario.html";
    }

}
