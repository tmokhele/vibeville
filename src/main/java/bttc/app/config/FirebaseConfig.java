package bttc.app.config;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
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
    public DatabaseReference firebaseDatabse() {
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        return firebase;
    }

    @PostConstruct
    public void init() throws Exception{
        ResourceUtils.getURL(configPath).openStream();
        InputStream serviceAccount = ResourceUtils.getURL(configPath).openStream();
        FirebaseOptions options = new FirebaseOptions.Builder().setServiceAccount(serviceAccount)
                .setDatabaseUrl(databaseUrl).build();
        FirebaseApp.initializeApp(options);
    }
}
