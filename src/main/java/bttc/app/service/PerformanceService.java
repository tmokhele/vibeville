package bttc.app.service;

import bttc.app.model.Performance;

import java.util.List;

public interface PerformanceService {
    Performance addPerformance(Performance performance);
    boolean addPerformances(List<Performance> performances);
    Performance updatePerformance(Performance performance);
    Performance getPerformance(String id);
    List<Performance> getAllPerformances();
}
