package com.example.alertingservice.repository;

import com.example.alertingservice.model.Alert;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface AlertRepository extends MongoRepository<Alert, String> {
    List<Alert> findByIsResolvedFalse();
}
