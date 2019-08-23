package eu.nimble.service.model.ubl.document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.nimble.service.model.ubl.commonaggregatecomponents.DocumentReferenceType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.ItemType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyNameType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;

import java.util.List;

public interface IDocument {
    @JsonIgnore
    String getSellerPartyId();
    @JsonIgnore
    List<PartyNameType> getSellerPartyName();
    @JsonIgnore
    String getBuyerPartyId();
    @JsonIgnore
    List<PartyNameType> getBuyerPartyName();
    @JsonIgnore
    String getDocumentStatus();
    @JsonIgnore
    ItemType getItemType();
    @JsonIgnore
    List<DocumentReferenceType> getAdditionalDocuments();
    @JsonIgnore
    PartyType getSellerParty();
    @JsonIgnore
    PartyType getBuyerParty();
}
