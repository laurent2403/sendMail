package com.cegim.emailcegim;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class Test {

    public Options build() {
        Options options = new Options();

        // creations des differentes options
        Option optionHostName = new Option("host", "hostname", true, "Nom de l'hôte");
        optionHostName.setRequired(true);

        Option optionSubject = new Option("port", "smtpport", true, "port SMTP");
        optionSubject.setRequired(true);

        Option optionFrom = new Option("f", "from", true, "From");
        optionFrom.setRequired(true);

        Option optionMultipleArgs = new Option("t", "test", false, "test multiple args");
        optionMultipleArgs.setArgs(3);
        optionMultipleArgs.setArgName("hostname> <community> <oid");

        Option optionSeparator = new Option("S", "separator", true, "separator");
        optionSeparator.setArgName("arg1, arg2, arg3");
        optionSeparator.setArgs(3);
        optionSeparator.setDescription("Test Separator");
        // final char sequence = ',';
        // optionSeparator.setValueSeparator(sequence);

        Option optionSetType = new Option("T", "setType", true, "testType");
        optionSetType.setType(Integer.class);
        optionSetType.setRequired(true);

        options.addOption(optionHostName);
        options.addOption(optionSubject);
        options.addOption(optionFrom);
        options.addOption(optionMultipleArgs);
        options.addOption(optionSeparator);
        options.addOption(optionSetType);

        return options;
    }

}
