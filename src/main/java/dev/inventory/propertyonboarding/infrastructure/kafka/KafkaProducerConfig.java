package dev.inventory.propertyonboarding.infrastructure.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Kafka configuration — creates the topic on startup if it does not exist.
 */
@Configuration
public class KafkaProducerConfig {

    @Value("${app.kafka.topic}")
    private String topicName;

    @Bean
    public NewTopic propertyOnboardingTopic() {
        return TopicBuilder.name(topicName)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
