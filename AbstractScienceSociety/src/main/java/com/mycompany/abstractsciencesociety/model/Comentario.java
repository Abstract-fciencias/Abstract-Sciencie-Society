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
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author edervs
 */
@Entity
@Table(name = "comentario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comentario.findAll", query = "SELECT c FROM Comentario c")
    , @NamedQuery(name = "Comentario.findByIdcomentario", query = "SELECT c FROM Comentario c WHERE c.idcomentario = :idcomentario")
    , @NamedQuery(name = "Comentario.findByComentario", query = "SELECT c FROM Comentario c WHERE c.comentario = :comentario")
    , @NamedQuery(name = "Comentario.findByFechapublicacion", query = "SELECT c FROM Comentario c WHERE c.fechapublicacion = :fechapublicacion")})
@NamedNativeQueries(value = {
    @NamedNativeQuery( 
            name = "Comentario.findByIdtema",
            query = "select * from comentario where idtema = ?1",
            resultClass = Comentario.class
    )
})
public class Comentario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcomentario")
    private Integer idcomentario;
    @Basic(optional = false)
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "fechapublicacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechapublicacion;
    @JoinColumn(name = "idtema", referencedColumnName = "idtema")
    @ManyToOne
    private Tema idtema;
    @JoinColumn(name = "idusuario", referencedColumnName = "idusuario")
    @ManyToOne
    private Usuario idusuario;

    public Comentario() {
    }

    public Comentario(Integer idcomentario) {
        this.idcomentario = idcomentario;
    }

    public Comentario(Integer idcomentario, String comentario, Date fechapublicacion) {
        this.idcomentario = idcomentario;
        this.comentario = comentario;
        this.fechapublicacion = fechapublicacion;
    }

    public Comentario(String comentario, Date fechapublicacion) {
        this.comentario = comentario;
        this.fechapublicacion = fechapublicacion;
    }

    public Integer getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(Integer idcomentario) {
        this.idcomentario = idcomentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechapublicacion() {
        return fechapublicacion;
    }

    public void setFechapublicacion(Date fechapublicacion) {
        this.fechapublicacion = fechapublicacion;
    }

    public Tema getIdtema() {
        return idtema;
    }

    public void setIdtema(Tema idtema) {
        this.idtema = idtema;
    }

    public Usuario getIdusuario() {
        System.out.println("lalalalalalala");
        System.out.println(idusuario.getImagen());
        return idusuario;
    }

    public void setIdusuario(Usuario idusuario) {
        this.idusuario = idusuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcomentario != null ? idcomentario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentario)) {
            return false;
        }
        Comentario other = (Comentario) object;
        if ((this.idcomentario == null && other.idcomentario != null) || (this.idcomentario != null && !this.idcomentario.equals(other.idcomentario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.abstractsciencesociety.model.Comentario[ idcomentario=" + idcomentario + " ]";
    }
    
}
