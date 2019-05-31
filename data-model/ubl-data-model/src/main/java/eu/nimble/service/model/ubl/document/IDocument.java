package eu.nimble.service.model.ubl.document;

import eu.nimble.service.model.ubl.commonaggregatecomponents.ItemType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyNameType;

import java.util.List;

public interface IDocument {
    String getSellerPartyId();

    List<PartyNameType> getSellerPartyName();

    String getBuyerPartyId();

    List<PartyNameType> getBuyerPartyName();

    String getDocumentStatus();

    ItemType getItemType();
}
