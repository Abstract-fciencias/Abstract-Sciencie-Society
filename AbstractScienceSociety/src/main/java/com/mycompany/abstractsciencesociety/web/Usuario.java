/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

/**
 *
 * @author abstract
 */
public class Usuario {
    /**
     * nombre.
     */
    private String nombre;
    /**
     * correo.
     */
    private String correo;
    /**
     * contraseña.
     */
    private String contraseña;
    /**
     * confirmacionContraseña.
     */
    private String confirmacionContraseña;
    /**
     * carrera.
     */
    private String carrera;
    /**
     * anioingreso.
     */
    private String anioingreso;

    /**
     * getNombre.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * setNombre.
     * @param nombreAux
     */
    public void setNombre(String nombreAux) {
        this.nombre = nombreAux;
    }

    /**
     * getCorreo.
     * @return correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * setCorreo.
     * @param correoAux
     */
    public void setCorreo(String correoAux) {
        this.correo = correoAux;
    }

    /**
     * getContraseña.
     * @return contraseña
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * setContraseña.
     * @param contraseñaAux
     */
    public void setContraseña(String contraseñaAux) {
        this.contraseña = contraseñaAux;
    }

    /**
     * getConfirmacionContraseña.
     * @return confirmacionContraseña
     */
    public String getConfirmacionContraseña() {
        return confirmacionContraseña;
    }

    /**
     * setConfirmacionContraseña.
     * @param confirmacionContraseñaAux
     */
    public void setConfirmacionContraseña(String confirmacionContraseñaAux) {
        this.confirmacionContraseña = confirmacionContraseñaAux;
    }

    /**
     * getCarrera.
     * @return carrera
     */
    public String getCarrera() {
        return carrera;
    }

    /**
     * setCarrera.
     * @param carreraAux
     */
    public void setCarrera(String carreraAux) {
        this.carrera = carreraAux;
    }

    /**
     * getAnioIngreso.
     * @return anioIngreso
     */
    public String getAnioingreso() {
        return this.anioingreso;
    }

    /**
     * setAnioingreso.
     * @param anioIngresoAux
     */
    public void setAnioingreso(String anioIngresoAux) {
        this.anioingreso = anioIngresoAux;
    }

}

