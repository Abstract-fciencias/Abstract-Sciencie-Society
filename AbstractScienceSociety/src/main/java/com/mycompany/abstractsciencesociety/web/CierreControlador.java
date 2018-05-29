/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import java.util.Locale;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpSession;

/**
 *
 * @author abstract
 */

@ManagedBean(name = "cierreControlador")
@RequestScoped
public class CierreControlador {
    /**
     * user.
     */
    private Usuario user;

    /**
     * getUser.
     * @return user
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
     * Constructor.
     */
    public CierreControlador() {
        user = new Usuario();
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }

    /**
     * logout.
     * @return index home page
     */
    public String logout() {
      HttpSession session = UtilCierre.getSession();
      session.invalidate();
      return "index.xhtml";
   }

}
