/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.web;

import com.mycompany.abstractsciencesociety.model.Categoria;
import com.mycompany.abstractsciencesociety.model.EntityProvider;
import com.mycompany.abstractsciencesociety.model.Tema;
import com.mycompany.abstractsciencesociety.model.Usuario;
import com.sun.corba.se.impl.naming.pcosnaming.NameService;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static org.eclipse.persistence.logging.SessionLog.EJB;
/**
 *
 * @author aide
 */
@ManagedBean
@RequestScoped

public class ControladorTemas {
    
    @ManagedProperty(value = "#{param.idTema}")
    private int idTema;
    private Tema temaM;
    private EntityManagerFactory emf;
    private CategoriaJpaController controladorCategoria;
    private TemaJpaController controladorTema;
    private List<Categoria> categorias;
    private int idcategoria;
    private List<Tema> temas;
    private TemaJpaController temacontrolador;
    /**
     * Creates a new instance of ControladorTemas
     */
    public ControladorTemas() {
         FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
          emf = EntityProvider.provider();
          controladorCategoria = new CategoriaJpaController(emf);
          controladorTema = new TemaJpaController(emf);
          categorias = new LinkedList();
          temas = new LinkedList();
          temacontrolador  = new TemaJpaController(emf);
          llenaCategorias();
          allTemas();
    }
    
   
    
    public Tema getTemaM() {
        return temaM;
    }
    
    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    public int getIdTema() {
        return idTema;
    }
    /**
     * Método para buscar un tema según su  id
     * @return 
    */
    public Tema getTema(int id){
        return controladorTema.findTema(id);
    }

    
    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public CategoriaJpaController getControladorCategoria() {
        return controladorCategoria;
    }

    public void setControladorCategoria(CategoriaJpaController controladorCategoria) {
        this.controladorCategoria = controladorCategoria;
    }

 

    public int getIdcategoria() {
        return idcategoria;
    }

    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public List<Tema> getTemas() {
        return temas;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    
    
    private void llenaCategorias(){
        List<Categoria> l = controladorCategoria.findCategoriaEntities(); 
        for(Categoria c : l){
           categorias.add(c);
        }
    
    }

    private void allTemas(){
        List<Tema> t = controladorTema.findTemaEntities(); 
        for(Tema tema : t){
           temas.add(tema);
        }
    }
    
    public Tema getTemaById(String idUsuario) {
        Tema temaMAux = controladorTema.findTema(Integer.valueOf(idUsuario));
        return temaMAux;
    }
    
    public String setTemaById(String id){
        if (id == "") {
            return "404";
        }
        Tema temaMAux = getTemaById(id);
        if (temaMAux == null) {
            return "404";
        }
        this.temaM = temaMAux;
        System.out.println(this.temaM);
        return null;
    }

    public String elimina(String id) {

        Tema temaMAux = getTemaById(id);

        if (temaMAux == null) {
            return "index?faces-redirect=true&eliminar=1";
        }

        try {
            controladorTema.destroy(temaMAux.getIdtema());
        } catch (Exception e) {
            System.out.println(e);
            return "ver-tema?faces-redirect=true?id=" + temaMAux.getIdtema().toString() + "&problema=eliminar";
        }
        return "index?faces-redirect=true&eliminar=1";
    }
    
   
}
