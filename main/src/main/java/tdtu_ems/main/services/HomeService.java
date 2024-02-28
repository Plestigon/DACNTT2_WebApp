package tdtu_ems.main.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HomeService {
    @Value("${rabbitmq.queue.name}")
    private String queue;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    private final Logger logger;
    private RabbitTemplate rabbitTemplate;

    public HomeService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        logger = LoggerFactory.getLogger(HomeService.class);
    }

    public void sendMessage(String message) {
        logger.info("Sent message from HomeService: " + message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
