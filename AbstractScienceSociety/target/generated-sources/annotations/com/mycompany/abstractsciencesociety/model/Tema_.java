package com.mycompany.abstractsciencesociety.model;

import com.mycompany.abstractsciencesociety.model.Categoria;
import com.mycompany.abstractsciencesociety.model.Usuario;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-29T18:40:54")
@StaticMetamodel(Tema.class)
public class Tema_ { 

    public static volatile SingularAttribute<Tema, String> contenido;
    public static volatile SingularAttribute<Tema, Date> fechapublicacion;
    public static volatile SingularAttribute<Tema, String> disponibilidad;
    public static volatile SingularAttribute<Tema, Integer> idtema;
    public static volatile SingularAttribute<Tema, Categoria> idcategoria;
    public static volatile SingularAttribute<Tema, Usuario> idusuario;

}