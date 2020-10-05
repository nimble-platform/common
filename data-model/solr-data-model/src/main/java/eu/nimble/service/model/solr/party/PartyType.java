package eu.nimble.service.model.solr.party;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.data.solr.core.mapping.Dynamic;
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

//	@Indexed(name=NAME_FIELD)
//	private String name;
	@Indexed(name=LEGAL_NAME_FIELD)
	private String legalName;
	/**
	 * Lowercase of the legal name to be used for the sorting
	 * */
	@Indexed(name=LOWERCASE_LEGAL_NAME_FIELD)
	private String lowercaseLegalName;
	@Indexed(name=BRAND_NAME_FIELD) @Dynamic
	private Map<String, String> brandName;
	@Indexed(name=ORIGIN_FIELD) @Dynamic
	private Map<String,String> origin;
	@Indexed(name=CERTIFICATE_TYPE_FIELD, type="string") @Dynamic
	private Map<String,Collection<String>> certificateType;
	@Indexed(name=PPAP_COMPLIANCE_LEVEL_FIELD, type="pint")
	private Integer ppapComplianceLevel;
	@Indexed(name=PPAP_DOCUMENT_TYPE_FIELD) @Dynamic
	private Map<String,String> ppapDocumentType;
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
	@Indexed(name=TRUST_NUMBER_OF_EVALUATIONS_FIELD, type="pdouble",required=false)
	private Double trustNumberOfEvaluations;
	@Indexed(name= LOGO_ID_FIELD)
	private String logoId;
	@Indexed(name=BUSINESS_TYPE_FIELD)
	private String businessType;
	@Indexed(name= ACTIVITY_SECTORS_FIELD, type="string")
	@Dynamic
	private Map<String, Collection<String>> activitySectors;
	@Indexed(name=BUSINESS_KEYWORDS_FIELD, type="string")
	@Dynamic
	protected Map<String, Collection<String>> businessKeywords;

	@Indexed(name=VERIFIED_FIELD,type="boolean")
	private Boolean isVerified = false;

	@Indexed(name=WEBSITE_FIELD, type="string")
	private String website;

	public String getId() {
		return getUri();
	}
	public void setId(String id) {
		setUri(id);
	}
