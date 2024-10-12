package com.example.predictionservice.controller;

import com.example.predictionservice.model.PredictionRequest;
import com.example.predictionservice.service.PredictionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PredictionController {

    private final PredictionService predictionService;

    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    @PostMapping("/predict")
    public String predict(@RequestBody PredictionRequest request) {
        double prediction = predictionService.getPrediction(request);
        return String.format("Predicted time to failure: %.2f hours", prediction);
    }
}
