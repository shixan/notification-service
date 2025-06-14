package com.example.notificationservice.consumer;

import com.example.notificationservice.dto.UserEventDto;
import com.example.notificationservice.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    public UserEventConsumer(EmailService emailService) {
        this.emailService = emailService;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void consume(ConsumerRecord<String, String> record) {
        handleMessage(record.value());
    }

    public void handleMessage(String message) {
        try {
            UserEventDto event = objectMapper.readValue(message, UserEventDto.class);
            String email = event.getEmail();
            String operation = event.getOperation();

            if ("CREATE".equalsIgnoreCase(operation)) {
                emailService.sendEmail(email, "Успешное создание аккаунта",
                        "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
            } else if ("DELETE".equalsIgnoreCase(operation)) {
                emailService.sendEmail(email, "Удаление аккаунта",
                        "Здравствуйте! Ваш аккаунт был удалён.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
