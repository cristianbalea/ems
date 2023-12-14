package org.sd.external.devices;

import lombok.RequiredArgsConstructor;
import org.sd.config.WebClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class DeviceWebClientConfig {
    private final WebClientProperties webClientProperties;

    @Bean
    public WebClient deviceWebClient() {
        HttpClient httpClient = HttpClient.newConnection().responseTimeout(Duration.ofSeconds(30)).compress(true);

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl(webClientProperties.getDevice())
                .build();
    }
}