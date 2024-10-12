package com.example.alertingservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document(collection = "alerts")
public class Alert {
    @Id
    private String id;
    private String deviceId;
    private String message;
    private AlertSeverity severity;
    private Instant timestamp;
    private boolean isResolved;
}

enum AlertSeverity {
    LOW, MEDIUM, HIGH, CRITICAL
}
