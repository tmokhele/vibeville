package bttc.app.controller;

import bttc.app.exception.AppException;
import bttc.app.model.StallInformation;
import bttc.app.service.StallService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/stalls")
public class StallController {

    List<StallInformation> stallInformationList;

    @Autowired
    StallService stallService;

    @GetMapping
    private  @ResponseBody List<StallInformation> getApplications() {
        stallInformationList = new ArrayList<>();
        try {
            CompletableFuture<List<String>> allStallApplications = stallService.getAllStallApplications();
            for (String s : allStallApplications.get()) {
                mapStalls(s);
            }
        }catch (Exception ex)
        {

            throw new AppException(MessageFormat.format("Error retrieving stall applications : {0}",ex.getMessage()));
        }
        return stallInformationList;
    }

    private void mapStalls(String entry) {
        Gson g = new Gson();
        stallInformationList.add(g.fromJson(entry, StallInformation.class));
    }
}
