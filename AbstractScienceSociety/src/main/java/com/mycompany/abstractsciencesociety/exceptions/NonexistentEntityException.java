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
public class NonexistentEntityException extends Exception {

    /**
     * NonExistentEntityException.
     * @param message
     * @param cause
     */
    public NonexistentEntityException(
        final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * NonExistentEntityException.
     * @param message
     */
    public NonexistentEntityException(final String message) {
        super(message);
    }
}
