/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import com.mycompany.abstractsciencesociety.model.EntityProvider;
import com.mycompany.abstractsciencesociety.model.UsuarioJpaController;
import java.util.Locale;
import java.util.LinkedList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author abstract
 */

@ManagedBean
@RequestScoped
public class RegistroControlador {
    /**
     * user.
     */
    private Usuario user;
    /**
     * usuarios.
     */
    final private LinkedList<com.mycompany.abstractsciencesociety.model.Usuario> usuarios;
    /**
     * emf.
     */
    private EntityManagerFactory emf;
    /**
     * controladorUsuario.
     */
    private UsuarioJpaController controladorUsuario;

    /**
     * RegistroControlador.
     */
    public RegistroControlador() {
        user = new Usuario();
        usuarios = new LinkedList();
        emf = EntityProvider.provider();
        controladorUsuario = new UsuarioJpaController(emf);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        allUsuarios();
    }

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
    public void setUser(Usuario user) {
        this.user = user;
    }

    /**
     * getUsuarios.
     * @return usuarios.
     */
    public LinkedList<com.mycompany.abstractsciencesociety.model.Usuario> getUsuarios() {
        for (com.mycompany.abstractsciencesociety.model.Usuario usuario: usuarios) {
        }
        return usuarios;
    }

    /**
     * validarCorreoCiencias.
     * @return boolean
     */
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
        if (usuarioCorreo.equals("") || !dominioCorreo.equals("ciencias.unam.mx")) {
            return false;
        }

        return true;
    }

    /**
     * agregarUsuario.
     * @return redirect
     */
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
            nuevoUsuario = new com.mycompany.abstractsciencesociety.model.Usuario(user.getNombre(), user.getCorreo(), user.getContraseña(), "normal", user.getCarrera(), user.getAnioingreso());
            EntityManagerFactory emf = EntityProvider.provider();
            UsuarioJpaController usuarioJpaC = new UsuarioJpaController(emf);
            usuarioJpaC.create(nuevoUsuario);
            user = null;

            // Agregando el nuevo usuario a nuestra lista de usuarios.
            usuarios.add(nuevoUsuario);

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Felicidades, el registro se ha realizado correctamente", ""));

            // Redireccionando a la pantalla de inicio
            return "index.xhtml?faces-redirect=true&register=1";
        }
        return null;
    }

    /**
     * allUsuarios.
     */
    private void allUsuarios() {
        List<com.mycompany.abstractsciencesociety.model.Usuario> u = controladorUsuario.findUsuarioEntities(); 
        for (com.mycompany.abstractsciencesociety.model.Usuario usuario : u) {
           usuarios.add(usuario);
        }
    }
}
