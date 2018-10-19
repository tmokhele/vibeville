package bttc.app.repository;

import bttc.app.exception.RestTemplateResponseErrorHandler;
import bttc.app.model.Performance;
import bttc.app.util.Token;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Repository
public class PerformanceRepository {

    @Value("${firebase.api.key}")
    private String apiKey;

    @Value("${firebase.identity.key}")
    private String apiUrl;

    @Value("${firebase.db.url}")
    private String dbUrl;

    private RestTemplate restTemplate;

    @Autowired
    public PerformanceRepository(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public Performance addPerformance(Performance performance){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append("performance.json");
        ResponseEntity<Performance> userResponseEntity = restTemplate.postForEntity(stringBuilder.toString(), performance, Performance.class);
        return userResponseEntity.getBody();
    }

    public Performance updatePerformance(Performance performance) {
        String token = Token.invoke();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("performance.json?access_token={0}",token));
        restTemplate.put(stringBuilder.toString(), performance);
        return performance;
    }

    public Performance getPerformance(String id) {
        String token = Token.invoke();
        StringBuilder stringBuilder = new StringBuilder();
        List<Performance> performances = new ArrayList<>();
        Gson g = new Gson();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("performance.json?access_token={0}&orderBy=\"uid\"&equalTo=\"{1}\"", token, id));
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(stringBuilder.toString(), Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) forEntity.getBody();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            performances.add(g.fromJson(JSONObject.valueToString(e.getValue()), Performance.class));
        }
        return performances.get(0);
    }

    public CompletableFuture<List<String>> getAll() {
        List<String> jsonObjects = new ArrayList<>();
        String token = Token.invoke();
        String url = "https://tebogo-chat.firebaseio.com/performance.json?access_token=" + token;
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) responseEntity.getBody();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            jsonObjects.add(JSONObject.valueToString(e.getValue()));
        }
        return CompletableFuture.completedFuture(jsonObjects);
    }
}
