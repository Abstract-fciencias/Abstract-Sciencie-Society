/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import com.mycompany.abstractsciencesociety.model.EntityProvider;
import com.mycompany.abstractsciencesociety.model.Usuario;
import com.mycompany.abstractsciencesociety.model.UsuarioJpaController;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *
 * @author edervs
 */
@ManagedBean
@SessionScoped
public class UsuarioController {

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
    public UsuarioController() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        emf = EntityProvider.provider();
        jpaController = new UsuarioJpaController(emf);
        usuario = new Usuario();
    }

    /**
     * getusuario.
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
        Usuario l = jpaController.findUsuario(usuario.getNombre(), usuario.getContraseña());
        boolean logged = l != null;
        if (logged) {
            FacesContext context = getCurrentInstance();
            context.getExternalContext().getSessionMap().put("usuario", l);
            return "secured/inicio?faces-redirect=true";
        }
        return "registro?faces-redirect=true";
    }

}