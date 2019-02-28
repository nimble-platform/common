package eu.nimble.common.rest.trust;

import feign.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign client for trust microservice.
 */
@FeignClient(name = "trust-service", url = "${nimble.trust.url:}")
public interface TrustClient {
    @RequestMapping(method = RequestMethod.GET, value = "/version", produces = "application/json")
    Response getVersion();

    @RequestMapping(method = RequestMethod.GET, value = "/party/{partyId}/trust", produces = MediaType.APPLICATION_JSON_VALUE)
    Response obtainPartyTrustValues(@PathVariable("partyId") String partyId, @RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/party/list/trust", produces = MediaType.APPLICATION_JSON_VALUE)
    Response getAllTrustValues(@RequestHeader("Authorization") String bearerToken);
}