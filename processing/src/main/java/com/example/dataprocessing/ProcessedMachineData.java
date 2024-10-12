package com.example.dataprocessing.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "processed_machine_data")
public class ProcessedMachineData {
    @Id
    private String id;
    private String deviceId;
    private double temperature;
    private double vibration;
    private double pressure;
    private Instant timestamp;
    private double anomalyScore;
}
