package bttc.app.controller;

import bttc.app.model.ApiResponse;
import bttc.app.model.Venue;
import bttc.app.repository.VenueRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/venue")
@Api(value = "VenueController")
public class VenueController {

    @Autowired
    VenueRepository venueRepository;

    @PostMapping
    public ResponseEntity<ApiResponse> saveVenue(@RequestBody Venue venue)
    {
        return ResponseEntity.ok().body(new ApiResponse(true, "Record Successfully saved",venueRepository.saveVenue(venue)));
    }
}
