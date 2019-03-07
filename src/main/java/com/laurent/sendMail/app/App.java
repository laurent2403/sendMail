package com.laurent.sendMail.app;

import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.UnrecognizedOptionException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Classe de lancement de l'application.
 * 
 *  
 * @author Laurent Baillehache 
 */
public final class App {

    /**
     * Nom du programme.
     */
    private static final String APP_NAME = "sendEmail";
    /**
     * Séparateur d'adresses email.
     */
    private static final String ADDRESS_SEPARATOR = ",";
    /**
     * Séparateur de fichiers.
     */
    private static final String FILE_SEPARATOR = ";";

    /**
     * Constructeur privé car elle ne doit pas etre isntanciable.
     */
    private App() {
        // rien a faire
    }

    /**
     * Fonction principale de l'application.
     * 
     * @param args
     *            les paramètres de la ligne de commande
     */
    public static void main(final String[] args) {

        // code de retour du programme
        int result = 0;

        // creation des toutes premières options
        final Options firstOptions = new Options();
        final Option optionHelp = new Option("H", OptionName.HELP, false, "Affiche l'aide");
        final Option optionVersion = new Option("V", OptionName.VERSION, false, "Affiche le numéro de version");
        firstOptions.addOption(optionHelp);
        firstOptions.addOption(optionVersion);

        // classe en charge de la creation des options de la ligne de commande
        final OptionsBuilder builder = new OptionsBuilder();
        // creation des options
        final Options options = builder.build();

        // recuperation de l'aide
        final String header = getHelpHeader().toString();
        // creation du formatter d'aide
        final HelpFormatter formater = new HelpFormatter();

        try {
            // creation du parser de la ligne de commande
            final CommandLineParser parser = new DefaultParser();
            // parsage de la ligne de commande
            final CommandLine cmdLine = parser.parse(firstOptions, args, true);

            if (cmdLine.getOptions().length > 0) {
                // la ligne de commande contient les toutes premieres options

                // gestion de l'option d'aide
                if (cmdLine.hasOption(OptionName.HELP)) {
                    formater.printHelp(APP_NAME, header, options, null, true);
                }

                // gestion de l'option version
                if (cmdLine.hasOption(OptionName.VERSION)) {
                    System.out.println("CEGIM Informatique");
                    System.out.println("Numéro de version : " + App.class.getPackage().getImplementationVersion());
                }

            } else {
                result = handleOptions(options, args);
            }

        } catch (ParseException pex) {
            System.out.println("Erreur dans l'analyse de la ligne de commande : " + pex.getMessage());
            formater.printHelp(APP_NAME, header, options, null, true);
            result = ErrorCode.PARSING_ERROR;
        }

        System.exit(result);

    }

