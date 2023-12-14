package org.sd.external.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sd.dtos.DeviceMessage;
import org.sd.services.DeviceService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceConsumer {
    private final DeviceService deviceService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = {"${rabbitmq.deviceChanges}"})
    public void consume(String payload) throws JsonProcessingException {
        log.info(String.format("Received message -> %s", payload));

        DeviceMessage message = objectMapper.readValue(payload, DeviceMessage.class);
        deviceService.handleEvent(message);
    }
}
