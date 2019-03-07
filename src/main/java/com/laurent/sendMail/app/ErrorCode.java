package com.laurent.sendMail.app;

/**
 * Classe de constantes definissant les differents types d'erreurs que
 * l'application peut retourner.
 * 
 *  
 * @author Laurent Baillehache 
 */
public final class ErrorCode {

    /**
     * Option non reconnue dans la ligne de commande.
     */
    public static final int UNRECOGNIZED_OPTION = 1;
    /**
     * Argument d'option manquant dans la ligne de commande.
     */
    public static final int MISSING_ARGUMENT = 2;
    /**
     * Option manquante dans la ligne de commande.
     */
    public static final int MISSING_OPTION = 3;
    /**
     * Erreur lors de l'analyse de la ligne de commande.
     */
    public static final int PARSING_ERROR = 4;

    /**
     * Nom du charset invalide.
     */
    public static final int ILLEGAL_CHARSET_NAME = 10;
    /**
     * Charset non supporte.
     */
    public static final int UNSUPPORTED_CHARSET = 11;
    /**
     * Adresse email invalide.
     */
    public static final int INVALID_EMAIL_ADDRESS = 12;
    /**
     * Erreur liée a l'envoi de mail.
     */
    public static final int EMAIL_ERROR = 20;
    /**
     * Erreur générique.
     */
    public static final int GENERIC_ERROR = 30;

    /**
     * Constructeur privé car classe utilitaire.
     */
    private ErrorCode() {
        // rien a faire
    }

}
