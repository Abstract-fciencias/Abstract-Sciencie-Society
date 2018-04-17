/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import com.mycompany.abstractsciencesociety.model.EntityProvider;
import com.mycompany.abstractsciencesociety.model.UsuarioJpaController;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.lang.InterruptedException;

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

    private boolean validarCorreoCiencias() {
        String usuarioCorreo, dominioCorreo;
        String[] partesCorreo = user.getCorreo().split("@");
        // Validando que sólo haya un @
        if (partesCorreo.length != 2) {
            return false;
        }

        
        // Validando que pertenezca a un correo de @ciencias.unam.mx
        usuarioCorreo = partesCorreo[0];
        dominioCorreo = partesCorreo[1];
        if (usuarioCorreo.equals("") && !dominioCorreo.equals("ciencias.unam.mx")) {
            return false;
        }

        return true;
    }

    public String agregarUsuario() {
        com.mycompany.abstractsciencesociety.model.Usuario nuevoUsuario;
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(new Locale("es-Mx"));

        if (!user.getContraseña().equals(user.getConfirmacionContraseña())) {
            context.addMessage(null
                                                         , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo de registro: Las contraseñas deben coincidir", ""));
        } else if (!validarCorreoCiencias()) {
            context.addMessage(null
                                                         , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fallo de registro: Sólo se puede registrar con un correo de @ciencias.unam.mx", ""));
        } else {
            System.out.println(user.getAñoingreso());
            nuevoUsuario = new com.mycompany.abstractsciencesociety.model.Usuario(user.getNombre(), user.getCorreo(), user.getContraseña(), "normal", user.getCarrera(), user.getAñoingreso());
            EntityManagerFactory emf = EntityProvider.provider();
            UsuarioJpaController usuarioJpaC = new UsuarioJpaController(emf);
            usuarioJpaC.create(nuevoUsuario);
            user = null;

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades, el registro se ha realizado correctamente", ""));
            
            // Redireccionando a la pantalla de inicio
            try {
                context.getExternalContext().redirect("index.xhtml");
            } catch (IOException e) {
                System.out.println("No se encontró el archivo index.xhtml");
            }
        }
        return null;
    }
}
