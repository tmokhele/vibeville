package bttc.app.service.impl;

import bttc.app.model.FileUpload;
import bttc.app.service.StorageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.api.gax.paging.Page;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.http.HttpTransportOptions;
import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.*;

@Service
public class StorageServiceImpl implements StorageService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${vibeville.mediamanager.host}")
    private String vibevilleMediaManagerHost;

    @Override
    public boolean uploadFiles(List<FileUpload> fileUploads) throws IOException {
        StorageOptions storageOptions = getStorageOptions();
        Storage s = storageOptions.getService();
        for (FileUpload fileUpload : fileUploads) {
            BlobId blobId = BlobId.of("bttc-cb6f6.appspot.com", String.format("%s/%s", fileUpload.getDocName(), fileUpload.getFileName()));
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(fileUpload.getFileType()).build();
            s.create(blobInfo, fileUpload.getFile());
        }
        return true;

    }


    @Override
    public Map<String, String> getFiles(String documentType) throws IOException {
        StorageOptions storageOptions = getStorageOptions();
        Storage s = storageOptions.getService();
        Bucket bucket = s.get("bttc-cb6f6.appspot.com");
        Page<Blob> image = bucket.list(Storage.BlobListOption.prefix(documentType));
        Iterable<Blob> blobs = image.iterateAll();
        Map<String, String> bytes = new HashMap<>();
        blobs.forEach(b -> {
            if (!b.getMediaLink().isEmpty())
                bytes.put(b.getName(), b.getMediaLink());
        });
        return bytes;
    }


    private StorageOptions getStorageOptions() throws IOException {
        InputStream serviceAccount = TypeReference.class.getResourceAsStream("/serviceAccountKey.json");

        GoogleCredentials googleCred = GoogleCredentials.fromStream(serviceAccount);
        GoogleCredentials scoped = googleCred.createScoped(
                Arrays.asList(
                        "https://www.googleapis.com/auth/devstorage.full_control"
                )
        );
        HttpTransportOptions transportOptions = StorageOptions.getDefaultHttpTransportOptions();
        transportOptions = transportOptions.toBuilder().setConnectTimeout(60000).setReadTimeout(60000)
                .build();
        return StorageOptions.newBuilder()
                .setCredentials(scoped)
                .setTransportOptions(transportOptions)
                .setProjectId("bttc-cb6f6")
                .build();
    }


    @Override
    public Map<String, String> getCloudinaryFiles(String documentType) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MessageFormat.format("{0}/{1}", vibevilleMediaManagerHost, documentType));
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(stringBuilder.toString(), Map.class);
        Map<String, String> map = (Map<String, String>) forEntity.getBody();
        return map;
    }

    @Override
    public List<String> upLoadCloudinaryFiles(List<FileUpload> fileUploads) {
        return restTemplate.postForEntity(vibevilleMediaManagerHost, fileUploads, ArrayList.class).getBody();
    }

}
