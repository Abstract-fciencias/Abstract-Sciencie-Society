/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.exceptions;

/**
 *
 * @author edervs
 */
public class PreexistingEntityException extends Exception {

    /**
     * PreexistingEntityException.
     * @param message
     * @param cause
     */
    public PreexistingEntityException(final String message, final Throwable cause) {
        super(message, cause);
    }
    
    /**
     * PreexistingEntityException.
     * @param message
     */
    public PreexistingEntityException(final String message) {
        super(message);
    }
}
