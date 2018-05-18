/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import com.mycompany.abstractsciencesociety.model.Usuario;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author edervs
 */
@ManagedBean
@SessionScoped
public class UsuarioBean {

    public static final String HOME_PAGE_REDIRECT = "index.xhtml?faces-redirect=true";
    public static final String LOGIN_PAGE_REDIRECT = "inicio_de_sesion.xhtml?faces-redirect=true";

    /**
     * Creates a new instance of UsuarioBean
     */
    public UsuarioBean() {
    }

    public boolean isLogged() {
        FacesContext context = getCurrentInstance();
        Usuario l = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        return l != null;
    }

    public Usuario getUsuario() {
        FacesContext context = getCurrentInstance();
        return (Usuario) context.getExternalContext().getSessionMap().get("usuario");
    }

    public String loggeado() {
        FacesContext context = getCurrentInstance();
        Usuario l = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l == null){
            return LOGIN_PAGE_REDIRECT;
        }
        return null;
    }

    public String noLoggeado() {
        FacesContext context = getCurrentInstance();
        Usuario l = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l != null){
            return HOME_PAGE_REDIRECT;
        }
        return null;
    }

}
