package bttc.app.service.impl;

import bttc.app.model.FileUpload;
import bttc.app.service.StorageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.http.HttpTransportOptions;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    @Override
    public boolean uploadFiles(List<FileUpload> fileUploads) throws IOException {
        InputStream serviceAccount = TypeReference.class.getResourceAsStream("/serviceAccountKey.json");

        GoogleCredentials googleCred = GoogleCredentials.fromStream(serviceAccount);
        GoogleCredentials scoped = googleCred.createScoped(
                Arrays.asList(
                        "https://www.googleapis.com/auth/firebase.database",
                        "https://www.googleapis.com/auth/userinfo.email",
                        "https://www.googleapis.com/auth/devstorage.full_control"
                )
        );
        HttpTransportOptions transportOptions = StorageOptions.getDefaultHttpTransportOptions();
        transportOptions = transportOptions.toBuilder().setConnectTimeout(60000).setReadTimeout(60000)
                .build();
        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setCredentials(scoped)
                .setTransportOptions(transportOptions)
                .setProjectId("bttc-cb6f6")
                .build();
        Storage s = storageOptions.getService();
        for (FileUpload fileUpload:fileUploads) {
            BlobId blobId = BlobId.of("bttc-cb6f6.appspot.com", String.format("%s/%s",fileUpload.getDocName(),fileUpload.getFileName()));
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpeg").build();
            s.create(blobInfo, fileUpload.getFile());
        }
        return true;

    }

}
