package eu.nimble.common.rest.identity;

import com.fasterxml.jackson.core.type.TypeReference;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.utility.JsonSerializationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suat on 24-Apr-18.
 */
@Component
public class IdentityClientTyped {
    private static Logger logger = LoggerFactory.getLogger(IdentityClientTyped.class);

    @Autowired
    private IdentityClient identityClient;

    public PartyType getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId) {
        if(bearerToken == null) {
            logger.warn("A valid bearer token must be provided");
            return null;
        }

        if(storeId == null) {
            logger.warn("No party id specified");
            return null;
        }

        try {
            return JsonSerializationUtility.deserializeContent(identityClient.getParty(bearerToken, storeId).body().asInputStream(), new TypeReference<PartyType>() {});
        } catch (IOException e) {
            logger.error("Failed to deserialize the party");
            return null;
        }
    }

    public List<PartyType> getParties(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyIds") List<String> partyIds) {
        if(bearerToken == null) {
            logger.warn("A valid bearer token must be provided");
            return new ArrayList<>();
        }

        if(partyIds == null || partyIds.size() == 0) {
            logger.warn("No party id specified");
            return new ArrayList<>();
        }

        try {
            StringBuilder commaSeparatedIds = new StringBuilder("");
            int i=0;
            for(; i<partyIds.size()-1; i++) {
                commaSeparatedIds.append(partyIds.get(i)).append(",");
            }
            commaSeparatedIds.append(partyIds.get(partyIds.size()-1));

            return JsonSerializationUtility.deserializeContent(identityClient.getParties(bearerToken, commaSeparatedIds.toString()).body().asInputStream(), new TypeReference<List<PartyType>>(){});
        } catch (IOException e) {
            logger.error("Failed to deserialize the party");
            return null;
        }
    }
}