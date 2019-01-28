package util;

import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;

/**
 * Created by suat on 25-Sep-18.
 */
public class DataModelUtility {
    /**
     * Nullify all the direct fields of a {@link PartyType}.
     *
     * @param party
     */
    public static void nullifyPartyFields(PartyType party) {
        party.setHjid(null);
        nullifyPartyFieldsExceptHjid(party);
    }

    public static void nullifyPartyFieldsExceptHjid(PartyType party) {
        party.setExternalAward(null);
        party.setFederationInstanceID(null);
        party.setPpapCompatibilityLevel(null);
        party.setWebsiteURI(null);
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
        party.setBrandName(null);
        party.setDescription(null);
        party.setPartyIdentification(null);
        party.setPartyName(null);
    }

    /**
     * Copies the information from {@code sourceParty} to {@code targetParty}.
     *
     * @param targetParty
     * @param sourceParty
     */
    public static void copyParty(PartyType targetParty, PartyType sourceParty) {
        targetParty.setHjid(sourceParty.getHjid());
        copyPartyExceptHjid(targetParty, sourceParty);
    }

    public static void copyPartyExceptHjid(PartyType targetParty, PartyType sourceParty) {
        targetParty.setPartyIdentification(sourceParty.getPartyIdentification());
        targetParty.setPartyName(sourceParty.getPartyName());
        targetParty.setDescription(sourceParty.getDescription());
        targetParty.setBrandName(sourceParty.getBrandName());
        targetParty.setExternalAward(sourceParty.getExternalAward());
        targetParty.setFederationInstanceID(sourceParty.getFederationInstanceID());
        targetParty.setPpapCompatibilityLevel(sourceParty.getPpapCompatibilityLevel());
        targetParty.setWebsiteURI(sourceParty.getWebsiteURI());
        targetParty.setQualityIndicator(sourceParty.getQualityIndicator());
        targetParty.setCertificate(sourceParty.getCertificate());
        targetParty.setContact(sourceParty.getContact());
        targetParty.setDocumentReference(sourceParty.getDocumentReference());
        targetParty.setIndustryClassificationCode(sourceParty.getIndustryClassificationCode());
        targetParty.setIndustrySector(sourceParty.getIndustrySector());
        targetParty.setMostRecentItemsClassificationCode(sourceParty.getMostRecentItemsClassificationCode());
        targetParty.setNACE(sourceParty.getNACE());
        targetParty.setPartyTaxScheme(sourceParty.getPartyTaxScheme());
        targetParty.setPerson(sourceParty.getPerson());
        targetParty.setPostalAddress(sourceParty.getPostalAddress());
        targetParty.setPpapDocumentReference(sourceParty.getPpapDocumentReference());
        targetParty.setPreferredItemClassificationCode(sourceParty.getPreferredItemClassificationCode());
        targetParty.setPurchaseTerms(sourceParty.getPurchaseTerms());
        targetParty.setSalesTerms(sourceParty.getSalesTerms());
    }
}
