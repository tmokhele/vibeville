package bttc.app.util;


import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;


public class PasswordUtil {

    private static final String characterString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";

    private PasswordUtil() {
    }

    static char[] possibleCharacters = (characterString).toCharArray();

    public  static String generatePassword()
    {

        return RandomStringUtils.random(8, 0, possibleCharacters.length-1,  true,  true, possibleCharacters, new SecureRandom());
    }
}