    /**
     * Retourne les informations concernant l'utilisation de l'application.
     * 
     * @return les informations concernant l'utilisation de l'application
     */
    private static StringBuilder getHelpHeader() {

        final String newLine = System.getProperty("line.separator");

        final StringBuilder header = new StringBuilder();
        header.append("Application d'envoi de mail en ligne de commande." + newLine);
        header.append("Ce programme s'appuie sur la librairie Apache Commons Email." + newLine);
        header.append("La plupart des paramètres de ce programme font appel à leur équivalent ");
        header.append("dans cette librairie." + newLine);
        header.append("Guide d'utilisation de la librairie :" + newLine);
        header.append("https://commons.apache.org/proper/commons-email/userguide.html" + newLine);
        header.append(newLine);
        header.append("Exemple d'utilisation pour un compte classique (port SMTP par défaut, authentification simple) :"
                + newLine);
        header.append(APP_NAME);
        header.append(" --" + OptionName.HOSTNAME + "=\"hote_smtp\"");
        header.append(" --" + OptionName.AUTHENTIFICATION_USER + "=\"username\"");
        header.append(" --" + OptionName.AUTHENTIFICATION_PASSWORD + "=\"password\"");
        header.append(" --" + OptionName.FROM + "=\"user@boo.com\"");
        header.append(" --" + OptionName.SUBJECT + "=\"TestMail\"");
        header.append(" --" + OptionName.MESSAGE + "=\"This is a test mail ... :-)\"");
        header.append(" --" + OptionName.TO + "=\"foo@bar.com\"");
        header.append(" --" + OptionName.CHARSET + "=\"utf-8\"");
        header.append(newLine);
        header.append(newLine);
        header.append("Exemple d'utilisation pour un compte gmail :" + newLine);
        header.append(APP_NAME);
        header.append(" --" + OptionName.HOSTNAME + "=\"smtp.gmail.com\"");
        header.append(" --" + OptionName.SMTP_PORT + "=\"587\"");
        header.append(" --" + OptionName.AUTHENTIFICATION_USER + "=\"user@gmail.com\"");
        header.append(" --" + OptionName.AUTHENTIFICATION_PASSWORD + "=\"psswd\"");
        header.append(" --" + OptionName.SSL_ON_CONNECT);
        header.append(" --" + OptionName.FROM + "=\"user@gmail.com\"");
        header.append(" --" + OptionName.SUBJECT + "=\"TestMail\"");
        header.append(" --" + OptionName.MESSAGE + "=\"This is a test mail ... :-)\"");
        header.append(" --" + OptionName.TO + "=\"foo@bar.com\"");
        header.append(" --" + OptionName.CHARSET + "=\"utf-8\"");
        header.append(newLine);
        header.append(newLine);

        return header;
    }

