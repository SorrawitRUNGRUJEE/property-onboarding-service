package dev.inventory.propertyonboarding.infrastructure.kafka;

import dev.inventory.propertyonboarding.domain.event.PropertyRegisteredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Infrastructure adapter that publishes domain events to Kafka.
 *
 * Uses Asynchronous Nonblocking communication — the producer sends
 * the message to the Kafka topic and does NOT wait for downstream
 * consumers to process it. This achieves Eventual Consistency.
 */
@Component
public class PropertyEventProducer {

    private static final Logger log = LoggerFactory.getLogger(PropertyEventProducer.class);

    private final KafkaTemplate<String, PropertyRegisteredEvent> kafkaTemplate;
    private final String topicName;

    public PropertyEventProducer(
            KafkaTemplate<String, PropertyRegisteredEvent> kafkaTemplate,
            @Value("${app.kafka.topic}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    /**
     * Publishes a PropertyRegisteredEvent to the configured Kafka topic.
     * The key is the propertyId to ensure ordering per property.
     */
    public void publish(PropertyRegisteredEvent event) {
        log.info("Publishing PropertyRegisteredEvent to topic [{}] | propertyId={}",
                 topicName, event.getPropertyId());

        kafkaTemplate.send(topicName, event.getPropertyId(), event)
            .whenComplete((result, ex) -> {
                if (ex != null) {
                    log.error("Failed to publish event for propertyId={}: {}",
                              event.getPropertyId(), ex.getMessage());
                } else {
                    log.info("Event published successfully | topic={}, partition={}, offset={}",
                             result.getRecordMetadata().topic(),
                             result.getRecordMetadata().partition(),
                             result.getRecordMetadata().offset());
                }
            });
    }
}
