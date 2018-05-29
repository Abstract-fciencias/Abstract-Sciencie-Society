package com.mycompany.abstractsciencesociety.model;

import com.mycompany.abstractsciencesociety.model.Tema;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-29T16:01:18")
@StaticMetamodel(Usuario1.class)
public class Usuario1_ { 

    public static volatile CollectionAttribute<Usuario1, Tema> temaCollection;
    public static volatile SingularAttribute<Usuario1, String> anioingreso;
    public static volatile SingularAttribute<Usuario1, String> tipo;
    public static volatile SingularAttribute<Usuario1, String> correo;
    public static volatile SingularAttribute<Usuario1, Boolean> confirmado;
    public static volatile SingularAttribute<Usuario1, String> carrera;
    public static volatile SingularAttribute<Usuario1, String> nombre;
    public static volatile SingularAttribute<Usuario1, Integer> idusuario;
    public static volatile SingularAttribute<Usuario1, String> contraseña;

}