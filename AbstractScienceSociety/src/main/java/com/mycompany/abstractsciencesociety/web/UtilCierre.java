/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;


import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * UtilCierre.
 * @author edervs
 */
public class UtilCierre {

    /**
     * getSession.
     * @return session
     */
    public static HttpSession getSession() {
        return (HttpSession)
          FacesContext.
          getCurrentInstance().
          getExternalContext().
          getSession(false);
      }

}
