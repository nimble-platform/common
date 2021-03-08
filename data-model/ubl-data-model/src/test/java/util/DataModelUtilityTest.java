package util;

import eu.nimble.service.model.ubl.commonaggregatecomponents.*;
import eu.nimble.service.model.ubl.commonbasiccomponents.CodeType;
import eu.nimble.service.model.ubl.commonbasiccomponents.TextType;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.soap.Text;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by suat on 25-Sep-18.
 */
public class DataModelUtilityTest {

    private static final Logger logger = LoggerFactory.getLogger(DataModelUtilityTest.class);

    @Test
    public void nullifyPartyFields() throws IllegalAccessException {
        Field[] fields = PartyType.class.getDeclaredFields();
        PartyType initializedParty = createEmptyParty();

        // check the empty party contains a non-null value for all fields
        for(Field f : fields) {
            if(!f.getType().isPrimitive() && !Modifier.isTransient(f.getModifiers())) {
                f.setAccessible(true);
                Object fieldValue = f.get(initializedParty);
                Assert.assertNotNull(fieldValue);
                f.setAccessible(false);
            }
        }

        DataModelUtility.nullifyPartyFields(initializedParty);

        for(Field f : fields) {
            if(!f.getType().isPrimitive() && !Modifier.isTransient(f.getModifiers())) {
                f.setAccessible(true);
                Object fieldValue = f.get(initializedParty);
                Assert.assertNull(fieldValue);
                f.setAccessible(false);
            }
        }
    }

    @Test
    public void testCopyParty() throws IllegalAccessException {
        List<String> fieldNames = new ArrayList<>();
        Field[] fields = PartyType.class.getDeclaredFields();
        for(Field f : fields) {
            if(!Modifier.isTransient(f.getModifiers())) {
                fieldNames.add(f.getName());
            }
        }

        PartyType p1 = new PartyType();
        DataModelUtility.nullifyPartyFields(p1);
        PartyType p2 = createEmptyParty();
        DataModelUtility.copyParty(p1, p2);

        List<String> copiedFields = new ArrayList<>();
        for(Field f : fields) {
            if(!Modifier.isTransient(f.getModifiers())) {
                f.setAccessible(true);
                Object fieldValue = f.get(p1);
                if (fieldValue != null) {
                    copiedFields.add(f.getName());
                }
                f.setAccessible(false);
            }
        }

        if(!(copiedFields.containsAll(fieldNames) && fieldNames.containsAll(copiedFields))) {
            Assert.fail("Not all the fields in the PartyType are copied");
        }
    }

    private PartyType createEmptyParty() {
        PartyType party = new PartyType();
        party.setHjid(1L);
        party.setWebsiteURI("website");
        party.setDescription(new ArrayList<>());
        party.setBrandName(new ArrayList<>());
        PartyIdentificationType partyIdentificationType = new PartyIdentificationType();
        partyIdentificationType.setID("id");
        PartyNameType partyNameType = new PartyNameType();
        TextType textType = new TextType();
        textType.setLanguageID("en");
        textType.setValue("name");
        partyNameType.setName(textType);
        party.setPartyIdentification(Arrays.asList(partyIdentificationType));
        party.setPartyName(Arrays.asList(partyNameType));
        party.setPpapCompatibilityLevel(new BigDecimal(1));
        party.setQualityIndicator(new ArrayList<>());
        party.setCertificate(new ArrayList<>());
        party.setContact(new ContactType());
        party.setDocumentReference(new ArrayList<>());
        party.setExternalAward("award");
        party.setFederationInstanceID("fedid");
        party.setProcessID(new ArrayList<>());
        party.setStripeAccountId("accountId");
        TextType industryClassification = new TextType();
        party.setIndustryClassification(industryClassification);
        party.setIndustryClassificationCode(new CodeType());
        party.setMostRecentItemsClassificationCode(new ArrayList<>());
        party.setIndustrySector(new ArrayList<>());
        party.setNACE(new ArrayList<>());
        party.setPartyTaxScheme(new ArrayList<>());
        party.setPerson(new ArrayList<>());
        party.setPostalAddress(new AddressType());
        party.setPpapDocumentReference(new ArrayList<>());
        party.setPreferredItemClassificationCode(new ArrayList<>());
        party.setPurchaseTerms(new TradingPreferences());
        party.setSalesTerms(new TradingPreferences());
        party.setProductPublishSubscription(new ProductPublishSubscriptionType());
        party.setNetwork(new ArrayList<>());
        party.setMetadata(new MetadataType());
        return party;
    }
}
