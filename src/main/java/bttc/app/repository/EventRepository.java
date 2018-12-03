package bttc.app.repository;

import bttc.app.exception.RestTemplateResponseErrorHandler;
import bttc.app.model.Event;
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

@Repository
public class EventRepository {

    @Value("${firebase.api.key}")
    private String apiKey;

    @Value("${firebase.identity.key}")
    private String apiUrl;

    @Value("${firebase.db.url}")
    private String dbUrl;

    private RestTemplate restTemplate;

    @Autowired
    public EventRepository(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public Event addEvent(Event event)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("event.json?access_token={0}",Token.invoke()));
        ResponseEntity<Event> userResponseEntity = restTemplate.postForEntity(stringBuilder.toString(), event, Event.class);
        return userResponseEntity.getBody();
    }

    public Event updateEvent(Event event) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append("event/");
        stringBuilder.append(event.getId());
        stringBuilder.append(MessageFormat.format(".json?access_token={0}",Token.invoke()));
        return restTemplate.patchForObject(stringBuilder.toString(), event, Event.class);
    }
    public Event getEvent(String id){
        String token = Token.invoke();
        StringBuilder stringBuilder = new StringBuilder();
        List<Event> eventList = new ArrayList<>();
        Gson g = new Gson();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("event.json?access_token={0}&orderBy=\"uid\"&equalTo=\"{1}\"", token, id));
        ResponseEntity<Object> forEntity = restTemplate.getForEntity(stringBuilder.toString(), Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) forEntity.getBody();
        map.entrySet().forEach(e -> eventList.add(g.fromJson(JSONObject.valueToString(e.getValue()), Event.class)));
        return eventList.get(0);
    }
    public Map<String,String> getAllEvents() {
        Map<String,String> jsonObjects = new LinkedHashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append(MessageFormat.format("event.json?access_token={0}",Token.invoke()));
        ResponseEntity<Object> responseEntity = restTemplate.getForEntity(stringBuilder.toString(), Object.class);
        LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) responseEntity.getBody();

        map.entrySet().forEach(e -> jsonObjects.put(e.getKey(),JSONObject.valueToString(e.getValue())));
        return jsonObjects;
    }
}
