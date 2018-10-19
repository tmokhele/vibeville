package bttc.app.util;


import org.apache.commons.lang3.RandomStringUtils;

import java.security.SecureRandom;


public class PasswordUtil {

    private PasswordUtil() {
    }

    static char[] possibleCharacters = (new String("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?")).toCharArray();

    public  static String generatePassword()
    {

        return RandomStringUtils.random(8, 0, possibleCharacters.length-1,  true,  true, possibleCharacters, new SecureRandom());
    }
}
