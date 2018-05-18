/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import com.mycompany.abstractsciencesociety.model.EntityProvider;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import com.mycompany.abstractsciencesociety.model.UsuarioJpaController;

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
    private EntityManagerFactory emf;
    private UsuarioJpaController jpaController;

    /**
     * Creates a new instance of UsuarioBean
     */

    public UsuarioBean() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new UsuarioJpaController(emf);
    }

    public boolean isLogged() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        return l != null;
    }

    public boolean isLoggedAdmin() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l != null) {
            return l.getTipo().equals("admin");
        }
        return false;
    }

    public com.mycompany.abstractsciencesociety.model.Usuario getUsuario() {
        FacesContext context = getCurrentInstance();
        return (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
    }

    public String loggeado() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l == null){
            return LOGIN_PAGE_REDIRECT;
        }
        return null;
    }
    
    public String loggeadoAdmin() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l == null){
            return HOME_PAGE_REDIRECT;
        }
        if (!l.getTipo().equals("admin")) {
            return HOME_PAGE_REDIRECT;
        }
        return null;
    }

    public String noLoggeado() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l != null){
            return HOME_PAGE_REDIRECT;
        }
        return null;
    }

}
