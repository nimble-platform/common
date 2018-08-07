package eu.nimble.common.rest.identity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Johannes Innerbichler on 06.08.18.
 */
@Component
@ConfigurationProperties("nimble.identity")
public class IdentityProperties {

    private String identityUrl;

    public String getIdentityUrl() {
        return identityUrl;
    }

    public void setIdentityUrl(String identityUrl) {
        this.identityUrl = identityUrl;
    }
}
