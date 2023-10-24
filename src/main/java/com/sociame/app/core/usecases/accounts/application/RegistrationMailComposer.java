package com.sociame.app.core.usecases.accounts.application;

public class RegistrationMailComposer {

    public static String compose(int pin, String verificationToken) {
        return "Your verification pin is: " + pin + "\n" +
                "Your verification token is: " + verificationToken + "\n";
    }

}
