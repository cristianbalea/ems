package org.sd.external.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sd.dtos.SimulatorMessage;
import org.sd.services.MonitorService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MeasurementsConsumer {
    private final MonitorService monitorService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = {"${rabbitmq.queue.name}"})
    public void consume(String payload) throws JsonProcessingException {
        log.info(String.format("Received message -> %s", payload));

        SimulatorMessage message = objectMapper.readValue(payload, SimulatorMessage.class);
        monitorService.handleMeasure(message);
    }
}
