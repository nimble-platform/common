package eu.nimble.common.rest.identity;
import eu.nimble.common.rest.identity.model.NegotiationSettings;
import eu.nimble.common.rest.identity.model.PersonPartyTuple;
import eu.nimble.service.model.ubl.commonaggregatecomponents.*;
import eu.nimble.service.model.ubl.commonbasiccomponents.CodeType;
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
import java.util.*;

@Profile("test")
@Component
public class IdentityClientTypedMock implements IIdentityClientTyped {
    public PartyType getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId) throws IOException {
        return createParty(storeId);
    }
    public PartyType getParty(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String storeId,
                              @RequestParam(value = "includeRoles") boolean includeRoles) throws IOException {
        return getParty(bearerToken,storeId);
    }
    public List<PartyType> getParties(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyIds") List<String> partyIds,@RequestParam(value = "includeRoles") boolean includeRoles) throws IOException {
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
        PartyType party = null;
        if(personId.contentEquals(IdentityClientTypedMockConfig.sellerPersonID) || personId.contentEquals(IdentityClientTypedMockConfig.sellerPerson2ID)){
            party = createParty(IdentityClientTypedMockConfig.sellerPartyID);
        }
        else if(personId.contentEquals(IdentityClientTypedMockConfig.transportServiceProviderPersonID)){
            party = createParty(IdentityClientTypedMockConfig.transportServiceProviderPartyID);
        }
        else if(personId.contentEquals(IdentityClientTypedMockConfig.buyerUserID)){
            party = createParty(IdentityClientTypedMockConfig.buyerPartyID);
        }
        return Arrays.asList(party);
    }
    public PersonType getPerson(@RequestHeader("Authorization") String bearerToken, @PathVariable("partyId") String personId) throws IOException {
        return createPerson(personId);
    }

    // the bearer token is a person id in this method
    public PersonType getPerson(@RequestHeader("Authorization") String bearerToken) throws IOException {
        return createPerson(bearerToken);
    }
    public Boolean getUserInfo(@RequestHeader("Authorization") String bearerToken) throws IOException{
        return true;
    }

    @Override
    public PersonPartyTuple getPersonPartyTuple(String bearerToken) throws IOException {
        return createPersonPartyTuple(bearerToken);
    }

    @Override
    public Response getAllPartyIds(String bearerToken, List<String> exclude) {
        List<String> ids = Arrays.asList(IdentityClientTypedMockConfig.sellerPartyID,IdentityClientTypedMockConfig.transportServiceProviderPartyID,IdentityClientTypedMockConfig.buyerPartyID);
        ids.removeAll(exclude);

        String nameIdPairs = getPartyNameIdPairs(ids);
        return Response.builder()
                .body(nameIdPairs, StandardCharsets.UTF_8)
                .status(200)
                .headers(new HashMap<>())
                .build();
    }

    @Override
    public Response inviteCompaniesToDemandDetails(DemandType demand, String bearer, String language) {
        return Response.builder()
                .body(null, StandardCharsets.UTF_8)
                .status(200)
                .headers(new HashMap<>())
                .build();
    }

    @Override
    public Response getVerifiedPartyIds(String bearerToken) throws Exception{
        List<String> ids = Arrays.asList(IdentityClientTypedMockConfig.sellerPartyID,IdentityClientTypedMockConfig.transportServiceProviderPartyID,IdentityClientTypedMockConfig.buyerPartyID);
        return Response.builder()
                .body(JsonSerializationUtility.getObjectMapper().writeValueAsString(ids), StandardCharsets.UTF_8)
                .status(200)
                .headers(new HashMap<>())
                .build();
    }

    @Override
    public Response getPartyPartiesInUBL(String bearerToken, String page, String includeRoles, String size) throws Exception{
        List<String> ids = Arrays.asList(IdentityClientTypedMockConfig.sellerPartyID,IdentityClientTypedMockConfig.transportServiceProviderPartyID,IdentityClientTypedMockConfig.buyerPartyID);
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

    @Override
    public NegotiationSettings getNegotiationSettings(String companyID) throws IOException {
        NegotiationSettings negotiationSettings = null;
        if(companyID.contentEquals(IdentityClientTypedMockConfig.sellerPartyID)){
            PartyType party = createParty(IdentityClientTypedMockConfig.sellerPartyID);
            List<String> paymentMeans = new ArrayList<>();
            paymentMeans.add("Credit Card");
            List<String> paymentTerms = new ArrayList<>();
            paymentTerms.add("PIA - Payment in advance");
            List<String> incoterms = new ArrayList<>();
            incoterms.add("FCA (Free Carrier)");

            negotiationSettings = new NegotiationSettings(party,paymentTerms,paymentMeans,incoterms);
        }
        else if(companyID.contentEquals(IdentityClientTypedMockConfig.buyerPartyID)){
            PartyType party = createParty(IdentityClientTypedMockConfig.buyerPartyID);
            List<String> paymentMeans = new ArrayList<>();
            paymentMeans.add("Credit Card");
            List<String> paymentTerms = new ArrayList<>();
            paymentTerms.add("PIA - Payment in advance");
            List<String> incoterms = new ArrayList<>();

            negotiationSettings = new NegotiationSettings(party,paymentTerms,paymentMeans,incoterms);
        }
        return negotiationSettings;
    }
    private PersonType createPerson(String personId){
        PersonType person = new PersonType();
        if(personId.contentEquals(IdentityClientTypedMockConfig.sellerPersonID)){
            person.setID(IdentityClientTypedMockConfig.sellerPersonID);
            person.setFirstName("ali");
            person.setFamilyName("can");
        } else if(personId.contentEquals(IdentityClientTypedMockConfig.sellerPerson2ID)){
            person.setID(IdentityClientTypedMockConfig.sellerPerson2ID);
            person.setFirstName("Suat");
            person.setFamilyName("Gonul");
        } else if(personId.contentEquals(IdentityClientTypedMockConfig.transportServiceProviderPersonID)){
            person.setID(IdentityClientTypedMockConfig.transportServiceProviderPersonID);
            person.setFirstName("veli");
            person.setFamilyName("cav");
        } else if(personId.contentEquals(IdentityClientTypedMockConfig.buyerUserID)){
            person.setID(IdentityClientTypedMockConfig.buyerUserID);
            person.setFirstName("alp");
            person.setFamilyName("cenk");
        }
        person.setContact(new ContactType());
        return person;
    }
    private PartyType createParty(String partyId){
        PartyIdentificationType partyIdentification = new PartyIdentificationType();
        partyIdentification.setID(partyId);

        PartyType party = new PartyType();
        party.getPartyIdentification().add(partyIdentification);

        if(partyId.contentEquals(IdentityClientTypedMockConfig.sellerPartyID)){
            PersonType person = new PersonType();
            person.setID(IdentityClientTypedMockConfig.sellerPersonID);
            person.setFirstName("ali");
            person.setFamilyName("can");
            person.setContact(new ContactType());

            PersonType person2 = new PersonType();
            person2.setID(IdentityClientTypedMockConfig.sellerPerson2ID);
            person2.setFirstName("Suat");
            person2.setFamilyName("Gonul");

            PartyNameType partyName = new PartyNameType();
            TextType text = new TextType();
            text.setLanguageID("en");
            text.setValue("canCompany");
            partyName.setName(text);

            party.getPartyName().add(partyName);
            party.setPerson(Arrays.asList(person,person2));
        } else if(partyId.contentEquals(IdentityClientTypedMockConfig.transportServiceProviderPartyID)){
            PersonType person = new PersonType();
            person.setID(IdentityClientTypedMockConfig.transportServiceProviderPersonID);
            person.setFirstName("veli");
            person.setFamilyName("cav");
            PartyNameType partyName = new PartyNameType();
            TextType text = new TextType();
            text.setLanguageID("en");
            text.setValue("CavCompany");
            partyName.setName(text);
            party.getPartyName().add(partyName);
            party.setPerson(Arrays.asList(person));
        } else if(partyId.contentEquals(IdentityClientTypedMockConfig.buyerPartyID)){
            PersonType person = new PersonType();
            person.setID(IdentityClientTypedMockConfig.buyerUserID);
            person.setFirstName("alp");
            person.setFamilyName("cenk");
            PartyNameType partyName = new PartyNameType();
            TextType text = new TextType();
            text.setLanguageID("en");
            text.setValue("alpCompany");
            partyName.setName(text);
            AddressType address = new AddressType();
            CountryType country = new CountryType();
            CodeType codeType = new CodeType();
            codeType.setValue("TR");
            country.setIdentificationCode(codeType);
            address.setBuildingNumber("10");
            address.setCityName("Ankara");
            address.setCountry(country);
            address.setStreetName("Cankaya");
            List<String> processIds = Arrays.asList("Item_Information_Request");
            party.getPartyName().add(partyName);
            party.setPerson(Arrays.asList(person));
            party.setProcessID(processIds);
            party.setPostalAddress(address);
        }
        party.setFederationInstanceID("TEST_INSTANCE");
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

    private PersonPartyTuple createPersonPartyTuple(String personId) {
        PersonPartyTuple personPartyTuple = new PersonPartyTuple();
        personPartyTuple.setPersonID(personId);
        if(personId.contentEquals(IdentityClientTypedMockConfig.sellerPersonID)){
            personPartyTuple.setCompanyID(IdentityClientTypedMockConfig.sellerPartyID);
        } else if(personId.contentEquals(IdentityClientTypedMockConfig.sellerPerson2ID)){
            personPartyTuple.setCompanyID(IdentityClientTypedMockConfig.sellerPartyID);
        } else if(personId.contentEquals(IdentityClientTypedMockConfig.transportServiceProviderPersonID)){
            personPartyTuple.setCompanyID(IdentityClientTypedMockConfig.transportServiceProviderPartyID);
        } else if(personId.contentEquals(IdentityClientTypedMockConfig.buyerUserID)){
            personPartyTuple.setCompanyID(IdentityClientTypedMockConfig.buyerPartyID);
        }
        return personPartyTuple;
    }
}
