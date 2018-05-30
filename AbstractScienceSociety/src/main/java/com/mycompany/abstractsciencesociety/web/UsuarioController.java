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
import com.mycompany.abstractsciencesociety.web.exceptions.NonexistentEntityException;

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
     * usuario de model.
     */
    private com.mycompany.abstractsciencesociety.model.Usuario usuarioM;

    /**
     * contraseñaCambio.
     */
    private String contraseña;

    /**
     * confirmacionContraseñaCambio.
     */
    private String confirmacionContraseña;

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
     *
     * @return usuario
     */
    public Usuario getusuario() {
        return usuario;
    }

    /**
     * setUsuarioM.
     *
     * @param usuario
     */
    public void setUsuarioM(com.mycompany.abstractsciencesociety.model.Usuario usuario) {
        this.usuarioM = usuario;
    }

    /**
     * getusuarioM.
     *
     * @return usuario
     */
    public com.mycompany.abstractsciencesociety.model.Usuario getUsuarioM() {
        return usuarioM;
    }

    /**
     * setUsuario.
     *
     * @param usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * getConfirmacionContraseña.
     *
     * @return contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * setContraseña.
     *
     * @param usuario
     */
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    /**
     * getContraseña.
     *
     * @return contraseña
     */
    public String getConfirmacionContraseña() {
        return confirmacionContraseña;
    }

    /**
     * setContraseña.
     *
     * @param usuario
     */
    public void setConfirmacionContraseña(String contraseña) {
        this.confirmacionContraseña = contraseña;
    }

    /**
     * canLogin.
     *
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

    /**
     * getUsuarioById.
     *
     * @param idUsuario
     * @return redirect
     */
    public com.mycompany.abstractsciencesociety.model.Usuario getUsuarioById(String idUsuario) {
        com.mycompany.abstractsciencesociety.model.Usuario usuarioM = jpaController.findUsuario(Integer.valueOf(idUsuario));
        return usuarioM;
    }

    /**
     * setUsuarioById.
     *
     * @param idUsuario
     * @return redirect
     */
    public String setUsuarioById(String idUsuario) {
        if (idUsuario == "") {
            return "404";
        }
        com.mycompany.abstractsciencesociety.model.Usuario usuarioM = getUsuarioById(idUsuario);
        if (usuarioM == null) {
            return "404";
        }
        this.usuarioM = usuarioM;
        return null;
    }

    /**
     * updateUsuario.
     *
     * @return String
     */
    public String updateUsuario() {
        if (this.usuarioM == null) {
            return "usuarios?faces-redirect=true&actualizar=1";
        }

        if (contraseña != null && !contraseña.equals("")) {
            if (confirmacionContraseña != null && !confirmacionContraseña.equals("")) {
                if (contraseña.equals(confirmacionContraseña)) {
                    usuarioM.setContraseña(contraseña);
                } else {
                    System.out.println("no iguales");
                    return "ver-usuario?faces-redirect=true&idUsuario=" + usuarioM.getIdusuario().toString() + "&problema=password";
                }
            } else {
                return "ver-usuario?faces-redirect=true&idUsuario=" + usuarioM.getIdusuario().toString() + "&problema=password";
            }
        }

        try {
            jpaController.edit(usuarioM);
        } catch (Exception e) {
            System.out.println(e);
            return "ver-usuario?faces-redirect=true?idUsuario=" + usuarioM.getIdusuario().toString() + "&problema=actualizar";
        }
        return "ver-usuario?faces-redirect=true&idUsuario=" + usuarioM.getIdusuario().toString() + "&actualizado=1";
    }

    /**
     * deleteUsuario.
     *
     * @return String
     */
    public String deleteUsuario() {
        if (this.usuarioM == null) {
            return "usuarios?faces-redirect=true&eliminar=1";
        }

        try {
            jpaController.destroy(usuarioM.getIdusuario());
        } catch (Exception e) {
            System.out.println(e);
            return "ver-usuario?faces-redirect=true?idUsuario=" + usuarioM.getIdusuario().toString() + "&problema=eliminar";
        }
        return "usuarios?faces-redirect=true&eliminar=1";
    }

}
