package bttc.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Token {
    private Token() {
    }

    private static final Logger logger = LoggerFactory.getLogger(Token.class);
    static String  idToken = null;
    public static String invoke() {
        FireBaseAuthToken fireBaseAuthToken = new FireBaseAuthToken();
        try {

                idToken = fireBaseAuthToken.invoke();

        } catch (IOException e) {
            logger.error(String.format("Error getting token %s",e.getMessage()));
        }

        return idToken;
    }
}
