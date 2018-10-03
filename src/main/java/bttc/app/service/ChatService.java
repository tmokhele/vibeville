package bttc.app.service;

import bttc.app.util.FireBaseAuthToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ChatService {


    static  final  RestTemplate restTemplate = new RestTemplate();



    @Async
    public CompletableFuture<List<String>> getAllChats() throws IOException {
        List<String> jsonObjects = new ArrayList<>();
        FireBaseAuthToken fireBaseAuthToken = new FireBaseAuthToken();
        String token = fireBaseAuthToken.invoke();
        String url = "https://tebogo-chat.firebaseio.com/chats.json?access_token=" + token;
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) responseEntity.getBody();
        map.entrySet().forEach(e -> {
            jsonObjects.add(JSONObject.valueToString(e.getValue()));
        });
        return CompletableFuture.completedFuture(jsonObjects);
    }


}
