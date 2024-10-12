package com.example.dataprocessing.service;

import com.example.dataprocessing.model.ProcessedMachineData;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class DataProcessingService {

    private final MongoTemplate mongoTemplate;

    public DataProcessingService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public ProcessedMachineData processData(ProcessedMachineData data) {
        // Implement your data processing logic here
        // This is a simple example that calculates an "anomaly score"
        double anomalyScore = calculateAnomalyScore(data);
        data.setAnomalyScore(anomalyScore);
        return data;
    }

    private double calculateAnomalyScore(ProcessedMachineData data) {
        // This is a simplified anomaly score calculation
        // In a real-world scenario, you'd use more sophisticated methods
        double tempScore = Math.abs(data.getTemperature() - 50) / 30;
        double vibScore = data.getVibration() / 10;
        double pressScore = Math.abs(data.getPressure() - 50) / 50;
        return (tempScore + vibScore + pressScore) / 3;
    }

    public void saveProcessedData(ProcessedMachineData data) {
        mongoTemplate.save(data);
    }
}
