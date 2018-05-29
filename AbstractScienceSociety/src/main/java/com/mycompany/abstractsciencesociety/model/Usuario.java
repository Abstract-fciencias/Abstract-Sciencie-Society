/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author edervs
 */
@Entity
@Table(name = "usuario", schema="public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")
    , @NamedQuery(name = "Usuario.findByIdusuario", query = "SELECT u FROM Usuario u WHERE u.idusuario = :idusuario")
    , @NamedQuery(name = "Usuario.findByNombre", query = "SELECT u FROM Usuario u WHERE u.nombre = :nombre")
    , @NamedQuery(name = "Usuario.findByCorreo", query = "SELECT u FROM Usuario u WHERE u.correo = :correo")
    , @NamedQuery(name = "Usuario.findByContrase\u00f1a", query = "SELECT u FROM Usuario u WHERE u.contrase\u00f1a = :contrase\u00f1a")
    , @NamedQuery(name = "Usuario.findByTipo", query = "SELECT u FROM Usuario u WHERE u.tipo = :tipo")
    , @NamedQuery(name = "Usuario.findByCarrera", query = "SELECT u FROM Usuario u WHERE u.carrera = :carrera")
    , @NamedQuery(name = "Usuario.findByAnioingreso", query = "SELECT u FROM Usuario u WHERE u.anioingreso = :anioingreso")})
@NamedNativeQueries(value = {
    @NamedNativeQuery(
            name = "canLogin",
            query = "select login(?, ?)"
    )
    , @NamedNativeQuery( 
            name = "Usuario.findByCorreoAndContraseña",
            query = "select * from usuario where correo = ?1 and contraseña = crypt(?2, contraseña)",
            resultClass = Usuario.class
    )
})
public class Usuario implements Serializable {

    /**
     * temaCollection.
     */
    @OneToMany(mappedBy = "idusuario")
    private Collection<Tema> temaCollection;

    private static final long serialVersionUID = 1L;
    /**
     * idusuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idusuario")
    private Integer idusuario;
    /**
     * nombre.
     */
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    /**
     * correo.
     */
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    /**
     * contraseña.
     */
    @Basic(optional = false)
    @Column(name = "contrase\u00f1a")
    private String contraseña;
    /**
     * tipo.
     */
    @Basic(optional = false)
    @Column(name = "tipo")
    private String tipo;
    /**
     * carrera.
     */
    @Basic(optional = false)
    @Column(name = "carrera")
    private String carrera;
    /**
     * anioingreso.
     */
    @Basic(optional = false)
    @Column(name = "anioingreso")
    private String anioingreso;

    /**
     * Usuario.
     */
    public Usuario() {
    }

    /**
     * Usuario.
     * @param idusuarioAux
     */
    public Usuario(final Integer idusuarioAux) {
        this.idusuario = idusuarioAux;
    }

    /**
     * Usuario.
     * @param idusuarioAux
     * @param nombreAux
     * @param correoAux
     * @param contraseñaAux
     * @param tipoAux
     * @param carreraAux
     * @param anioingresoAux
     */
    public Usuario(final Integer idusuarioAux, final String nombreAux, final String correoAux, final String contraseñaAux, final String tipoAux, final String carreraAux, final String anioingresoAux) {
        this.idusuario = idusuarioAux;
        this.nombre = nombreAux;
        this.correo = correoAux;
        this.contraseña = contraseñaAux;
        this.tipo = tipoAux;
        this.carrera = carreraAux;
        this.anioingreso = anioingresoAux;
    }

    /**
     * Usuario.
     * @param nombreAux
     * @param correoAux
     * @param contraseñaAux
     * @param tipoAux
     * @param carreraAux
     * @param anioingresoAux
     */
    public Usuario(final String nombreAux, final String correoAux, final String contraseñaAux, final String tipoAux, final String carreraAux, final String anioingresoAux) {
        this.nombre = nombreAux;
        this.correo = correoAux;
        this.contraseña = contraseñaAux;
        this.tipo = tipoAux;
        this.carrera = carreraAux;
        this.anioingreso = anioingresoAux;
    }

    /**
     * getIdusuario.
     * @return idusuario
     */
    public Integer getIdusuario() {
        return idusuario;
    }

    /**
     * setIdusuario
     * @param idusuarioAux
     */
    public void setIdusuario(final Integer idusuarioAux) {
        this.idusuario = idusuarioAux;
    }

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
    public void setNombre(final String nombreAux) {
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
    public void setCorreo(final String correoAux) {
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
    public void setContraseña(final String contraseñaAux) {
        this.contraseña = contraseñaAux;
    }

    /**
     * getTipo.
     * @return tipo
     */
    public String getTipo() {
        return this.tipo;
    }

    /**
     * setTipo.
     * @param tipoAux
     */
    public void setTipo(final String tipoAux) {
        this.tipo = tipoAux;
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
    public void setCarrera(final String carreraAux) {
        this.carrera = carreraAux;
    }

    /**
     * getAnioingreso.
     * @return anioingreso
     */
    public String getAnioingreso() {
        return anioingreso;
    }

    /**
     * setAnioingreso.
     * @param anioingresoAux
     */
    public void setAnioingreso(final String anioingresoAux) {
        this.anioingreso = anioingresoAux;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idusuario != null ? idusuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idusuario == null && other.idusuario != null) || (this.idusuario != null && !this.idusuario.equals(other.idusuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.abstractsciencesociety.model.Usuario[ idusuario=" + idusuario + " ]";
    }

    /**
     * getTemasCollection.
     * @return temaCollection
     */
    @XmlTransient
    public Collection<Tema> getTemaCollection() {
        return temaCollection;
    }

    /**
     * setTemaCollection.
     * @param temaCollectionAux
     */
    public void setTemaCollection(final Collection<Tema> temaCollectionAux) {
        this.temaCollection = temaCollectionAux;
    }
}
