package bttc.app.repository;

import bttc.app.exception.RestTemplateResponseErrorHandler;
import bttc.app.model.Venue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class VenueRepository {

    @Value("${firebase.db.url}")
    private String dbUrl;

    private RestTemplate restTemplate;

    @Autowired
    public VenueRepository(RestTemplateBuilder restTemplateBuilder) {
        restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public Venue saveVenue(Venue venue)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(dbUrl);
        stringBuilder.append("venue.json");
        ResponseEntity<Venue> userResponseEntity = restTemplate.postForEntity(stringBuilder.toString(), venue, Venue.class);
        return userResponseEntity.getBody();
    }

}
