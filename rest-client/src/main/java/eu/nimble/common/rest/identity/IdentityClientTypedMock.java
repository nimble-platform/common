package eu.nimble.common.rest.identity;

import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyIdentificationType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyNameType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PersonType;
import eu.nimble.service.model.ubl.commonbasiccomponents.TextType;
import eu.nimble.utility.JsonSerializationUtility;
import eu.nimble.utility.persistence.JPARepositoryFactory;
import feign.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Profile("test")
@Component
public class IdentityClientTypedMock implements IIdentityClientTyped {

    public PartyType getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId) throws IOException {
        String insertQuery = "SELECT party FROM PartyType party join party.partyIdentification as partyIdentification where partyIdentification.ID = :partyId";
        PartyType party = new JPARepositoryFactory().forCatalogueRepository(true).getSingleEntity(insertQuery,new String[]{"partyId"}, new Object[]{storeId});
        if(party == null){
            party = createParty(storeId);
        }

        return party;
    }

    public PartyType getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId,
                              @RequestParam(value = "includeRoles") boolean includeRoles) throws IOException {
        return getParty(bearerToken,storeId);
    }

    public List<PartyType> getParties(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyIds") List<String> partyIds) throws IOException {

        StringBuilder commaSeparatedIds = new StringBuilder("");
        int i = 0;
        for (; i < partyIds.size() - 1; i++) {
            commaSeparatedIds.append(partyIds.get(i)).append(",");
        }
        commaSeparatedIds.append(partyIds.get(partyIds.size() - 1));
        String query = "SELECT party FROM PartyType party join party.partyIdentification as partyIdentification where partyIdentification.ID in :partyIds";
        List<PartyType> parties = new JPARepositoryFactory().forCatalogueRepository(true).getEntities(query,new String[]{"partyIds"}, new Object[]{commaSeparatedIds.toString()});

        for(String partyId:partyIds){
            boolean partyExists = false;
            for(PartyType party:parties){
                if(party.getPartyIdentification().get(0).getID().contentEquals(partyId)){
                    partyExists = true;
                    break;
                }
            }

            if(!partyExists){
                parties.add(createParty(partyId));
            }
        }

        return parties;
    }

    public List<PartyType> getPartyByPersonID(@PathVariable("personId") String personId) throws IOException {
        String query = "SELECT party FROM PartyType party join party.person as person where person.ID = :personId";
        return new JPARepositoryFactory().forCatalogueRepository(true).getEntities(query,new String[]{"personId"}, new Object[]{personId});
    }

    public PersonType getPerson(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String personId) throws IOException {
        String query = "SELECT person FROM PersonType person where person.ID = :personId";
        PersonType person = new JPARepositoryFactory().forCatalogueRepository(true).getSingleEntity(query,new String[]{"personId"}, new Object[]{personId});
        return person;
    }

    // the bearer token is a person id in this method
    public PersonType getPerson(@RequestHeader("Authorization") String bearerToken) throws IOException {
        return getPerson(bearerToken,bearerToken);
    }

    public Boolean getUserInfo(@RequestHeader("Authorization") String bearerToken) throws IOException{
        return true;
    }

    @Override
    public Response getAllPartyIds(String bearerToken, List<String> exclude) {
        List<String> ids = Arrays.asList("706","747","1339");
        ids.removeAll(exclude);

        String nameIdPairs = getPartyNameIdPairs(ids);
        return Response.builder()
                .body(nameIdPairs, StandardCharsets.UTF_8)
                .status(200)
                .headers(new HashMap<>())
                .build();
    }

    @Override
    public Response getPartyPartiesInUBL(String bearerToken, String page, String includeRoles, String size) throws Exception{
        List<String> ids = Arrays.asList("706","747","1339");
        List<PartyType> parties = new ArrayList<>();
        for(String id:ids){
            parties.add(createParty(id));
        }

        return Response.builder()
                .body(JsonSerializationUtility.getObjectMapper().writeValueAsString(parties), StandardCharsets.UTF_8)
                .status(200)
                .headers(new HashMap<>())
                .build();
    }

    private PartyType createParty(String partyId){
        PartyIdentificationType partyIdentification = new PartyIdentificationType();
        partyIdentification.setID(partyId);

        PartyType party = new PartyType();
        party.getPartyIdentification().add(partyIdentification);

        if(partyId.contentEquals("706")){
            PersonType person = new PersonType();
            person.setID("704");
            person.setFirstName("ali");
            person.setFamilyName("can");

            PersonType person2 = new PersonType();
            person2.setID("379");
            person2.setFirstName("Suat");
            person2.setFamilyName("Gonul");

            PartyNameType partyName = new PartyNameType();
            TextType text = new TextType();
            text.setLanguageID("en");
            text.setValue("canCompany");
            partyName.setName(text);

            party.getPartyName().add(partyName);
            party.setPerson(Arrays.asList(person,person2));
        } else if(partyId.contentEquals("747")){
            PersonType person = new PersonType();
            person.setID("745");
            person.setFirstName("veli");
            person.setFamilyName("cav");

            PartyNameType partyName = new PartyNameType();
            TextType text = new TextType();
            text.setLanguageID("en");
            text.setValue("CavCompany");
            partyName.setName(text);

            party.getPartyName().add(partyName);
            party.setPerson(Arrays.asList(person));
        } else if(partyId.contentEquals("1339")){
            PersonType person = new PersonType();
            person.setID("1337");
            person.setFirstName("alp");
            person.setFamilyName("cenk");

            PartyNameType partyName = new PartyNameType();
            TextType text = new TextType();
            text.setLanguageID("en");
            text.setValue("alpCompany");
            partyName.setName(text);

            party.getPartyName().add(partyName);
            party.setPerson(Arrays.asList(person));
        }

        return party;
    }

    private String getPartyNameIdPairs(List<String> ids){
        JSONArray jsonArray = new JSONArray();

        for(String id:ids){
            // get party
            PartyType partyType = createParty(id);

            JSONObject party = new JSONObject();
            party.put("companyID",partyType.getPartyIdentification().get(0).getID());

            JSONObject name = new JSONObject();
            name.put("en",partyType.getPartyName().get(0).getName().getValue());

            party.put("names",name);
            // add party name-id pair to the list
            jsonArray.put(party);
        }

        return jsonArray.toString();
    }

}