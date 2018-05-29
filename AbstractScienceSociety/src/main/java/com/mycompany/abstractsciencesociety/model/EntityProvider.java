/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author edervs
 */
public class EntityProvider {

    /**
     * EntityManagerFactory.
     */
    private static EntityManagerFactory _emf;

    /**
     * EntityManagerFactory.
     * @return _emf
     */
    public static EntityManagerFactory provider() {
        if (_emf == null) {
            _emf = Persistence.createEntityManagerFactory("PersistenceUnit");
        }
        return _emf;
    }

}
