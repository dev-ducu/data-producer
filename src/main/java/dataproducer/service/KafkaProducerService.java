package dataproducer.service;

import dataproducer.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class KafkaProducerService {

    @Value(value = "${KafkaConfig.KafkaProducerService.topic.name.outlier}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public SendResult<String, String> sendMessage(String message) {
        log.info("Producing message -> {}", message);

        final ListenableFuture<SendResult<String, String>> resultListenableFuture = kafkaTemplate.send(topicName, message);
        resultListenableFuture.addCallback(resultHandler(message));

        try {
            return resultListenableFuture.get();
        } catch (Exception e) {
            throw new CustomException("Error occurred while publishing message to Kafka", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ListenableFutureCallback<SendResult<String, String>> resultHandler(String message) {
        return new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Message=[{}] sent successfully, offset=[{}]", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message=[{}] due to : {}", message, ex.getMessage());
            }
        };
    }
}
