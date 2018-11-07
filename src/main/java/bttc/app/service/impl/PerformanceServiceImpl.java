package bttc.app.service.impl;

import bttc.app.model.Performance;
import bttc.app.repository.PerformanceRepository;
import bttc.app.service.PerformanceService;
import bttc.app.util.ObjectMappingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class PerformanceServiceImpl implements PerformanceService {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceServiceImpl.class);

    @Autowired
    PerformanceRepository performanceRepository;

    @Override
    public Performance addPerformance(Performance performance) {
        return performanceRepository.addPerformance(performance);
    }

    @Override
    public boolean addPerformances(List<Performance> performances) {
        for (Performance performance : performances) {
            performanceRepository.addPerformance(performance);
        }
        return true;
    }

    @Override
    public Performance updatePerformance(Performance performance) {
        return performanceRepository.updatePerformance(performance);
    }

    @Override
    public Performance getPerformance(String id) {

        return performanceRepository.getPerformance(id);


    }

    @Override
    public List<Performance> getAllPerformances() {
        List<Performance> performances = new ArrayList<>();
        try {
            CompletableFuture<List<String>> all = performanceRepository.getAll();

            for (String s : all.get()) {
                performances.add((Performance) ObjectMappingUtil.mapChats(s, Performance.class));
            }
        } catch (ExecutionException | InterruptedException e) {
            logger.error(String.format("Exception getting all performances: %s", e.getMessage()));
            Thread.currentThread().interrupt();
        }
        return performances;
    }
}
