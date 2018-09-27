package util;

import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;

/**
 * Created by suat on 25-Sep-18.
 */
public class DataModelUtility {
    public static void nullifyPartyFields(PartyType party) {
        party.setQualityIndicator(null);
        party.setCertificate(null);
        party.setContact(null);
        party.setDocumentReference(null);
        party.setIndustryClassificationCode(null);
        party.setIndustrySector(null);
        party.setMostRecentItemsClassificationCode(null);
        party.setNACE(null);
        party.setPartyTaxScheme(null);
        party.setPerson(null);
        party.setPostalAddress(null);
        party.setPpapDocumentReference(null);
        party.setPreferredItemClassificationCode(null);
        party.setPurchaseTerms(null);
        party.setSalesTerms(null);
    }

    public static void copyParty(PartyType oldParty, PartyType newParty) {
        oldParty.setID(newParty.getID());
        oldParty.setName(newParty.getName());
        oldParty.setExternalAward(newParty.getExternalAward());
        oldParty.setFederationInstanceID(newParty.getFederationInstanceID());
        oldParty.setPpapCompatibilityLevel(newParty.getPpapCompatibilityLevel());
        oldParty.setWebsiteURI(newParty.getWebsiteURI());
        oldParty.setQualityIndicator(newParty.getQualityIndicator());
        oldParty.setCertificate(newParty.getCertificate());
        oldParty.setContact(newParty.getContact());
        oldParty.setDocumentReference(newParty.getDocumentReference());
        oldParty.setIndustryClassificationCode(newParty.getIndustryClassificationCode());
        oldParty.setIndustrySector(newParty.getIndustrySector());
        oldParty.setMostRecentItemsClassificationCode(newParty.getMostRecentItemsClassificationCode());
        oldParty.setNACE(newParty.getNACE());
        oldParty.setPartyTaxScheme(newParty.getPartyTaxScheme());
        oldParty.setPerson(newParty.getPerson());
        oldParty.setPostalAddress(newParty.getPostalAddress());
        oldParty.setPpapDocumentReference(newParty.getPpapDocumentReference());
        oldParty.setPreferredItemClassificationCode(newParty.getPreferredItemClassificationCode());
        oldParty.setPurchaseTerms(newParty.getPurchaseTerms());
        oldParty.setSalesTerms(newParty.getSalesTerms());
    }
}
