package com.example.notificationservice.controller;

import com.example.notificationservice.dto.UserEventDto;
import com.example.notificationservice.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final EmailService emailService;

    public NotificationController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<String> sendManualNotification(@RequestBody UserEventDto event) {
        String operation = event.getOperation();
        String email = event.getEmail();

        if (operation == null || email == null) {
            return ResponseEntity.badRequest().body("operation and email are required");
        }

        if ("CREATE".equalsIgnoreCase(operation)) {
            emailService.sendEmail(email, "Успешное создание аккаунта",
                    "Здравствуйте! Ваш аккаунт на сайте был успешно создан.");
        } else if ("DELETE".equalsIgnoreCase(operation)) {
            emailService.sendEmail(email, "Удаление аккаунта",
                    "Здравствуйте! Ваш аккаунт был удалён.");
        } else {
            return ResponseEntity.badRequest().body("Неизвестная операция: " + operation);
        }

        return ResponseEntity.ok("Письмо отправлено на " + email);
    }
}
