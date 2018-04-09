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
 * @author damri
 */
@Entity
@Table(name = "comentario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tema.findAll", query = "SELECT c FROM Comentario c")
    , @NamedQuery(name = "Tema.findByIdComentario", query = "SELECT c FROM Comentario c WHERE c.idComentario = :idComentario")
    , @NamedQuery(name = "Tema.findByIdUsuario", query = "SELECT c FROM Comentario c WHERE c.idUsuario = :idUsuario")
    , @NamedQuery(name = "Tema.findByFechapublicacion", query = "SELECT c FROM Comentario c WHERE c.fechaPublicacion = :fechaPublicacion")})
public class Comentario implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idComentario")
    private Integer idComentario;
    @Column(name = "comentario")
    private String comentario;
    @Basic(optional = false)
    @Column(name = "idUsuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @Column(name = "fechaPublicacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaPublicacion;
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    @ManyToOne
    private Usuario usuario;
    
    
    public Comentario(){}
    
    public Comentario(Integer idComentario){
        this.idComentario = idComentario;
    }
    
    public Comentario(Integer idComentario, String comentario ,Date fechaPublicacion){
        this.idComentario = idComentario;
        this.comentario = comentario;
        this.fechaPublicacion = fechaPublicacion;
    }
    
    public Integer getIdComentario(){
     return idComentario;
    }
    
    public void setIdComentario(Integer nvoIdComentario){
     this.idComentario = nvoIdComentario;   
    }
    
    public String getComentario(){
     return comentario;   
    }
    
    public void setComentario(String nvoComentario){
     this.comentario = nvoComentario;   
    }
    
    public Date getFechaPublicacion(){
        return fechaPublicacion;
    }
    
    public void setFechacPublicacion(Date nvoFechaPublicacion){
        this.fechaPublicacion = nvoFechaPublicacion;
    }
    
    public Integer getIdUsuario(){
        return idUsuario;
    }
    
    public void setIdUsuario(Integer nvoIdUsuario){
        this.idUsuario = nvoIdUsuario;
    }
    
@Override
    public int hashCode() {
        int hash = 0;
        hash += (idComentario != null ? idComentario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comentario)) {
            return false;
        }
        Comentario other = (Comentario) object;
        if ((this.idComentario == null && other.idComentario != null) || (this.idComentario != null && !this.idComentario.equals(other.idComentario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.abstractsciencesociety.model.Comentario[ idComentario=" + idComentario + " ]";
    }    
    
}
    
