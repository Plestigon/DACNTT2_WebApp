package tdtu.ems.employee_service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumerService {
    @Value("${rabbitmq.queue.name}")
    private String queue;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    private RabbitTemplate rabbitTemplate;
    private final Logger logger;

    public RabbitMQConsumerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.logger = LoggerFactory.getLogger(RabbitMQConsumerService.class);
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message) {
        logger.info("<Received> " + message);
    }
}
