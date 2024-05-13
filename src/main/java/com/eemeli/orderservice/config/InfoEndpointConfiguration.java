package com.eemeli.orderservice.config;

import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.util.List;
import java.util.Map;

@Configuration
public class InfoEndpointConfiguration {
    private static final String APP_INFO_FILE = "app-info.yml";

    @Bean
    public InfoEndpoint appInfoEndpoint() {
        Yaml yaml = new Yaml();
        Map<String, Object> appInfo = yaml.loadAs(
                this.getClass().getClassLoader().getResourceAsStream(APP_INFO_FILE),
                Map.class
        );

        InfoContributor appInfoContributor = builder -> builder.withDetails(appInfo);
        return new InfoEndpoint(List.of(appInfoContributor));
    }
}
