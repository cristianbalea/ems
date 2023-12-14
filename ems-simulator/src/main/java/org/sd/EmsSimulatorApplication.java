package org.sd;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class EmsSimulatorApplication {
    private final Producer producer;

    @Value("${device.id}")
    private String deviceId;

    public static void main(String[] args) {
        SpringApplication.run(EmsSimulatorApplication.class, args);
    }

    @PostConstruct
    public void loop() throws InterruptedException, IOException {
        Resource resource = new ClassPathResource("sensor.csv");
        InputStream input = resource.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        String row;

        while (true) {
            Double measurement_value = 0.0;

            if((row = reader.readLine()) != null) {
                measurement_value = Double.parseDouble(row);
            }

            producer.sendMessage(MessageTemplate.builder()
                    .timestamp(LocalDateTime.now())
                    .device_id(UUID.fromString(deviceId))
                    .measurement_value(measurement_value)
                    .build());

            Thread.sleep(6000);
        }
    }
}
