package com.example.alertingservice.controller;

import com.example.alertingservice.model.Alert;
import com.example.alertingservice.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<Alert> getUnresolvedAlerts() {
        return alertService.getUnresolvedAlerts();
    }

    @PostMapping("/{alertId}/resolve")
    public void resolveAlert(@PathVariable String alertId) {
        alertService.resolveAlert(alertId);
    }
}
