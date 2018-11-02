package bttc.app.repository;

import bttc.app.exception.RestTemplateResponseErrorHandler;
import bttc.app.model.SystemUser;
import bttc.app.util.Token;
import bttc.app.util.UserSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Repository
public class SystemUserRepository {

    private static final Logger logger = LoggerFactory.getLogger(SystemUserRepository.class);

    @Value("${firebase.api.key}")
    private String apiKey;

    @Value("${firebase.identity.key}")
    private String apiUrl;

    @Value("${firebase.db.url}")
    private String dbUrl;

    private RestTemplate restTemplate;

    @Autowired
    public SystemUserRepository(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public SystemUser addUser(SystemUser systemUser)
    {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SystemUser.class, new UserSerializer())
                .create();
        SystemUser user = gson.fromJson(gson.toJson(systemUser),SystemUser.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append("userInformation.json");
        ResponseEntity<SystemUser> userResponseEntity = restTemplate.postForEntity(stringBuilder.toString(), user, SystemUser.class);
        return userResponseEntity.getBody();
    }
   public SystemUser updateUser(SystemUser systemUser)
    {

        return null;
    }
    public SystemUser getUser(String userId)
    {
        String token = Token.invoke();
        StringBuilder stringBuilder = new StringBuilder();
        List<SystemUser> userList = new ArrayList<>();
        Gson g = new Gson();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("userInformation.json?access_token={0}&orderBy=\"uid\"&equalTo=\"{1}\"", token, userId));
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(stringBuilder.toString(), Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) forEntity.getBody();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            userList.add(g.fromJson(JSONObject.valueToString(e.getValue()), SystemUser.class));
        }
        return userList.get(0);
    }
    public CompletableFuture<List<String>>  getAllSystemUsers()
    {
        List<String> jsonObjects = new ArrayList<>();
        String token = Token.invoke();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(apiUrl);
        stringBuilder.append(MessageFormat.format("userInformation.json?access_token={0}",token));
        URI uri = null;
        try {
             uri = new URI(stringBuilder.toString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
//        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) responseEntity.getBody();
//        map.entrySet().forEach(e -> jsonObjects.add(JSONObject.valueToString(e.getValue())));
//        return CompletableFuture.completedFuture(jsonObjects);
        return null;
    }


}