//	public String getName() {
//		return name;
//	}
//	public void setName(String name) {
//		this.name = name;
//	}
	
	/**
	 * Getter for the multilingual origin labels
	 */
	public Map<String, String> getOrigin() {
		return origin;
	}
	public void setOrigin(Map<String, String> originMap) {
		if ( originMap != null ) {
			for ( String key : originMap.keySet() ) {
				addOrigin(key, originMap.get(key));
			}
		}
		else {
			this.origin = null;
		}
	}
	/**
	 * Helper method for adding a multilingual origin to the concept. Only one origin per language is stored.
	 * This method maintains the list of languages in use, see {@link #getLanguages()}
	 * @param language The language code such as <i>en</i>, <i>es</i>
	 * @param label The respective label for the origin
	 */
	public void addOrigin(String language, String label) {
		if ( this.origin == null) {
			this.origin = new HashMap<>();
		}
		this.origin.put(language, label);
		// 
		addLanguage(language);
	}
	/**
	 * Getter for the multilingual origin labels
	 */
	public Map<String, String> getBrandName() {
		return brandName;
	}
	public void setBrandName(Map<String, String> brandNameMap) {
		if ( brandNameMap != null ) {
			for ( String key : brandNameMap.keySet() ) {
				addBrandName(key, brandNameMap.get(key));
			}
		}
		else {
			this.brandName = null;
		}
	}
	/**
	 * Helper method for adding a multilingual origin to the concept. Only one origin per language is stored.
	 * This method maintains the list of languages in use, see {@link #getLanguages()}
	 * @param language The language code such as <i>en</i>, <i>es</i>
	 * @param label The respective label for the brandName
	 */
	public void addBrandName(String language, String label) {
		if ( this.brandName == null) {
			this.brandName = new HashMap<>();
		}
		this.brandName.put(language, label);
		// 
		addLanguage(language);
	}

	
	public Map<String,Collection<String>> getCertificateType() {
		return certificateType;
	}

	/**
	 * Setter for the certificateType labels
	 * @param certificateTypeMap
	 */
	public void setCertificateType(Map<String, Collection<String>> certificateTypeMap) {
		if ( certificateTypeMap != null ) {
			for ( String lang : certificateTypeMap.keySet() ) {
				for (String label : certificateTypeMap.get(lang)) {
					addCertificateType(lang, label);
					
				}
			}
		}
		else {
			this.certificateType = null;
		}
	}
	/**
	 * Helper method for adding a (multilingual) label to the list of certificat types 
	 * @param language
	 * @param certificatTypeLabel
	 */
	public void addCertificateType(String language, String certificatTypeLabel) {
		if (this.certificateType ==null) {
			this.certificateType = new HashMap<>();
		}
		if ( !this.certificateType.containsKey(language)) {
			this.certificateType.put(language, new HashSet<>());
		}
		this.certificateType.get(language).add(certificatTypeLabel);
		// 
		addLanguage(language);
	}
	
	public Integer getPpapComplianceLevel() {
		return ppapComplianceLevel;
	}
	public void setPpapComplianceLevel(Integer ppapComplianceLevel) {
		this.ppapComplianceLevel = ppapComplianceLevel;
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
	public Double getTrustNumberOfEvaluations() { return trustNumberOfEvaluations; }
	public void setTrustNumberOfEvaluations(Double trustNumberOfEvaluations) {
		this.trustNumberOfEvaluations = trustNumberOfEvaluations;
	}

	public String getLegalName() {
		return legalName;
	}
	public void setLegalName(String legalName) {
		this.legalName = legalName;
		if(legalName != null){
			this.lowercaseLegalName = legalName.toLowerCase();
		}
	}
	/**
	 * Getter for the multilingual origin labels
	 */
	public Map<String, String> getPpapDocumentType() {
		return ppapDocumentType;
	}
	public void setPpapDocumentType(Map<String, String> originMap) {
		if ( originMap != null ) {
			for ( String key : originMap.keySet() ) {
				addLabel(key, originMap.get(key));
			}
		}
		else {
			this.ppapDocumentType = null;
		}
	}
	/**
	 * Helper method for adding a multilingual PPAP Document Type labels to the concept. Only one label per language is stored.
	 * This method maintains the list of languages in use, see {@link #getLanguages()}
	 * @param language The language code such as <i>en</i>, <i>es</i>
	 * @param label The respective label for the PPAP Document Type
	 */
	public void addPpapDocumentType(String language, String label) {
		if ( this.ppapDocumentType == null) {
			this.ppapDocumentType = new HashMap<>();
		}
		this.ppapDocumentType.put(language, label);
		// 
		addLanguage(language);
	}

	public String getLogoId() {
		return logoId;
	}

	public void setLogoId(String logoId) {
		this.logoId = logoId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Map<String, Collection<String>> getActivitySectors() {
		return activitySectors;
	}

	public void setActivitySectors(Map<String, Collection<String>> activitySectors) {
		this.activitySectors = activitySectors;
	}

	public Map<String, Collection<String>> getBusinessKeywords() {
		return businessKeywords;
	}

	public void setBusinessKeywords(Map<String, Collection<String>> businessKeywords) {
		this.businessKeywords = businessKeywords;
	}

	public void addActivitySector(String language, String activitySector) {
		if (this.activitySectors ==null) {
			this.activitySectors = new HashMap<>();
		}
		if ( !this.activitySectors.containsKey(language)) {
			this.activitySectors.put(language, new HashSet<>());
		}
		this.activitySectors.get(language).add(activitySector);
		addLanguage(language);
	}

	public void addBusinessKeyword(String language,String businessKeyword) {
		if (this.businessKeywords ==null) {
			this.businessKeywords = new HashMap<>();
		}
		if ( !this.businessKeywords.containsKey(language)) {
			this.businessKeywords.put(language, new HashSet<>());
		}
		this.businessKeywords.get(language).add(businessKeyword);
		addLanguage(language);
	}


	public Boolean getVerified() {
		return isVerified;
	}

	public void setVerified(Boolean verified) {
		isVerified = verified;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}
}
