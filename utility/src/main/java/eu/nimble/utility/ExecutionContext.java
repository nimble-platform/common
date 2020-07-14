package eu.nimble.utility;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;
/**
 * This a Http-request scoped bean which is instantiated for each REST call. It can be used to pass REST call-specific
 * information to inner-level code execution. For example, the authorization token might passed to inner-level code
 * which can be used to check user roles or execute subsequent REST-calls respecting to the authorization level of the user.
 *
 * Created by suat on 24-Jan-19.
 */
@Component
@RequestScope
public class ExecutionContext {
    private String bearerToken;
    // the token of the user calling the REST service via delegate
    private String originalBearerToken;
    // federation id of the client calling the REST service via delegate
    private String clientFederationId;
    // log representing the associates REST call
    private String requestLog;
    // user roles available in the bearer token
    private List<String> userRoles;
    // user email retrieved from the bearer token
    private String userEmail;
    // language id
    private String languageId;
    // vat number of user
    private String vatNumber;

    public void setBearerToken(String bearerToken) {
        this.bearerToken = bearerToken;
    }

    public String getBearerToken() {
        return this.bearerToken;
    }

    public String getRequestLog() {
        return requestLog;
    }

    public void setRequestLog(String requestLog) {
        this.requestLog = requestLog;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<String> userRoles) {
        this.userRoles = userRoles;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOriginalBearerToken() {
        return originalBearerToken;
    }

    public void setOriginalBearerToken(String originalBearerToken) {
        this.originalBearerToken = originalBearerToken;
    }

    public String getClientFederationId() {
        return clientFederationId;
    }

    public void setClientFederationId(String clientFederationId) {
        this.clientFederationId = clientFederationId;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }
}