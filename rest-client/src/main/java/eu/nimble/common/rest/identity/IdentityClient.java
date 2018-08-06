package eu.nimble.common.rest.identity;

import feign.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}

/**
 * Fallback if identity service is not reachable.
 */
//@Component
//class IdentityClientFallback implements IdentityClient {
//
//    private static Logger logger = LoggerFactory.getLogger(IdentityClientFallback.class);
//
//    @Override
//    public PartyType getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId) {
//        logger.warn("Cannot fetch party with Id {}", storeId);
//        return null;
//    }
//
//    @Override
//    public List<PartyType> getParties(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyIds") List<String> partyIds) {
//        logger.warn("Failed to fetch parties with ids: {}", partyIds.toString());
//        return new ArrayList<>();
//    }
//}