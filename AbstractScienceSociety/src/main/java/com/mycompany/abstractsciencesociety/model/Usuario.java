/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author abstract
 */
@Entity
@Table(name="usuario", schema="usuario", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nombre", "correo"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Login.findAll", query = "SELECT l FROM Usuario l")
    , @NamedQuery(name = "Login.findById", query = "SELECT l FROM Usuario l WHERE l.id = :id")
    , @NamedQuery(name = "Login.findByUsuario", query = "SELECT l FROM Usuario l WHERE l.nombre = :nombre")
    , @NamedQuery(name = "Login.findByContraseña", query = "SELECT l FROM Usuario l WHERE l.contraseña = :password")})
@NamedNativeQueries(value = {
    @NamedNativeQuery(
            name = "Login.canLogin",
            query = "select Usuario.login(?, ?)"
    )
    , @NamedNativeQuery(
            name = "Usuario.findByNombreAndContraseña",
            query = "select id, nombre from Usuario.login where nombre = ?1 and contraseña = crypt(?2, password)",
            resultClass = Usuario.class
    )
})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 50)
    private String nombre;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 80)
    private String contraseña;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 80)
    private String correo;
    
    @Basic(optional = false)
    @Column(nullable = false, length = 80)
    private String carrera;
    
    @Basic(optional = false)
    @Column(nullable = false, name="añoIngreso")
    private int añoIngreso;

    public Usuario() {
    }

    public Usuario(Integer id) {
        this.id = id;
    }

    public Usuario(Integer id, String usuario, String pass) {
        this.id = id;
        this.nombre = usuario;
        this.contraseña = pass;
    }
    
    public Usuario(String nombre, String pass, String carrera, int añoIngreso) {
        this.nombre = nombre;
        this.contraseña = pass;
        this.carrera = carrera;
        this.añoIngreso = añoIngreso;
        this.id = 1;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }
    
    public int getAñoIngreso() {
        return añoIngreso;
    }

    public void setAñoIngreso(int añoIngreso) {
        this.añoIngreso = añoIngreso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Usuario[ id=" + id + " nombre= " + nombre + "]";
    }

}