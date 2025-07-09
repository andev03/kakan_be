package com.kakan.order_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component
public class KafkaConsumerStartupVerifier {

    @Autowired
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @EventListener(ApplicationReadyEvent.class)
    public void verifyKafkaConsumersStarted() {
        log.info("Verifying Kafka consumers startup...");
        
        Collection<MessageListenerContainer> containers = kafkaListenerEndpointRegistry.getListenerContainers();
        log.info("Found {} Kafka listener containers", containers.size());
        
        for (MessageListenerContainer container : containers) {
            log.info("Container: {} - Running: {}", 
                    container.getListenerId(), 
                    container.isRunning());
            
            if (!container.isRunning()) {
                log.warn("Starting container: {}", container.getListenerId());
                container.start();
            }
        }
        
        log.info("Kafka consumer verification completed");
    }
} 