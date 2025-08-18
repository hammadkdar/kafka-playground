package com.karamatics.audit.audit_service.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karamatics.audit.audit_service.entity.SemActivityLog;
import com.karamatics.audit.audit_service.service.SemActivityLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.adapter.ConsumerRecordMetadata;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuditEventConsumer {

    private final ObjectMapper objectMapper;

    private final SemActivityLogService semActivityLogService;

    @Transactional
    @KafkaListener(topics = "${audit.kafka.consumer.topic}", groupId = "${audit.kafka.consumer.group-id}")
    public void consumeEvent(@Payload String message,
                             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                             @Header(value = KafkaHeaders.CORRELATION_ID, required = false) String correlationId,
                             @Header(value = KafkaHeaders.OFFSET, required = false) String offset,
                             @Header(value = "message-type", required = false) String messageType,
                             ConsumerRecordMetadata consumerRecordMetadata) {
        // Logic to consume the audit event
        // This could involve parsing the event, saving it to a database, etc.
        log.info("Consumed audit event message: '{}' of type [{}],  from [topic][partition][offset] = [{}][{}][{}]",
                message, messageType, consumerRecordMetadata.topic(), consumerRecordMetadata.offset(), consumerRecordMetadata.partition());

        if ("json".equalsIgnoreCase(messageType)) {
            try {
                SemActivityLog data = objectMapper.readValue(message, SemActivityLog.class);
                semActivityLogService.save(data);
                log.info("Audit event processed successfully: {}", data);
            } catch (JsonProcessingException exception) {
                // Handle JSON parsing error
                log.error("Failed to parse JSON: '" + message + "', logging into database.");
            } catch (Exception e) {
                // Handle other exceptions
                log.error("Error processing audit event: " + message + "logging into database.");
            }
        } else {
            log.warn("INVALID_MESSAGE_TYPE : Message is not of json type, ignoring and only printing it to console: '{}'", message);

        }
    }
}
