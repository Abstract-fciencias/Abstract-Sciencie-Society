/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import com.mycompany.abstractsciencesociety.model.EntityProvider;
import com.mycompany.abstractsciencesociety.model.UsuarioJpaController;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author miguel
 */
@ManagedBean
@SessionScoped
public class LoginControlador {

    /**
     * emf.
     */
    private EntityManagerFactory emf;
    /**
     * jpaController.
     */
    private UsuarioJpaController jpaController;
    /**
     * usuario.
     */
    private Usuario usuario;

    /**
     * Constructor.
     */
    public LoginControlador() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new UsuarioJpaController(emf);
        usuario = new Usuario();
    }

    /**
     * getUsuario.
     * @return usuario
     */
    public Usuario getusuario() {
        return usuario;
    }

    /**
     * setUsuario.
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * canLogin.
     * @return redirect
     */
    public String canLogin() {
        com.mycompany.abstractsciencesociety.model.Usuario usuarioM = jpaController.findUsuario(usuario.getCorreo(), usuario.getContraseña());
        boolean logged = usuarioM != null;
        if (logged) {
            FacesContext context = getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", usuarioM);
            return "index.xhtml?faces-redirect=true";
        }
        usuarioM = jpaController.findUsuario(usuario.getCorreo());
        if (usuarioM != null) {
            return "inicio_de_sesion?faces-redirect=true&password=1";
        }
        return "inicio_de_sesion?faces-redirect=true&email=1";
    }

    /**
     * Logout.
     * @return redirect
     */
    public String logout() {
        FacesContext context = getCurrentInstance();
        context.getExternalContext().invalidateSession();
        return "index?faces-redirect=true";
    }

}
