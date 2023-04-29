package ru.skillbox.zerone.backend.configuration.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "cloudinary")
public class CloudinaryProperties {
  private String cloudName;
  private String apiKey;
  private String apiSecret;
}