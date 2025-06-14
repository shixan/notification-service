package com.example.notificationservice.consumer;

import com.example.notificationservice.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserEventConsumerTest {

    @Autowired
    private UserEventConsumer consumer;

    @MockBean
    private EmailService emailService;

    @Test
    void shouldConsumeCreateMessageAndSendEmail() {
        String message = """
            {
              "operation": "CREATE",
              "email": "create@example.com"
            }
        """;

        consumer.handleMessage(message);

        verify(emailService).sendEmail(
                "create@example.com",
                "Успешное создание аккаунта",
                "Здравствуйте! Ваш аккаунт на сайте был успешно создан."
        );
    }

    @Test
    void shouldConsumeDeleteMessageAndSendEmail() {
        String message = """
            {
              "operation": "DELETE",
              "email": "delete@example.com"
            }
        """;

        consumer.handleMessage(message);

        verify(emailService).sendEmail(
                "delete@example.com",
                "Удаление аккаунта",
                "Здравствуйте! Ваш аккаунт был удалён."
        );
    }
}
