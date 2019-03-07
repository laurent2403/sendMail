package com.laurent.sendMail.app;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

/**
 * Gestionnaire de fabrication de l'ensemble des options de la ligne de
 * commande.
 * 
 *  
 * @author Laurent Baillehache 
 */
public class OptionsBuilder {

    /**
     * Construit l'ensemble des options.
     * 
     * @return l'ensemble des options
     */
    public final Options build() {

        // creation de la liste des options
        final Options options = new Options();

        final Option optionHostName = new Option("h", OptionName.HOSTNAME, true, "Nom de l'hôte du serveur SMTP");
        optionHostName.setRequired(true);

        final Option optionSmtpPort = new Option("P", OptionName.SMTP_PORT, true, "Port SMTP");

        final Option optionUser = new Option("u", OptionName.AUTHENTIFICATION_USER, true,
                "Nom d'utilisateur pour l'authentification");

        final Option optionPassword = new Option("p", OptionName.AUTHENTIFICATION_PASSWORD, true,
                "Mot de passe de l'utilisateur pour l'authentification");

        final Option optionFrom = new Option("f", OptionName.FROM, true, "Adresse email de l'expéditeur");
        optionFrom.setRequired(true);

        final Option optionSubject = new Option("s", OptionName.SUBJECT, true, "Sujet du mail");

        final Option optionMessage = new Option("m", OptionName.MESSAGE, true,
                "Contenu du mail (soit au format texte, soit au format html)");

        final Option optionTxtMsg = new Option("t", OptionName.TEXT_MESSAGE, true,
                "Contenu du mail au format texte dans le cas où l'html n'est pas supporté "
                        + "par la messagerie du destinataire du mail");

        final Option optionTo = new Option("to", OptionName.TO, true,
                "Liste des adresses mail (séparé par des virgules) des destinataires");

        final Option optionCc = new Option("cc", OptionName.CC, true,
                "Liste des adresses mail (séparé par des virgules) des destinataires de type copie carbone");

        final Option optionBcc = new Option("bcc", OptionName.BCC, true,
                "Liste des adresses mail (séparé par des virgules) des destinataires de type copie cachée");

        final Option optionReplyTo = new Option("replyto", OptionName.REPLY_TO, true,
                "Liste des adresses mail (séparé par des virgules) des destinataires de type Répondre à");

        final Option optionBounceAddress = new Option("B", OptionName.BOUNCE_ADDRESS, true,
                "Adresse a laquelle les messages non délivrables seront retournés");

        final Option optionCharset = new Option("C", OptionName.CHARSET, true,
                "Encodage du message (valeurs possibles : utf-8, us-ascii, iso-8859-1, koi8-r");

        final Option optionDebug = new Option("d", OptionName.DEBUG, false, "Mode debug");

        final Option optionSendDate = new Option("d", OptionName.SENT_DATE, true,
                "Date d'envoi au format yyyyMMddHHmmss");

        final Option optionSslSmtpPort = new Option("L", OptionName.SSL_SMTP_PORT, true,
                "Port SSL utilisé pour le transport SMTP");

        final Option optionSslCheckSrvIdentity = new Option("I", OptionName.SSL_CHECK_SERVER_IDENTITY, false,
                "Force la vérification de l'identité du serveur");

        final Option optionSsLOnConnect = new Option("S", OptionName.SSL_ON_CONNECT, false,
                "Active l'encryption SSL/TLS");

        final Option optionStartTlsRequired = new Option("R", OptionName.STARTTLS_REQUIRED, false,
                "Active la nécessité de l'encription STARTTLS");

        final Option optionStartTlsEnabled = new Option("E", OptionName.STARTTLS_ENABLED, false,
                "Active l'encryption STARTTLS");

        final Option optionAttach = new Option("a", OptionName.ATTACH, true,
                "Liste des chemins des pièces jointes (séparé par des ;)");

        final Option optionHelp = new Option("H", OptionName.HELP, false, "Affiche l'aide");

        final Option optionVersion = new Option("V", OptionName.VERSION, false, "Affiche le numéro de version");

        // ajout des instances d'option
        options.addOption(optionHostName);
        options.addOption(optionSmtpPort);

        options.addOption(optionUser);
        options.addOption(optionPassword);

        options.addOption(optionFrom);
        options.addOption(optionSubject);
        options.addOption(optionMessage);
        options.addOption(optionTxtMsg);

        options.addOption(optionTo);
        options.addOption(optionCc);
        options.addOption(optionBcc);
        options.addOption(optionReplyTo);

        options.addOption(optionBounceAddress);
        options.addOption(optionCharset);
        options.addOption(optionDebug);
        options.addOption(optionSendDate);

        options.addOption(optionSslSmtpPort);
        options.addOption(optionSslCheckSrvIdentity);
        options.addOption(optionSsLOnConnect);
        options.addOption(optionStartTlsRequired);
        options.addOption(optionStartTlsEnabled);

        options.addOption(optionAttach);

        options.addOption(optionHelp);
        options.addOption(optionVersion);

        return options;
    }
}
