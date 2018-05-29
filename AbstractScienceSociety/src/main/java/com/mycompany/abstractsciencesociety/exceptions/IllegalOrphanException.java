/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.abstractsciencesociety.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * IllegalOrphanException.
 * @author abstract
 */
public class IllegalOrphanException extends Exception {
    /**
     * Mensajes.
     */
    private final List<String> messages;
    /**
     * IllegalOrphanException.
     * @param messagesAux
     */
    public IllegalOrphanException(final List<String> messagesAux) {
        super("String");
        if (messagesAux == null) {
            this.messages = new ArrayList<String>();
        } else {
            this.messages = messagesAux;
        }
    }

    /**
     * Get all Messages.
     * @return all messages
     */
    public List<String> getMessages() {
        return messages;
    }
}
