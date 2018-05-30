/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import com.mycompany.abstractsciencesociety.model.EntityProvider;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
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

    /**
     * redirect a home.
     */
    public static final String HOME_PAGE_REDIRECT = "index.xhtml?faces-redirect=true";
    /**
     * redirect login.
     */
    public static final String LOGIN_PAGE_REDIRECT = "inicio_de_sesion.xhtml?faces-redirect=true";
    /**
     * emf.
     */
    private EntityManagerFactory emf;
    /**
     * jpaController.
     */
    private UsuarioJpaController jpaController;

    /**
     * Creates a new instance of UsuarioBean.
     */
    public UsuarioBean() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new UsuarioJpaController(emf);
    }

    /**
     * isLogged.
     *
     * @return boolean
     */
    public boolean isLogged() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        return l != null;
    }

    /**
     * isLoggedAdmin.
     *
     * @return boolean
     */
    public boolean isLoggedAdmin() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l != null) {
            return l.getTipo().equals("admin");
        }
        return false;
    }

    /**
     * getUsuario.
     *
     * @return usuario
     */
    public com.mycompany.abstractsciencesociety.model.Usuario getUsuario() {
        FacesContext context = getCurrentInstance();
        return (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
    }

    /**
     * loggeado.
     *
     * @return redirect
     */
    public String loggeado() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l == null) {
            return LOGIN_PAGE_REDIRECT;
        }
        return null;
    }

    /**
     * noLoggeado.
     *
     * @return redirect
     */
    public String noLoggeado() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l != null) {
            return HOME_PAGE_REDIRECT;
        }
        return null;
    }

    /**
     * loggeadoAdmin.
     *
     * @return redirect.
     */
    public String loggeadoAdmin() {
        FacesContext context = getCurrentInstance();
        com.mycompany.abstractsciencesociety.model.Usuario l = (com.mycompany.abstractsciencesociety.model.Usuario) context.getExternalContext().getSessionMap().get("usuario");
        if (l == null) {
            return HOME_PAGE_REDIRECT;
        }
        if (!l.getTipo().equals("admin")) {
            return HOME_PAGE_REDIRECT;
        }
        return null;
    }

}
