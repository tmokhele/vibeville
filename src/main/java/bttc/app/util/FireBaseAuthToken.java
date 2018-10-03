package bttc.app.util;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class FireBaseAuthToken {


    private String configPath ="serviceAccountKey.json";


    public  String invoke() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(configPath).getFile());
        FileInputStream serviceAccount = new FileInputStream(file);
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
