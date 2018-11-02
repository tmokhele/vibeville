package bttc.app.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class FireBaseAuthToken {

    public  String invoke() throws IOException {
        InputStream serviceAccount = TypeReference.class.getResourceAsStream("/serviceAccountKey.json");
        GoogleCredential googleCred = GoogleCredential.fromStream(serviceAccount);
        GoogleCredential scoped = googleCred.createScoped(
                Arrays.asList(
                        "https://www.googleapis.com/auth/firebase.database",
                        "https://www.googleapis.com/auth/userinfo.email"
                )
        );
        scoped.refreshToken();
        return scoped.getAccessToken();
    }
}
