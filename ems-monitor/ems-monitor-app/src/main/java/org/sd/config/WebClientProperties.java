package org.sd.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "webclient")
public class WebClientProperties {
    private String device;
    private String keycloak;
}
