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
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
/**
 *
 * @author aide
 */
@ManagedBean
@RequestScoped
public class CreacionTemaBean {
    /**
     * emf.
     */
    private EntityManagerFactory emf;
    /**
     * controladorCategoria.
     */
    private CategoriaJpaController controladorCategoria;
    /**
     * controladorTema.
     */
    private final TemaJpaController controladorTema;
    /**
     * tema.
     */
    private Tema tema;
    /**
     * categorias.
     */
    private List<Categoria> categorias;
    /**
     * idcategoria.
     */
    private int idcategoria;
<<<<<<< HEAD
    private List<Tema> temas;
    private TemaJpaController temacontrolador;
    private String busqueda;
=======
>>>>>>> origin/feature/upload-image
    /**
     * temas.
     */
    private final List<Tema> temas;
    /**
     * temacontrolador.
     */
    private final  TemaJpaController temacontrolador;
    /**
     * Creates a new instance of CreacionTemaBean.
     */
    public CreacionTemaBean() {
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
          busqueda = "";
          
    }

<<<<<<< HEAD
=======
    /**
     * creaTema.
     * @return index home redirect
     */
>>>>>>> origin/feature/upload-image
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
        asignaCategoria();
        temacontrolador.create(tema);
        temas.add(tema);

        // Mandando notificación
        String nombre = tema.getContenido();
        return   "ver-tema.xhtml?faces-redirect=true&id="+String.valueOf(tema.getIdtema());
        //String requ = String.format("ver-tema?faces-redirect=true&amp;idTema = %s", nombre);
        //return  requ;
    }

<<<<<<< HEAD
    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }
    
    public void asignaCategoria(){
        for(Categoria c : categorias){
             if(c.getIdcategoria() == idcategoria){
=======
    /**
     * asignaCategoria.
     */
    public void asignaCategoria () {
        for (Categoria c : categorias) {
             if (c.getIdcategoria() == idcategoria) {
>>>>>>> origin/feature/upload-image
                tema.setIdcategoria(c);
                return;
             }
        }

    }

    /**
     * getEmf.
     * @return emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }

    /**
     * setEmf.
     * @param emf
     */
    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * getControladorCategoria.
     * @return controladorCategoria
     */
    public CategoriaJpaController getControladorCategoria() {
        return controladorCategoria;
    }

    /**
     * setControladorCategoria.
     * @param controladorCategoria
     */
    public void setControladorCategoria(CategoriaJpaController controladorCategoria) {
        this.controladorCategoria = controladorCategoria;
    }

    /**
     * getTema.
     * @return tema
     */
    public Tema getTema() {
        return tema;
    }

    /**
     * setTema.
     * @param tema
     */
    public void setTema(Tema tema) {
        this.tema = tema;
    }

    /**
     * getIdcategoria.
     * @return idcategoria
     */
    public int getIdcategoria() {
        return idcategoria;
    }

    /**
     * setIdcategoria.
     * @param idcategoria
     */
    public void setIdcategoria(int idcategoria) {
        this.idcategoria = idcategoria;
    }

    /**
     * getCategorias.
     * @return categorias
     */
    public List<Categoria> getCategorias() {
        return categorias;
    }

    /**
     * getTemas.
     * @return temas
     */
    public List<Tema> getTemas() {
        return temas;
    }

    /**
     * setCategorias.
     * @param categorias
     */
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    /**
     * llenaCategorias.
     */
    private void llenaCategorias() {
        List<Categoria> l = controladorCategoria.findCategoriaEntities();
        for (Categoria c : l) {
           categorias.add(c);
        }
    }

    /**
     * allTemas.
     */
    private void allTemas(){
        List<Tema> t = controladorTema.findTemaEntities();
        for (Tema temaAux : t) {
           temas.add(temaAux);
        }
    }
    public void buscaPorCategoria(Categoria categoria){
        System.out.print("holi eder");
        List<Tema> aux = temacontrolador.findTemaEntities();
        List<Tema> nueva = new LinkedList<Tema>();
        for(Tema t : aux ){
            if(t.getIdcategoria().equals(categoria)){
               nueva.add(t);
            }
        }
        
        temas = nueva;
        
    }

    

    public String busca(){
      List<Tema> aux = temacontrolador.findTemaEntities();
        List<Tema> nueva = new LinkedList<Tema>();
        for(Tema t : aux ){
            if(t.getContenido().toLowerCase().contains(busqueda.toLowerCase())){
               nueva.add(t);
            }
        }
        
        temas = nueva; 
        return "http://localhost:8084/AbstractScienceSociety/index.xhtml";
    }
    
}