    /**
     * Gère les options.
     * 
     * @param pOptions
     *            les options
     * @param args
     *            les paramètres de la ligne de commande
     * @return le code d'erreur
     */
    private static int handleOptions(final Options pOptions, final String[] args) {

        int result = 0;

        try {
            // creation du parser de la ligne de commande
            final CommandLineParser parser = new DefaultParser();
            // parsage de la ligne de commande
            final CommandLine cmdLine = parser.parse(pOptions, args);

            // creation de l'email de type html
            final HtmlEmail email = new HtmlEmail();

            // gestion de l'option hostname
            if (cmdLine.hasOption(OptionName.HOSTNAME)) {
                email.setHostName(cmdLine.getOptionValue(OptionName.HOSTNAME));
            }

            // gestion de l'option du port SMTP
            if (cmdLine.hasOption(OptionName.SMTP_PORT)) {
                final String smtpPort = cmdLine.getOptionValue(OptionName.SMTP_PORT);
                try {
                    int port = Integer.parseInt(smtpPort);
                    email.setSmtpPort(port);
                } catch (NumberFormatException nfe) {
                    throw new IllegalArgumentException("Numéro de port smtp invalide", nfe);
                }
            }

            // gestion de l'option de l'utilisateur
            String username = null;
            if (cmdLine.hasOption(OptionName.AUTHENTIFICATION_USER)) {
                username = cmdLine.getOptionValue(OptionName.AUTHENTIFICATION_USER);
            }

            // gestion de l'option du mot de passe
            String password = null;
            if (cmdLine.hasOption(OptionName.AUTHENTIFICATION_PASSWORD)) {
                password = cmdLine.getOptionValue(OptionName.AUTHENTIFICATION_PASSWORD);
            }

            // gestion de l'authentification
            if (StringUtils.isNotBlank(username)) {
                email.setAuthenticator(new DefaultAuthenticator(username, password));
            }

            // gestion de l'option de l'adresse email de l'expediteur
            if (cmdLine.hasOption(OptionName.FROM)) {
                try {
                    email.setFrom(cmdLine.getOptionValue(OptionName.FROM));
                } catch (EmailException eme) {
                    throw new InvalidEmailAddressException("Adresse email invalide dans l'option " + OptionName.FROM,
                            eme);
                }
            }

            // gestion de l'option du sujet
            if (cmdLine.hasOption(OptionName.SUBJECT)) {
                email.setSubject(cmdLine.getOptionValue(OptionName.SUBJECT));
            }

            // gestion de l'option du message
            if (cmdLine.hasOption(OptionName.MESSAGE)) {
                final String msg = cmdLine.getOptionValue(OptionName.MESSAGE);
                email.setHtmlMsg(msg);
            }

            // gestion de l'option du message au format texte
            if (cmdLine.hasOption(OptionName.TEXT_MESSAGE)) {
                final String txtMsg = cmdLine.getOptionValue(OptionName.TEXT_MESSAGE);
                email.setTextMsg(txtMsg);
            }

            // gestion de l'option des adresses mail des destinataires
            if (cmdLine.hasOption(OptionName.TO)) {
                final String dest = cmdLine.getOptionValue(OptionName.TO);
                final String[] tabDest = StringUtils.split(dest, ADDRESS_SEPARATOR);
                final List<String> listDest = Arrays.asList(tabDest);
                try {
                    for (String curMail : listDest) {
                        email.addTo(curMail);
                    }
                } catch (EmailException eme) {
                    throw new InvalidEmailAddressException(
                            "Adresse(s) email invalide(s) dans l'option " + OptionName.TO, eme);
                }
            }

            // gestion de l'option des adresses mail des destinataires en copie
            if (cmdLine.hasOption(OptionName.CC)) {
                final String copyCarbon = cmdLine.getOptionValue(OptionName.CC);
                final String[] tabCopyCarbon = StringUtils.split(copyCarbon, ADDRESS_SEPARATOR);
                final List<String> listCopyCarbon = Arrays.asList(tabCopyCarbon);
                try {
                    for (String curMail : listCopyCarbon) {
                        email.addCc(curMail);
                    }
                } catch (EmailException eme) {
                    throw new InvalidEmailAddressException(
                            "Adresse(s) email invalide(s) dans l'option " + OptionName.CC, eme);
                }
            }

            // gestion de l'option des adresses mail des destinataires en copie
            // cachee
            if (cmdLine.hasOption(OptionName.BCC)) {
                final String bcc = cmdLine.getOptionValue(OptionName.BCC);
                final String[] tabBcc = StringUtils.split(bcc, ADDRESS_SEPARATOR);
                final List<String> listBcc = Arrays.asList(tabBcc);
                try {
                    for (String curMail : listBcc) {
                        email.addBcc(curMail);
                    }
                } catch (EmailException eme) {
                    throw new InvalidEmailAddressException(
                            "Adresse(s) email invalide(s) dans l'option " + OptionName.BCC, eme);
                }
            }

            // gestion de l'option des adresses mail des destinataires de type
            // repondre a
            if (cmdLine.hasOption(OptionName.REPLY_TO)) {
                final String replyTo = cmdLine.getOptionValue(OptionName.REPLY_TO);
                final String[] tabReplyTo = StringUtils.split(replyTo, ADDRESS_SEPARATOR);
                final List<String> listReplyTo = Arrays.asList(tabReplyTo);
                try {
                    for (String curMail : listReplyTo) {
                        email.addReplyTo(curMail);
                    }
                } catch (EmailException eme) {
                    throw new InvalidEmailAddressException(
                            "Adresse(s) email invalide(s) dans l'option " + OptionName.REPLY_TO, eme);
                }
            }

            // gestion de l'option de l'adresse de retour des mails non
            // delivrables
            if (cmdLine.hasOption(OptionName.BOUNCE_ADDRESS)) {
                email.setBounceAddress(cmdLine.getOptionValue(OptionName.BOUNCE_ADDRESS));
            }

            // gestion de l'option du charset
            if (cmdLine.hasOption(OptionName.CHARSET)) {
                email.setCharset(cmdLine.getOptionValue(OptionName.CHARSET));
            }

            // gestion de l'option debug
            if (cmdLine.hasOption(OptionName.DEBUG)) {
                email.setDebug(true);
            }

            // gestion de l'option de la date d'envoi
            if (cmdLine.hasOption(OptionName.SENT_DATE)) {
                final String date = cmdLine.getOptionValue(OptionName.SENT_DATE);
                final DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyyMMddHHmmss");
                try {
                    final DateTime dateTime = formatter.parseDateTime(date);
                    final Date sentDate = dateTime.toDate();
                    email.setSentDate(sentDate);
                } catch (Exception ex) {
                    throw new IllegalArgumentException("Date d'envoi au format invalide", ex);
                }
            }

            // gestion de l'option du port ssl smtp
            if (cmdLine.hasOption(OptionName.SSL_SMTP_PORT)) {
                email.setSslSmtpPort(cmdLine.getOptionValue(OptionName.SSL_SMTP_PORT));
            }

            // gestion de l'option de verification de l'identite du serveur
            if (cmdLine.hasOption(OptionName.SSL_CHECK_SERVER_IDENTITY)) {
                email.setSSLCheckServerIdentity(true);
            }

            // gestion de l'option SSL a la connexion
            if (cmdLine.hasOption(OptionName.SSL_ON_CONNECT)) {
                email.setSSLOnConnect(true);
            }

            // gestion de l'option STARTTLS requise
            if (cmdLine.hasOption(OptionName.STARTTLS_REQUIRED)) {
                email.setStartTLSRequired(true);
            }

            // gestion de l'option STARTTLS active
            if (cmdLine.hasOption(OptionName.STARTTLS_ENABLED)) {
                email.setStartTLSEnabled(true);
            }

            // gestion des pieces jointes
            if (cmdLine.hasOption(OptionName.ATTACH)) {
                final String attach = cmdLine.getOptionValue("a");
                final String[] tabAttach = StringUtils.split(attach, FILE_SEPARATOR);
                final List<String> listAttach = Arrays.asList(tabAttach);
                if (!listAttach.isEmpty()) {
                    EmailAttachment attachment = null;
                    for (String curAttach : listAttach) {
                        attachment = new EmailAttachment();
                        attachment.setPath(curAttach);
                        attachment.setDisposition(EmailAttachment.ATTACHMENT);
                        email.attach(attachment);
                    }
                }
            }

            // envoi du mail
            email.send();

        } catch (UnrecognizedOptionException uoe) {
            System.out.println("Au moins une des options saisie n'est pas reconnue : " + uoe.getMessage());
            System.out.println("Pour afficher l'aide : " + App.APP_NAME + " --" + OptionName.HELP);
            result = ErrorCode.UNRECOGNIZED_OPTION;
        } catch (MissingOptionException moe) {
            System.out.println("Il manque au moins une des options requises : " + moe.getMessage());
            System.out.println("Pour afficher l'aide : " + App.APP_NAME + " --" + OptionName.HELP);
            result = ErrorCode.MISSING_OPTION;
        } catch (MissingArgumentException mae) {
            System.out.println("Au moins une des options saisie necessite un argument : " + mae.getMessage());
            System.out.println("Pour afficher l'aide : " + App.APP_NAME + " --" + OptionName.HELP);
            result = ErrorCode.MISSING_ARGUMENT;
        } catch (ParseException pex) {
            System.out.println("Erreur lors de l'analyse de la ligne de commande : ");
            pex.printStackTrace();
            result = ErrorCode.PARSING_ERROR;
        } catch (IllegalCharsetNameException icne) {
            System.out.println("Nom du charset invalide : " + icne.getMessage());
            result = ErrorCode.ILLEGAL_CHARSET_NAME;
        } catch (UnsupportedCharsetException uce) {
            System.out.println("Charset non supporté par la JVM courante : " + uce.getMessage());
            result = ErrorCode.UNSUPPORTED_CHARSET;
        } catch (InvalidEmailAddressException ieae) {
            System.out.println("Adresse email invalide : " + ieae.getMessage());
            result = ErrorCode.INVALID_EMAIL_ADDRESS;
        } catch (EmailException emailex) {
            System.out.println("Erreur liée au mail : ");
            emailex.printStackTrace();
            result = ErrorCode.EMAIL_ERROR;
        } catch (Exception ex) {
            System.out.println("L'exception suivante est survenue : ");
            ex.printStackTrace();
            result = ErrorCode.GENERIC_ERROR;
        }

        return result;

    }
}
