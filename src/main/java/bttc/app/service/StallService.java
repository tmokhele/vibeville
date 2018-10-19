package bttc.app.service;

import bttc.app.util.FireBaseAuthToken;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class StallService {

    static  final RestTemplate restTemplate = new RestTemplate();

    @Async
    public CompletableFuture<List<String>> getAllStallApplications() throws IOException {
        List<String> jsonObjects = new ArrayList<>();
        FireBaseAuthToken fireBaseAuthToken = new FireBaseAuthToken();
        String token = fireBaseAuthToken.invoke();
        String url = "https://bttc-cb6f6.firebaseio.com/stallPaymentInformation.json?access_token=" + token;
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) responseEntity.getBody();
        map.entrySet().forEach(e -> jsonObjects.add(JSONObject.valueToString(e.getValue())));
        return CompletableFuture.completedFuture(jsonObjects);
    }
}
