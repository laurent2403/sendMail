package com.cegim.emailcegim;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class MainTest {

    public static void main(String[] args) {
        // new Cli(args).parse();
        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("username", "password"));
            email.setSSLOnConnect(true);
            email.setFrom("lbaillehache@cegim.nc");
            email.setSubject("TestMail");
            email.setMsg("This is a test mail ... :-)");
            email.addTo("laurentbaillehache@yahoo.fr");
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
