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

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.servlet.http.Part;

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
     * imagen.
     */
    private Part image;

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
     * getImage.
     */
    public Part getImage() {
        return image;
    }

    /**
     * setImage
     */
    public void setImage(Part imageAux) {
        this.image = imageAux;
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
     * doUpload.
     */
    public void doUpload() {
        try{
            InputStream in = image.getInputStream();

            File f = new File(System.getProperty("user.dir") + "/media/" + user.getNombre() + ".jpeg");
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }

            out.close();
            in.close();
        } catch (Exception e) {
            System.out.println(e);
        }
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

            if (image != null) {
                doUpload();
            }
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
