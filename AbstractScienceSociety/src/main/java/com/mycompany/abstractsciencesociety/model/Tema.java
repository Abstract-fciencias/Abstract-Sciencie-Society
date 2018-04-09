/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtema")
    private Integer idtema;
    @Column(name = "contenido")
    private String contenido;
    @Basic(optional = false)
    @Column(name = "fechapublicacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapublicacion;
    @Basic(optional = false)
    @Column(name = "disponibilidad")
    private String disponibilidad;
    @JoinColumn(name = "idcategoria", referencedColumnName = "idcategoria")
    @ManyToOne(optional = false)
    private Categoria idcategoria;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;

    public Tema() {
    }

    public Tema(Integer idtema) {
        this.idtema = idtema;
    }

    public Tema(Integer idtema, Date fechapublicacion, String disponibilidad) {
        this.idtema = idtema;
        this.fechapublicacion = fechapublicacion;
        this.disponibilidad = disponibilidad;
    }

    public Integer getIdtema() {
        return idtema;
    }

    public void setIdtema(Integer idtema) {
        this.idtema = idtema;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Date getFechapublicacion() {
        return fechapublicacion;
    }

    public void setFechapublicacion(Date fechapublicacion) {
        this.fechapublicacion = fechapublicacion;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Categoria getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(Categoria idcategoria) {
        this.idcategoria = idcategoria;
    }

    public Usuario getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtema != null ? idtema.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
    
}
