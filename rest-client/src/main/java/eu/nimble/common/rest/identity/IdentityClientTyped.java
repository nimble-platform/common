package eu.nimble.common.rest.identity;

import com.fasterxml.jackson.core.type.TypeReference;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PersonType;
import eu.nimble.utility.JsonSerializationUtility;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.util.List;

/**
 * Created by suat on 24-Apr-18.
 */
@Component
public class IdentityClientTyped {
    private static Logger logger = LoggerFactory.getLogger(IdentityClientTyped.class);

    @Autowired
    private IdentityClient identityClient;

    public PartyType getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId) throws IOException {
        Response response = identityClient.getParty(bearerToken, storeId);
        String responseBody;
        try {
            responseBody = IOUtils.toString(response.body().asInputStream());

        } catch (IOException e) {
            String msg = String.format("Failed to obtain body response while getting the party with id: %s", storeId);
            logger.error(msg);
            throw e;
        }

        if (response.status() == 200) {
            try {
                return JsonSerializationUtility.deserializeContent(responseBody, new TypeReference<PartyType>() {
                });
            } catch (IOException e) {
                String msg = String.format("Failed to deserialize party: %s", responseBody);
                logger.error(msg);
                throw e;
            }
        } else {
            logger.warn("Failed to get party with id: {}, response status: {}, response body: {}", storeId, response.status(), responseBody);
            return null;
        }
    }

    public List<PartyType> getParties(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyIds") List<String> partyIds) throws IOException {
        StringBuilder commaSeparatedIds = new StringBuilder("");
        int i = 0;
        for (; i < partyIds.size() - 1; i++) {
            commaSeparatedIds.append(partyIds.get(i)).append(",");
        }
        commaSeparatedIds.append(partyIds.get(partyIds.size() - 1));

        Response response = identityClient.getParties(bearerToken, commaSeparatedIds.toString());
        String responseBody;
        try {
            responseBody = IOUtils.toString(response.body().asInputStream());

        } catch (IOException e) {
            String msg = String.format("Failed to obtain body response while getting the parties with ids: %s", commaSeparatedIds.toString());
            logger.error(msg);
            throw e;
        }

        if (response.status() == 200) {
            try {
                return JsonSerializationUtility.deserializeContent(responseBody, new TypeReference<List<PartyType>>() {
                });

            } catch (IOException e) {
                String msg = String.format("Failed to deserialize parties: %s", responseBody);
                logger.error(msg);
                throw e;
            }
        } else {
            logger.warn("Failed to get parties with ids: {}, response status: {}, response body: {}", commaSeparatedIds.toString(), response.status(), responseBody);
            return null;
        }
    }

    public List<PartyType> getPartyByPersonID(@PathVariable("personId") String personId) throws IOException {
        Response response = identityClient.getPartyByPersonID(personId);
        String responseBody;
        try {
            responseBody = IOUtils.toString(response.body().asInputStream());
        } catch (IOException e) {
            String msg = String.format("Failed to obtain body response while getting the party for person with id: %s", personId);
            logger.error(msg);
            throw e;
        }

        if (response.status() == 200) {
            try {
                return JsonSerializationUtility.deserializeContent(responseBody, new TypeReference<List<PartyType>>() {
                });
            } catch (IOException e) {
                String msg = String.format("Failed to deserialize party: %s", responseBody);
                logger.error(msg);
                throw e;
            }
        } else {
            logger.warn("Failed to get party for person with id: {}, response status: {}, response body: {}", personId, response.status(), responseBody);
            return null;
        }
    }

    public PersonType getPerson(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String personId) throws IOException {
        Response response = identityClient.getPerson(bearerToken, personId);
        String responseBody;
        try {
            responseBody = IOUtils.toString(response.body().asInputStream());

        } catch (IOException e) {
            String msg = String.format("Failed to obtain body response while getting the person with id: %s", personId);
            logger.error(msg);
            throw e;
        }

        if (response.status() == 200) {
            try {
                return JsonSerializationUtility.deserializeContent(responseBody, new TypeReference<PersonType>() {
                });
            } catch (IOException e) {
                String msg = String.format("Failed to deserialize person: %s", responseBody);
                logger.error(msg);
                throw e;
            }
        } else {
            logger.warn("Failed to get person with id: {}, response status: {}, response body: {}", personId, response.status(), responseBody);
            return null;
        }
    }

    public PersonType getPerson(@RequestHeader("Authorization") String bearerToken) throws IOException {
        Response response = identityClient.getPerson(bearerToken);
        String responseBody;
        try {
            responseBody = IOUtils.toString(response.body().asInputStream());

        } catch (IOException e) {
            String msg = String.format("Failed to obtain body response while getting the person with token: %s", bearerToken);
            logger.error(msg);
            throw e;
        }

        if (response.status() == 200) {
            try {
                return JsonSerializationUtility.deserializeContent(responseBody, new TypeReference<PersonType>() {
                });
            } catch (IOException e) {
                String msg = String.format("Failed to deserialize person: %s", responseBody);
                logger.error(msg);
                throw e;
            }
        } else {
            logger.warn("Failed to get person with bearer token: {}, response status: {}, response body: {}", bearerToken, response.status(), responseBody);
            return null;
        }
    }
}