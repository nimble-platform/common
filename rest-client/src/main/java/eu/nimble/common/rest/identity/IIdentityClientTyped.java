package eu.nimble.common.rest.identity;

import eu.nimble.common.rest.identity.model.NegotiationSettings;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PersonType;
import feign.Response;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

public interface IIdentityClientTyped {

    public PartyType getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId) throws IOException;

    public PartyType getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId,
                              @RequestParam(value = "includeRoles") boolean includeRoles) throws IOException;

    public List<PartyType> getParties(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyIds") List<String> partyIds,@RequestParam(value = "includeRoles") boolean includeRoles) throws IOException;

    public List<PartyType> getPartyByPersonID(@PathVariable("personId") String personId) throws IOException;

    public Response getAllPartyIds(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "exclude", required = false) List<String> exclude) throws IOException;

    public Response getVerifiedPartyIds(@RequestHeader("Authorization") String bearerToken) throws Exception;

    public Response getPartyPartiesInUBL(@RequestHeader("Authorization") String bearerToken, @RequestParam(value = "page") String page, @RequestParam(value = "includeRoles") String includeRoles, @RequestParam(value = "size") String size) throws Exception;

    public PersonType getPerson(@RequestHeader("Authorization") String bearerToken, @PathVariable("personId") String personId) throws IOException;

    public PersonType getPerson(@RequestHeader("Authorization") String bearerToken) throws IOException;

    public Boolean getUserInfo(@RequestHeader("Authorization") String bearerToken) throws IOException;

    public NegotiationSettings getNegotiationSettings(@PathVariable("companyID") String companyID) throws IOException;
}
