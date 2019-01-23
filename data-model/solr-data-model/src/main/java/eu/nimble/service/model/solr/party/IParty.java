package eu.nimble.service.model.solr.party;

/**
 * Interface specifying the field names for the manufacturer party
 * @author dglachs
 *
 */
public interface IParty {
	public String COLLECTION_NAME = "party";
	public String ID_FIELD = "id";
	public String BRAND_NAME_FIELD = "brandName";
	public String LEGAL_NAME_FIELD = "legalName";
	public String NAME_FIELD = "name";
	public String NAME_ML_FIELD = "*_name";
	public String ORIGIN_FIELD = "origin";
	public String CERTIFICATE_TYPE_FIELD = "certificateType";
	public String PPAP_COMPLIANCE_LEVEL_FIELD = "ppapComplianceLevel";
	public String PPAP_DOCUMENT_TYPE_FIELD = "ppapDocumentType";
	public String TRUST_SCORE_FIELD = "trustScore";
	public String TRUST_RATING_FIELD = "trustRating";
	public String TRUST_TRADING_VOLUME_FIELD = "trustTradingVolume";
	public String TRUST_SELLLER_COMMUNICATION_FIELD = "trustSellerCommunication";
	public String TRUST_FULFILLMENT_OF_TERMS_FIELD = "trustFullfillmentOfTerms";
	public String TRUST_DELIVERY_PACKAGING_FIELD = "trustDeliveryPackaging";
	public String TRUST_NUMBER_OF_TRANSACTIONS_FIELD = "trustNumberOfTransactions";
}
