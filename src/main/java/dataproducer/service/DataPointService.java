package dataproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataproducer.dto.request.DataPointRequestDTO;
import dataproducer.dto.response.DataPointResponseDTO;
import dataproducer.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataPointService {

    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;

    public DataPointService(KafkaProducerService kafkaProducerService, ObjectMapper objectMapper) {
        this.kafkaProducerService = kafkaProducerService;
        this.objectMapper = objectMapper;
    }

    public DataPointResponseDTO handleCreate(DataPointRequestDTO dataPointRequestDTO) {
        try {
            final SendResult<String, String> result = kafkaProducerService.sendMessage(objectMapper.writeValueAsString(dataPointRequestDTO));
            return DataPointResponseDTO.builder().message("Message successfully sent. New offset is: "  + result.getRecordMetadata().offset()).build();
        } catch (JsonProcessingException e) {
            log.error("JSON marshalling error {}", e);
        }
        throw new CustomException("An error occurred while handling a data point creation request", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}