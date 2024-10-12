package com.example.alertingservice.service;

import com.example.alertingservice.model.Alert;
import com.example.alertingservice.repository.AlertRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final JavaMailSender mailSender;

    public AlertService(AlertRepository alertRepository, JavaMailSender mailSender) {
        this.alertRepository = alertRepository;
        this.mailSender = mailSender;
    }

    public void createAlert(String deviceId, String message, AlertSeverity severity) {
        Alert alert = new Alert();
        alert.setDeviceId(deviceId);
        alert.setMessage(message);
        alert.setSeverity(severity);
        alert.setTimestamp(Instant.now());
        alert.setResolved(false);

        alertRepository.save(alert);
        sendAlertEmail(alert);
    }

    private void sendAlertEmail(Alert alert) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("maintenance@example.com");
        message.setSubject("Maintenance Alert: " + alert.getSeverity());
        message.setText("Device ID: " + alert.getDeviceId() + "\n" +
                "Message: " + alert.getMessage() + "\n" +
                "Timestamp: " + alert.getTimestamp());
        mailSender.send(message);
    }

    @Scheduled(fixedRate = 300000) // Run every 5 minutes
    public void checkAndGenerateAlerts() {
        // This is where you'd implement logic to check predictions and generate alerts
        // For this example, we'll just create a dummy alert
        createAlert("device123", "High temperature detected", AlertSeverity.HIGH);
    }

    public List<Alert> getUnresolvedAlerts() {
        return alertRepository.findByIsResolvedFalse();
    }

    public void resolveAlert(String alertId) {
        alertRepository.findById(alertId).ifPresent(alert -> {
            alert.setResolved(true);
            alertRepository.save(alert);
        });
    }
}
