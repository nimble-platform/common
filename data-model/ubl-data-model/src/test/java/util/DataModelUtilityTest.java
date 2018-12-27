package util;

import eu.nimble.service.model.ubl.commonaggregatecomponents.*;
import eu.nimble.service.model.ubl.commonbasiccomponents.CodeType;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by suat on 25-Sep-18.
 */
public class DataModelUtilityTest {

    @Test
    public void nullifyPartyFields() throws IllegalAccessException {
        Field[] fields = PartyType.class.getDeclaredFields();
        PartyType initializedParty = createEmptyParty();

        DataModelUtility.nullifyPartyFields(initializedParty);

        for(Field f : fields) {
            if(!f.getType().isPrimitive()) {
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
            if(!f.getName().contentEquals("hjid")) {
                fieldNames.add(f.getName());
            }
        }

        PartyType p1 = new PartyType();
        DataModelUtility.nullifyPartyFields(p1);
        PartyType p2 = createEmptyParty();
        DataModelUtility.copyParty(p1, p2);

        List<String> copiedFields = new ArrayList<>();
        for(Field f : fields) {
            f.setAccessible(true);
            Object fieldValue = f.get(p1);
            if (fieldValue != null) {
                copiedFields.add(f.getName());
            }
            f.setAccessible(false);
        }

        Assert.assertTrue(copiedFields.containsAll(fieldNames) && fieldNames.containsAll(copiedFields));
    }

    private PartyType createEmptyParty() {
        PartyType party = new PartyType();
        party.setWebsiteURI("website");
        party.setID("id");
        party.setName("name");
        party.setPpapCompatibilityLevel(new BigDecimal(1));
        party.setQualityIndicator(new ArrayList<>());
        party.setCertificate(new ArrayList<>());
        party.setContact(new ContactType());
        party.setDocumentReference(new ArrayList<>());
        party.setExternalAward("award");
        party.setFederationInstanceID("fedid");
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
        return party;
    }
}
