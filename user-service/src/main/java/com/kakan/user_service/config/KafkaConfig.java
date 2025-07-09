package com.kakan.user_service.config;

import com.kakan.user_service.dto.PaymentSucceededEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    // chung props cho tất cả consumer của user-service
    private Map<String, Object> consumerProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        // tắt header __TypeId__ của Spring Kafka
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        // Additional consumer configs for reliability
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 30000);
        props.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 3000);
        
        System.out.println("User-service consumer configs: " + props);
        return props;
    }

    // ConsumerFactory cho PaymentSucceededEvent
    @Bean
    public ConsumerFactory<String, PaymentSucceededEvent> paymentSucceededConsumerFactory() {
        System.out.println("Creating paymentSucceededConsumerFactory in user-service...");
        JsonDeserializer<PaymentSucceededEvent> deserializer =
                new JsonDeserializer<>(PaymentSucceededEvent.class, false);
        deserializer.addTrustedPackages("com.kakan.*.dto");
        deserializer.setRemoveTypeHeaders(false);
        System.out.println("PaymentSucceededEvent consumer factory created successfully in user-service");

        return new DefaultKafkaConsumerFactory<>(
                consumerProps(),
                new StringDeserializer(),
                deserializer
        );
    }

    // ListenerContainerFactory cho @KafkaListener
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentSucceededEvent>
    paymentSucceededKafkaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentSucceededEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentSucceededConsumerFactory());
        
        // Error handling
        factory.setCommonErrorHandler(new org.springframework.kafka.listener.DefaultErrorHandler(
                new org.springframework.util.backoff.FixedBackOff(1000L, 3)
        ));
        
        // Auto startup
        factory.setAutoStartup(true);
        
        return factory;
    }
}
