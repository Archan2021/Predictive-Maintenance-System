package com.example.dataprocessing.kafka;

import com.example.dataprocessing.model.ProcessedMachineData;
import com.example.dataprocessing.service.DataProcessingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final ObjectMapper objectMapper;
    private final DataProcessingService dataProcessingService;

    public KafkaConsumer(ObjectMapper objectMapper, DataProcessingService dataProcessingService) {
        this.objectMapper = objectMapper;
        this.dataProcessingService = dataProcessingService;
    }

    @KafkaListener(topics = "machine-data", groupId = "data-processing-group")
    public void consume(String message) {
        try {
            ProcessedMachineData data = objectMapper.readValue(message, ProcessedMachineData.class);
            ProcessedMachineData processedData = dataProcessingService.processData(data);
            dataProcessingService.saveProcessedData(processedData);
        } catch (Exception e) {
            // Handle exception (log it, send to error topic, etc.)
            e.printStackTrace();
        }
    }
}
