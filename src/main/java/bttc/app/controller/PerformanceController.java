package bttc.app.controller;


import bttc.app.model.ApiResponse;
import bttc.app.model.Performance;
import bttc.app.service.PerformanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    @Autowired
    PerformanceService performanceService;

    @PostMapping("/addAll")
    public ResponseEntity addPerformances(@RequestBody List<Performance> performances) {
        performanceService.addPerformances(performances);
        return ResponseEntity.ok(new ApiResponse(true, "Performances added successfully", null));
    }

    @PostMapping
    public Performance addPerformance(@RequestBody Performance performance) {
        return performanceService.addPerformance(performance);
    }

    @PutMapping
    public Performance updatePerformance(@RequestBody Performance performance) {

        return performanceService.updatePerformance(performance);

    }

    @GetMapping("/{performanceId}")
    public Performance getPerformance(@PathVariable String performanceId) {

        return performanceService.getPerformance(performanceId);
    }

    @GetMapping
    public List<Performance> getAllPerformances() throws InterruptedException {

        return performanceService.getAllPerformances();

    }

}
