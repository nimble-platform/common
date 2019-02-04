package eu.nimble.common.rest.identity;

import feign.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Johannes Innerbichler on 02/05/17.
 * Feign client for identity microservice.
 */
@FeignClient(name = "identity-service", url = "${nimble.identity.url:}")
public interface IdentityClient {
    @RequestMapping(method = RequestMethod.GET, value = "/party/{partyId}", produces = "application/json")
    Response getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId);

    @RequestMapping(method = RequestMethod.GET, value = "/parties/{partyIds}", produces = "application/json")
    Response getParties(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyIds") String partyIds);

    @RequestMapping(method = RequestMethod.GET, value = "/party_by_person/{personId}", produces = "application/json")
    Response getPartyByPersonID(@PathVariable("personId") String personId);

    @RequestMapping(method = RequestMethod.GET, value = "/party/all", produces = "application/json")
    Response getAllPartyIds(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "exclude", required = false) List<String> exclude);

    @RequestMapping(method = RequestMethod.GET, value = "/person/{personId}", produces = "application/json")
    Response getPerson(@RequestHeader("Authorization") String bearerToken, @PathVariable("personId") String personId);

    @RequestMapping(method = RequestMethod.GET, value = "/person/", produces = "application/json")
    Response getPerson(@RequestHeader("Authorization") String bearerToken);

    @RequestMapping(method = RequestMethod.GET, value = "/user-info",produces = "application/json")
    Response getUserInfo(@RequestHeader("Authorization") String bearerToken);
}