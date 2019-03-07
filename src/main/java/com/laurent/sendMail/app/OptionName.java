package com.laurent.sendMail.app;

/**
 * Classe de constantes definissant les noms des options.
 * 
 *  
 */
public final class OptionName {

    /**
     * Option du nom d'hôte.
     */
    public static final String HOSTNAME = "hostname";

    /**
     * Option du port SMTP.
     */
    public static final String SMTP_PORT = "smtpport";

    /**
     * Option de l'utilisateur pour l'authentification.
     */
    public static final String AUTHENTIFICATION_USER = "user";

    /**
     * Option du mot de passe pour l'authentification.
     */
    public static final String AUTHENTIFICATION_PASSWORD = "password";

    /**
     * Option de l'adresse mail de l'expediteur.
     */
    public static final String FROM = "from";

    /**
     * Option du sujet.
     */
    public static final String SUBJECT = "subject";

    /**
     * Option du message.
     */
    public static final String MESSAGE = "msg";

    /**
     * Option du message au format texte.
     */
    public static final String TEXT_MESSAGE = "txtmsg";

    /**
     * Option des adresses mail des destinataires.
     */
    public static final String TO = "to";

    /**
     * Option des adresses mail des destinataires en copie.
     */
    public static final String CC = "cc";

    /**
     * Option des adresses mail des destinataires en copie cachee.
     */
    public static final String BCC = "bcc";

    /**
     * Option des adresses mail des destinataires de type repondre a.
     */
    public static final String REPLY_TO = "replyto";

    /**
     * Option de l'adresse de retour des maisl non delivrables.
     */
    public static final String BOUNCE_ADDRESS = "bounceaddress";

    /**
     * Option du charset.
     */
    public static final String CHARSET = "charset";

    /**
     * Option debug.
     */
    public static final String DEBUG = "debug";

    /**
     * Option date d'envoi.
     */
    public static final String SENT_DATE = "sentdate";

    /**
     * Option port SSL SMTP.
     */
    public static final String SSL_SMTP_PORT = "sslsmtpport";

    /**
     * Option verification de l'identite du serveur.
     */
    public static final String SSL_CHECK_SERVER_IDENTITY = "sslcheckserveridentity";

    /**
     * Option SSL à la connexion.
     */
    public static final String SSL_ON_CONNECT = "sslonconnect";

    /**
     * Option STARTTLS requise.
     */
    public static final String STARTTLS_REQUIRED = "starttlsrequired";

    /**
     * Option STARTTLS active.
     */
    public static final String STARTTLS_ENABLED = "starttlsenabled";

    /**
     * Option des pieces jointes.
     */
    public static final String ATTACH = "attach";

    /**
     * Option de l'aide.
     */
    public static final String HELP = "help";

    /**
     * Option du numero de version.
     */
    public static final String VERSION = "version";

    /**
     * Constructeur privé car classe utilitaire.
     */
    private OptionName() {
        // rien a faire
    }

}
