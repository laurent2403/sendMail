package com.laurent.sendMail.app;

import java.io.Serializable;

/**
 * Cette exception est soulevée lorsqu'une adresse email est invalide.
 * 
 * @author Johan Mélin 
 * 
 */
public class InvalidEmailAddressException extends RuntimeException implements Serializable {

    /**
     * Numero de version de la classe.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur.
     */
    public InvalidEmailAddressException() {
        super();
    }

    /**
     * Constructeur avec le message en parametre.
     * 
     * @param pMessage
     *            le message de l'exception
     */
    public InvalidEmailAddressException(final String pMessage) {
        super(pMessage);
    }

    /**
     * Constructeur avec le message et la cause en parametre.
     * 
     * @param pMessage
     *            le message de l'exception
     * @param pCause
     *            l'exception a l'origine
     */
    public InvalidEmailAddressException(final String pMessage, final Throwable pCause) {
        super(pMessage, pCause);
    }
}
