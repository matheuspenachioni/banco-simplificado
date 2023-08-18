package br.com.bancosimplificado.math.mensageria;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bancosimplificado.math.email.EmailService;

@Service
public class RabbitMQService {

	@Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private EmailService emailService;

    private final String exchangeName = "notificationExchange";
    private final String routingKey = "notificationKey";

    public void sendNotificationMessage(String email, String subject, String message) {
        Map<String, String> notificationDetails = new HashMap<>();
        notificationDetails.put("email", email);
        notificationDetails.put("subject", subject);
        notificationDetails.put("message", message);
        
        rabbitTemplate.convertAndSend(exchangeName, routingKey, notificationDetails);
    }

    @RabbitListener(queues = "${some.queue.name}")
    public void receiveNotificationMessage(Map<String, String> notificationDetails) {
        emailService.sendEmail(
            notificationDetails.get("email"),
            notificationDetails.get("subject"),
            notificationDetails.get("message")
        );
    }
    
}
