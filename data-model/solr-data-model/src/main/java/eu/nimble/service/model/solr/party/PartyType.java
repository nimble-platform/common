package eu.nimble.service.model.solr.party;

import java.util.Collection;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import eu.nimble.service.model.solr.owl.Concept;
/**
 * Class representing a manufacturer in the SOLR index
 * @author dglachs
 *
 */
@SolrDocument(collection=IParty.COLLECTION)
public class PartyType extends Concept implements IParty {

	@Indexed(name=NAME_FIELD)
	private String name;
	@Indexed(name=LEGAL_NAME_FIELD)
	private String legalName;
	@Indexed(name=BRAND_NAME_FIELD)
	private String brandName;
	@Indexed(name=ORIGIN_FIELD)
	private String origin;
	@Indexed(name=CERTIFICATE_TYPE_FIELD)
	private Collection<String> certificateType;
	@Indexed(name=PPAP_COMPLIANCE_LEVEL_FIELD)
	private String ppapComplianceLevel;
	@Indexed(name=PPAP_DOCUMENT_TYPE_FIELD)
	private String ppapDocumentType;
	@Indexed(name=TRUST_SCORE_FIELD, type="pdouble")
	private Double trustScore;
	@Indexed(name=TRUST_RATING_FIELD, type="pdouble")
	private Double trustRating;
	@Indexed(name=TRUST_TRADING_VOLUME_FIELD, type="pdouble")
	private Double trustTradingVolume;
	@Indexed(name=TRUST_SELLLER_COMMUNICATION_FIELD, type="pdouble")
	private Double trustSellerCommunication;
	@Indexed(name=TRUST_FULFILLMENT_OF_TERMS_FIELD, type="pdouble")
	private Double trustFullfillmentOfTerms;
	@Indexed(name=TRUST_DELIVERY_PACKAGING_FIELD, type="pdouble")
	private Double trustDeliveryPackaging;
	@Indexed(name=TRUST_NUMBER_OF_TRANSACTIONS_FIELD, type="pdouble")
	private Double trustNumberOfTransactions;
	public String getId() {
		return getUri();
	}
	public void setId(String id) {
		setUri(id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public Collection<String> getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(Collection<String> certificateType) {
		this.certificateType = certificateType;
	}
	public String getPpapComplianceLevel() {
		return ppapComplianceLevel;
	}
	public void setPpapComplianceLevel(String ppapComplianceLevel) {
		this.ppapComplianceLevel = ppapComplianceLevel;
	}
	public String getPpapDocumentType() {
		return ppapDocumentType;
	}
	public void setPpapDocumentType(String ppapDocumentType) {
		this.ppapDocumentType = ppapDocumentType;
	}
	public Double getTrustScore() {
		return trustScore;
	}
	public void setTrustScore(Double trustScore) {
		this.trustScore = trustScore;
	}
	public Double getTrustRating() {
		return trustRating;
	}
	public void setTrustRating(Double trustRating) {
		this.trustRating = trustRating;
	}
	public Double getTrustTradingVolume() {
		return trustTradingVolume;
	}
	public void setTrustTradingVolume(Double trustTradingVolume) {
		this.trustTradingVolume = trustTradingVolume;
	}
	public Double getTrustSellerCommunication() {
		return trustSellerCommunication;
	}
	public void setTrustSellerCommunication(Double trustSellerCommunication) {
		this.trustSellerCommunication = trustSellerCommunication;
	}
	public Double getTrustFullfillmentOfTerms() {
		return trustFullfillmentOfTerms;
	}
	public void setTrustFullfillmentOfTerms(Double trustFullfillmentOfTerms) {
		this.trustFullfillmentOfTerms = trustFullfillmentOfTerms;
	}
	public Double getTrustDeliveryPackaging() {
		return trustDeliveryPackaging;
	}
	public void setTrustDeliveryPackaging(Double trustDeliveryPackaging) {
		this.trustDeliveryPackaging = trustDeliveryPackaging;
	}
	public Double getTrustNumberOfTransactions() {
		return trustNumberOfTransactions;
	}
	public void setTrustNumberOfTransactions(Double trustNumberOfTransactions) {
		this.trustNumberOfTransactions = trustNumberOfTransactions;
	}
	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

}
