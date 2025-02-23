package com.tjalia.userprofile.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.web.header.Header;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "headers")
@Data
public class HeadersProperty {

    private HeadersClientProperty client;

    @Data
    public static class HeadersClientProperty {
        private String id;
        private String secret;
    }
}
