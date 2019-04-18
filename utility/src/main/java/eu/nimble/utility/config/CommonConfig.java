package eu.nimble.utility.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by suat on 29-Mar-19.
 */
@Configuration
public class CommonConfig {
    @Value("${nimble.binary-content.url}")
    private String binaryContentUrl;

    public String getBinaryContentUrl() {
        return binaryContentUrl;
    }
}
