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

@ManagedBean
@RequestScoped
public class RegistroControlador {
    private Usuario user = new Usuario();

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public RegistroControlador() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }

    public String agregarUsuario() {
        System.out.println("Pero que cosas");
        if (!user.getContraseña().equals(user.getConfirmacionContraseña())) {
            FacesContext.getCurrentInstance().addMessage(null
                                                         , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo de registro: Las contraseñas deben coincidir", ""));
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades, el registro se ha realizado correctamente", ""));
            user = null;
        }
        return null;
    }
}
