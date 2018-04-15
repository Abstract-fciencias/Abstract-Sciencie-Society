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
public class CreacionTemaBean {
    
    private EntityManagerFactory emf;
    private CategoriaJpaController controladorCategoria;
    private TemaJpaController controladorTema;
    private Tema tema;
    private List<String> categorias;
    private List<Tema> temas;
    private TemaJpaController temacontrolador;
    /**
     * Creates a new instance of CreacionTemaBean
     */
    public CreacionTemaBean(){
          FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
          emf = EntityProvider.provider();
          controladorCategoria = new CategoriaJpaController(emf);
          controladorTema = new TemaJpaController(emf);
          tema = new Tema();
          categorias = new LinkedList();
          temas = new LinkedList();
          temacontrolador  = new TemaJpaController(emf);
          llenaCategorias();
          allTemas();
    }
    
    public String creaTema(){
        // Obteniendo el usuario Loggeado
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Tema creado exitosament", ""));
        Usuario usuario_l = (Usuario) context.getExternalContext().getSessionMap().get("usuario");

        // Creando el tema
        Date d = new Date();
        tema.setFechapublicacion(d);
        tema.setDisponibilidad("abierto");
        tema.setIdusuario(usuario_l);
        temacontrolador.create(tema);
        temas.add(tema);

        // Mandando notificación
        String nombre = tema.getContenido();
        return "index.xhtml";
        //String requ = String.format("ver-tema?faces-redirect=true&amp;idTema = %s", nombre);
        //return  requ;
         
         
    }
    
    public void asignaCategoria(java.lang.String categoria){
        List<Categoria> l = controladorCategoria.findCategoriaEntities();
        for(Categoria c : l){
             if(c.getNombre().equals(categoria) ){
                tema.setIdcategoria(c);
                return;
             }
        }

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

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public List<Tema> getTemas() {
        return temas;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }
   
    private void llenaCategorias(){
        List<Categoria> l = controladorCategoria.findCategoriaEntities(); 
        for(Categoria c : l){
           categorias.add(c.getNombre());
        }
    
    }

    private void allTemas(){
        List<Tema> t = controladorTema.findTemaEntities(); 
        for(Tema tema : t){
           temas.add(tema);
        }
    }
}
