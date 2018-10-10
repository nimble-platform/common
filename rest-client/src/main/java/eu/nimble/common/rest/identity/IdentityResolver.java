package eu.nimble.common.rest.identity;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * Created by Johannes Innerbichler on 06.08.18.
 */
@Service
@EnableConfigurationProperties(IdentityProperties.class)
public class IdentityResolver {

    private final String identityServiceUrl;

    private final IdentityProperties identityProperties;

    public IdentityResolver(IdentityProperties identityProperties) {
        this.identityProperties = identityProperties;
        this.identityServiceUrl = identityProperties.getIdentityUrl();
    }

    /**
     * Extracts the identity from an OpenID Connect token and fetches the associated company from the Identity Service.
     * @param accessToken OpenID Connect token storing identity.
     * @return Identifier of associated company
     * @throws UnirestException Error while communicating with the Identity Service.
     */
    public String resolveCompanyId(String accessToken) throws UnirestException {

        // obtain extended user information
        HttpResponse<JsonNode> response = Unirest.get(identityServiceUrl + "/user-info")
                .header("Authorization", accessToken)
                .asJson();

        return (String) response.getBody().getObject().get("ublPartyID");
    }
}
