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
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @CachePut("users")
    public SystemUser addUser(SystemUser systemUser) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SystemUser.class, new UserSerializer())
                .create();
        SystemUser user = gson.fromJson(gson.toJson(systemUser), SystemUser.class);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("userInformation.json?access_token={0}", Token.invoke()));
        ResponseEntity<SystemUser> userResponseEntity = restTemplate.postForEntity(stringBuilder.toString(), user, SystemUser.class);
        return userResponseEntity.getBody();
    }

    @CachePut("users")
    public SystemUser updateUser(SystemUser systemUser) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append("userInformation/");
        stringBuilder.append(systemUser.getId());
        stringBuilder.append(MessageFormat.format(".json?access_token={0}", Token.invoke()));
        return restTemplate.patchForObject(stringBuilder.toString(), systemUser, SystemUser.class);
    }


    public SystemUser getUser(String userId) {
        String token = Token.invoke();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        List<SystemUser> userList = new ArrayList<>();
        Gson g = new Gson();
        stringBuilder.append(MessageFormat.format("userInformation.json?access_token={0}&orderBy=\"uid\"&equalTo=\"{1}\"", token, userId));
        getObjects(userList, g, stringBuilder);
        return userList.get(0);
    }

    @Cacheable("users")
    public List<SystemUser> getAllSystemUsers() {
        List<SystemUser> userList = new ArrayList<>();
        Gson gson = new Gson();
        String token = Token.invoke();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("userInformation.json?access_token={0}", token));
        getObjects(userList, gson, stringBuilder);
        return userList;
    }

    private void getObjects(List<SystemUser> userList, Gson gson, StringBuilder stringBuilder) {
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(stringBuilder.toString(), Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) responseEntity.getBody();
        for (Map.Entry<String, Object> e : map.entrySet()) {
            SystemUser e1 = gson.fromJson(JSONObject.valueToString(e.getValue()), SystemUser.class);
            e1.setId(e.getKey());
            userList.add(e1);
        }
    }


    public boolean deleteUserInformation(SystemUser systemUser) {
        String token = Token.invoke();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("userInformation/{0}.json?access_token={1}", systemUser.getId(), token));
        restTemplate.delete(stringBuilder.toString());
        return true;
    }
}
