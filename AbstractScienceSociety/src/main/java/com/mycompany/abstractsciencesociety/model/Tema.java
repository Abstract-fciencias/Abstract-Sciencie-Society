/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aide
 */
@Entity
@Table(name = "tema")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tema.findAll", query = "SELECT t FROM Tema t")
    , @NamedQuery(name = "Tema.findByIdtema", query = "SELECT t FROM Tema t WHERE t.idtema = :idtema")
    , @NamedQuery(name = "Tema.findByContenido", query = "SELECT t FROM Tema t WHERE t.contenido = :contenido")
    , @NamedQuery(name = "Tema.findByFechapublicacion", query = "SELECT t FROM Tema t WHERE t.fechapublicacion = :fechapublicacion")
    , @NamedQuery(name = "Tema.findByDisponibilidad", query = "SELECT t FROM Tema t WHERE t.disponibilidad = :disponibilidad")})
public class Tema implements Serializable {

    @OneToMany(mappedBy = "idtema")
    private Collection<Comentario> comentarioCollection;

    private static final long serialVersionUID = 1L;
    /**
     * idtema.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtema")
    private Integer idtema;
    /**
     * contenido.
     */
    @Column(name = "contenido")
    private String contenido;
    /**
     * fechapublicacion.
     */
    @Basic(optional = false)
    @Column(name = "fechapublicacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapublicacion;
    /**
     * disponibilidad.
     */
    @Basic(optional = false)
    @Column(name = "disponibilidad")
    private String disponibilidad;
    /**
     * idcategoria.
     */
    @JoinColumn(name = "idcategoria", referencedColumnName = "idcategoria")
    @ManyToOne(optional = false)
    private Categoria idcategoria;
    /**
     * idusuario.
     */
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;

    /**
     * Tema.
     */
    public Tema() {
    }
    
    /**
     * Tema.
     * @param idtemaAux
     */
    public Tema(final Integer idtemaAux) {
        this.idtema = idtemaAux;
    }

    /**
     * Tema.
     * @param idtemaAux
     * @param fechapublicacionAux
     * @param disponibilidadAux
     */
    public Tema(final Integer idtemaAux, final Date fechapublicacionAux, final String disponibilidadAux) {
        this.idtema = idtemaAux;
        this.fechapublicacion = fechapublicacionAux;
        this.disponibilidad = disponibilidadAux;
    }

    /**
     * getIdtema.
     * @return idtema
     */
    public Integer getIdtema() {
        return idtema;
    }

    /**
     * setIdtema.
     * @param idtemaAux
     */
    public void setIdtema(final Integer idtemaAux) {
        this.idtema = idtemaAux;
    }

    /**
     * getContenido.
     * @return contenido
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * setContenido.
     * @param contenidoAux
     */
    public void setContenido(final String contenidoAux) {
        this.contenido = contenidoAux;
    }

    /**
     * getFechapublicacion.
     * @return fechapublicacion
     */
    public Date getFechapublicacion() {
        return fechapublicacion;
    }

    /**
     * setFechapublicacion.
     * @param fechapublicacionAux
     */
    public void setFechapublicacion(final Date fechapublicacionAux) {
        this.fechapublicacion = fechapublicacionAux;
    }

    /**
     * getDisponibilidad.
     * @return disponibilidad
     */
    public String getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * setDisponibilidad.
     * @param disponibilidadAux
     */
    public void setDisponibilidad(final String disponibilidadAux) {
        this.disponibilidad = disponibilidadAux;
    }

    /**
     * getIdcategoria.
     * @return idcategoria
     */
    public Categoria getIdcategoria() {
        return idcategoria;
    }

    /**
     * setIdcategoria.
     * @param idcategoriaAux
     */
    public void setIdcategoria(final Categoria idcategoriaAux) {
        this.idcategoria = idcategoriaAux;
    }

    /**
     * getIdusuario.
     * @return idusuario
     */
    public Usuario getIdusuario() {
        return idusuario;
    }

    /**
     * setIdusuario.
     * @param idusuarioAux
     */
    public void setIdusuario(Usuario idusuarioAux) {
        this.idusuario = idusuarioAux;
    }

    /**
     * hashCode.
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtema != null ? idtema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Tema)) {
            return false;
        }
        Tema other = (Tema) object;
        if ((this.idtema == null && other.idtema != null) || (this.idtema != null && !this.idtema.equals(other.idtema))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.abstractsciencesociety.model.Tema[ idtema=" + idtema + " ]";
    }

    @XmlTransient
    public Collection<Comentario> getComentarioCollection() {
        return comentarioCollection;
    }

    public void setComentarioCollection(Collection<Comentario> comentarioCollection) {
        this.comentarioCollection = comentarioCollection;
    }
}
