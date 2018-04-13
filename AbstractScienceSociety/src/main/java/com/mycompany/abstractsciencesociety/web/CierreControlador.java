/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import java.util.*;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import javax.faces.context.FacesContext;

import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpSession;

/**
 *
 * @author abstract
 */

@ManagedBean(name = "cierreControlador")
@RequestScoped
public class CierreControlador {
    private Usuario user = new Usuario();

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public CierreControlador() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
    }      

    public String logout() {
      HttpSession session = UtilCierre.getSession();
      session.invalidate();
      return "index.xhtml";
   }

}
