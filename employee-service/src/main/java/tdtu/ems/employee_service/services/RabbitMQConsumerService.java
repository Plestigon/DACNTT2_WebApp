package tdtu.ems.employee_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tdtu.ems.employee_service.models.AccessLog;
import tdtu.ems.employee_service.models.AccessLogDto;

import java.util.Map;

@Service
public class RabbitMQConsumerService {
    private RabbitTemplate rabbitTemplate;
    private final Logger logger;
    private final AuthService _authService;

    public RabbitMQConsumerService(RabbitTemplate rabbitTemplate, AuthService authService) {
        this.rabbitTemplate = rabbitTemplate;
        this.logger = LoggerFactory.getLogger(RabbitMQConsumerService.class);
        _authService = authService;
    }

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String message) {
        logger.info("<Received> " + message);
    }

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJson(String json) {
        json = json.substring(1, json.length()-1);
        json = json.replace("\\", "");
        logger.info("<Received JSON> " + json);
        try {
            ObjectMapper mapper = new ObjectMapper();
            AccessLogDto log = mapper.readValue(json, AccessLogDto.class);
            if (log != null) {
                _authService.logAccess(log);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
