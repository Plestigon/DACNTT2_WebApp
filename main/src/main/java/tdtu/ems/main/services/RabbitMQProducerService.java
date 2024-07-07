package tdtu.ems.main.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tdtu.ems.main.models.AccessLogDto;

@Service
public class RabbitMQProducerService {
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    @Value("${rabbitmq.routing.json.key}")
    private String routingJsonKey;

    private final Logger logger;
    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        logger = LoggerFactory.getLogger(RabbitMQProducerService.class);
    }

    public void sendMessage(String message) {
        logger.info("<Sent> " + message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    public void sendJsonMessage(AccessLogDto log) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(log);
            logger.info("<Sent JSON> " + json);
            rabbitTemplate.convertAndSend(exchange, routingJsonKey, json);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }
}
