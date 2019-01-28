package bttc.app.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;


@Configuration
public class FirebaseConfig {

    @Value("${firebase.db.url}")
    private String databaseUrl;

    @Value("${firebase.config.file}")
    private String configPath;

    @Autowired
    ResourceLoader resourceloader;

    @Bean
    public DatabaseReference firebaseDatabase() {
       return FirebaseDatabase.getInstance().getReference();
    }

    @PostConstruct
    public void init() throws IOException {
        InputStream serviceAccount =  getClass().getResourceAsStream("/serviceAccountKey.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(databaseUrl).build();
        FirebaseApp.initializeApp(options);
    }
}
