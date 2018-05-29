/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author aide
 */
@Entity
@Table(name = "categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c")
    , @NamedQuery(name = "Categoria.findByIdcategoria", query = "SELECT c FROM Categoria c WHERE c.idcategoria = :idcategoria")
    , @NamedQuery(name = "Categoria.findByNombre", query = "SELECT c FROM Categoria c WHERE c.nombre = :nombre")
    , @NamedQuery(name = "Categoria.findByDescripcion", query = "SELECT c FROM Categoria c WHERE c.descripcion = :descripcion")})
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * idCategoria.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcategoria")
    private Integer idcategoria;
    /**
     * nombre.
     */
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    /**
     * descripcion.
     */
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    /**
     * temaCollection.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idcategoria")
    private Collection<Tema> temaCollection;

    /**
     * Categoria.
     */
    public Categoria() {
    }

    /**
     * Categoria.
     * @param idcategoria
     */
    public Categoria(final Integer idcategoria) {
        this.idcategoria = idcategoria;
    }

    /**
     * Categoria.
     * @param idcategoria
     * @param nombre
     * @param descripcion
     */
    public Categoria(final Integer idcategoria, final String nombre, final String descripcion) {
        this.idcategoria = idcategoria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    /**
     * getIdcategoria.
     * @return idcategoria
     */
    public Integer getIdcategoria() {
        return idcategoria;
    }

    /**
     * setIdcategoria.
     * @param idcategoria
     */
    public void setIdcategoria(final Integer idcategoria) {
        this.idcategoria = idcategoria;
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
     * @param nombre
     */
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }

    /**
     * getDescription.
     * @return description
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * setDescription.
     * @param descripcion
     */
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * getTemaCollection.
     * @return temaCollection
     */
    @XmlTransient
    public Collection<Tema> getTemaCollection() {
        return temaCollection;
    }

    /**
     * setTemaCollection.
     * @param temaCollection
     */
    public void setTemaCollection(final Collection<Tema> temaCollection) {
        this.temaCollection = temaCollection;
    }

    /**
     * hashCode.
     * @return hash
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcategoria != null ? idcategoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(final Object object) {
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.idcategoria == null && other.idcategoria != null) || (this.idcategoria != null && !this.idcategoria.equals(other.idcategoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.abstractsciencesociety.model.Categoria[ idcategoria=" + idcategoria + " ]";
    }
}
