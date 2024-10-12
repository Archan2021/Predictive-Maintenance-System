package com.example.predictionservice.service;

import com.example.predictionservice.model.PredictionRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PredictionService {

    private final RestTemplate restTemplate;
    private final String mlServiceUrl;

    public PredictionService(RestTemplate restTemplate, @Value("${ml.service.url}") String mlServiceUrl) {
        this.restTemplate = restTemplate;
        this.mlServiceUrl = mlServiceUrl;
    }

    public double getPrediction(PredictionRequest request) {
        return restTemplate.postForObject(mlServiceUrl + "/predict", request, Double.class);
    }
}
