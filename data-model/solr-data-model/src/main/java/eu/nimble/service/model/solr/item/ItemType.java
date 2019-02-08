package eu.nimble.service.model.solr.item;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.ext.com.google.common.base.CaseFormat;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.query.Join;
import org.springframework.data.solr.core.query.SimpleField;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eu.nimble.service.model.solr.owl.Concept;
import eu.nimble.service.model.solr.owl.IClassType;
import eu.nimble.service.model.solr.owl.PropertyType;
import eu.nimble.service.model.solr.party.IParty;
import eu.nimble.service.model.solr.party.PartyType;
/**
 * Document class representing a single product item
 * @author dglachs
 *
 */
@SolrDocument(collection=ICatalogueItem.COLLECTION)
public class ItemType extends Concept implements ICatalogueItem, Serializable {
	private static final long serialVersionUID = -3631731059281154372L;

	/**
	 * The indexed item must have a type value assigned for
	 * proper handling of nested documents
	 */
	@Indexed(name=TYPE_FIELD, defaultValue=TYPE_VALUE)
	private String type = TYPE_VALUE;
	/**
	 * The ID of the catalogue the item belongs to
	 */
	@Indexed(name=CATALOGUE_ID_FIELD)
	private String catalogueId;
	
	// PRICE & Currency
	@Indexed(name=CURRENCY_FIELD) @Dynamic
	private Map<String, String> currencyMap = new HashMap<>();
	@Indexed(name=PRICE_FIELD, type="pdouble") 
	@Dynamic
	private Map<String, Double> currencyValue = new HashMap<>();
	
	@Indexed(name=APPLICABLE_COUNTRIES_FIELD)
	private Set<String> applicableCountries;
	// FREE of charge indicator
	@Indexed(name=FREE_OF_CHARGE_FIELD,type="boolean")
	private Boolean freeOfCharge;
	// certification types 
	@Indexed(name=CERTIFICATE_TYPE_FIELD)
	private Set<String> certificateType;

	// delivery time(s)
	@Indexed(name=ESTIMATED_DELIVERY_TIME_UNIT_FIELD) @Dynamic
	private Map<String, String> deliveryTimeUnit = new HashMap<>();
	@Indexed(name=ESTIMATED_DELIVERY_TIME_FIELD, type="pdouble") @Dynamic
	private Map<String, Double> deliveryTime = new HashMap<>();
	/**
	 * Map holding a list of used Unit's for packaging
	 * The 
	 */
	@Indexed(name=PACKAGE_UNIT_FIELD) @Dynamic
	private Map<String, String> packageUnit = new HashMap<>();
	/**
	 * Map holding the amounts per package unit
	 */
	@Indexed(name=PACKAGE_AMOUNT_FILED, type="pdouble") @Dynamic
	private Map<String, List<Double>> packageAmounts =new HashMap<>();
	/**
	 * Id of the corresponding manufacturer
	 */
	@Indexed(name=MANUFACTURER_ID_FIELD) 
	private String manufacturerId;
	// Transportation Service Details
	@Indexed(name=SERVICE_TYPE_FIELD)
	private Set<String> serviceType;
	@Indexed(name=SUPPORTED_PRODUCT_NATURE_FIELD)
	private String supportedProductNature;
	@Indexed(name=SUPPORTED_CARGO_TYPE_FIELD)
	private String supportedCargoType;
	@Indexed(name=EMISSION_STANDARD_FIELD)
	private String emissionStandard;
	

	/**
	 * Possibility for joining to product class index
	 */
	@Indexed(name=COMMODITY_CLASSIFICATION_URI_FIELD)
	private List<String> classificationUri;
	//
	@Indexed(name=QUALIFIED_KEY_FIELD) @Dynamic
	private Map<String, String> propertyMap = new HashMap<>();
	@Indexed(name=QUALIFIED_STRING_FIELD, type="string") @Dynamic
	private Map<String, Collection<String>> stringValue = new HashMap<>();
	@Indexed(name=QUALIFIED_BOOLEAN_FIELD, type="boolean") @Dynamic
	private Map<String, Boolean> booleanValue = new HashMap<>();
	@Indexed(name=QUALIFIED_DOUBLE_FIELD, type="pdouble") @Dynamic
	private Map<String, Collection<Double>> doubleValue = new HashMap<>();
	
	
	@Indexed(name=IMAGE_URI_FIELD)
	private Collection<String> imgageUri;
	/**
	 * List containing multilingual labels for product classification
	 * 
	 */
	@ReadOnlyProperty
	private List<Concept> classification;
	/**
	 * Read only field - used to provide the manufacturer's details
	 * in a search result
	 */
	@ReadOnlyProperty
	private PartyType manufacturer;
	
	@ReadOnlyProperty
	private Map<String,PropertyType> customProperties;
	
	public enum JOIN_TO {
//		party(IParty.ID_FIELD, ItemType.MANUFACTURER_ID_FIELD, IParty.COLLECTION),
		// join to party type (manufacturer)
		manufacturer(IParty.ID_FIELD, ItemType.MANUFACTURER_ID_FIELD, IParty.COLLECTION, "manufacturer", "party"),
		// join to classes (furniture ontology, eClass)
		classification(IClassType.ID_FIELD, ItemType.COMMODITY_CLASSIFICATION_URI_FIELD, IClassType.COLLECTION, "productType", "classification"),
		;
		
		String from;
		String to;
		String fromIndex;
		String[] names;
		
		JOIN_TO(String from, String to, String fromIndex, String ... names) {
			this.from = from;
			this.to = to;
			this.fromIndex = fromIndex;
			this.names = names;
		}
		public static Join getJoin(String name) {
			for ( JOIN_TO j : values()) {
				if ( j.names != null ) {
					for (String s : j.names) {
						if ( s.equalsIgnoreCase(name)) {
							return j.getJoin();
						}
					}
				}
			}
			// not found - try the enum name
			try {
				// check for ItemType JOINS
				JOIN_TO join = JOIN_TO.valueOf(name.toLowerCase());
				// 
				return join.getJoin();
			} catch (Exception e) {
				// invalid join
				return null;
			}
		}		
		public Join getJoin() {
			return new Join(new SimpleField(from), new SimpleField(to), fromIndex);
		}

	}

	public void setStringProperty(String qualifier, Collection<String> values) {
		this.stringValue.put(dynamicKey(qualifier, propertyMap), values);
	}

	public void addProperty(String qualifier, String value) {
		String key = dynamicKey(qualifier, propertyMap);
		Collection<String> values = stringValue.get(key);
		if ( values == null ) {
			values = new HashSet<String>();
			this.stringValue.put(key, values);
		}
		//
		values.add(value);
	}
	public void addProperty(String qualifier, String value, PropertyType meta) {
		addProperty(qualifier, value);
		if ( meta != null) {
			// ensure the proper valueQualifier
			meta.setValueQualifier("NUMBER");
			addCustomProperty(qualifier, meta);
		}		
	}
	public void addProperty(String qualifier, String unit, Double value, PropertyType meta) {
		addProperty(qualifier, unit, value);
		if ( meta != null) {
			// ensure the proper valueQualifier
			meta.setValueQualifier("QUANTITY");
			addCustomProperty(qualifier, unit, meta);
		}		
	}
	@Deprecated
	public void addProperty(String qualifier, String unit, String value, PropertyType meta) {
		addProperty(qualifier, unit, value);
		if ( meta != null) {
			// 
			meta.setValueQualifier("TEXT");
			addCustomProperty(qualifier, unit, meta);
		}		
	}
	public void setDoubleProperty(String qualifier, Collection<Double> values) {
		this.doubleValue.put(dynamicKey(qualifier, propertyMap), values);
	}
	public void addProperty(String qualifier, Double value) {
		String key = dynamicKey(qualifier, propertyMap);
		Collection<Double> values = doubleValue.get(key);
		if ( values == null ) {
			values = new HashSet<Double>();
			this.doubleValue.put(key, values);
		}
		//
		values.add(value);
	}
	public void addProperty(String qualifier, Double value, PropertyType meta) {
		addProperty(qualifier, value);
		if ( meta != null) {
			// 
			addCustomProperty(qualifier, meta);
		}
	}
	private void addCustomProperty(String qualifier, String unit, PropertyType meta) {
		String part = dynamicFieldPart(qualifier, unit);
		if ( customProperties == null ) {
			customProperties = new HashMap<>();
		}
		customProperties.put(part, meta);
		
	}
	private void addCustomProperty(String qualifier, PropertyType meta) {
		String part = dynamicFieldPart(qualifier);
		if ( customProperties == null ) {
			customProperties = new HashMap<>();
		}
		customProperties.put(part, meta);
	}
	public void addProperty(String qualifier, String unit, String value) {
		String key = dynamicKey(propertyMap, qualifier, unit);
		Collection<String> values = this.stringValue.get(key);
		if ( values == null ) {
			values = new HashSet<String>();
			this.stringValue.put(key, values);
		}
		//
		values.add(value);
	}
	public void addProperty(String qualifier, String unit, Double value) {
		String key = dynamicKey(propertyMap, qualifier, unit);
		Collection<Double> values = doubleValue.get(key);
		if ( values == null ) {
			values = new HashSet<Double>();
			this.doubleValue.put(key, values);
		}
		//
		values.add(value);
	}
	public Collection<Double> getProperty(String qualifier, String unit) {
		String key = dynamicFieldPart(qualifier, unit);
		return this.doubleValue.get(key);
	}
	public Map<String, Boolean> getBooleanValue() {
		Map<String, Boolean> result = new HashMap<>();
		for ( String dynUnitKey : this.propertyMap.keySet()) {
			if ( booleanValue.containsKey(dynUnitKey)) {
				result.put(propertyMap.get(dynUnitKey), booleanValue.get(dynUnitKey));
			}
		}
		return result;
	}

	public void setBooleanValue(Map<String, Boolean> booleanValue) {
		if ( booleanValue != null ) {
			for (String key :  booleanValue.keySet()) {
				this.booleanValue.put(dynamicKey(key, propertyMap), booleanValue.get(key));
			}
		}
		else {
			this.booleanValue = booleanValue;
		}
	}

	public Map<String, Collection<String>> getStringValue() {
		Map<String, Collection<String>> result = new HashMap<>();
		for ( String dynUnitKey : this.propertyMap.keySet()) {
			if ( stringValue.containsKey(dynUnitKey)) {
				result.put(propertyMap.get(dynUnitKey), stringValue.get(dynUnitKey));
			}
		}
		return result;
	}
	
	public Map<String, Collection<Double>> getDoubleValue() {
		Map<String, Collection<Double>> result = new HashMap<>();
		for ( String dynUnitKey : this.propertyMap.keySet()) {
			if ( doubleValue.containsKey(dynUnitKey)) {
				result.put(propertyMap.get(dynUnitKey), doubleValue.get(dynUnitKey));
			}
		}
		return result;
	}
	public void setStringValue(Map<String, Collection<String>> stringValue) {
		if ( stringValue != null ) {
			for (String key :  stringValue.keySet()) {
				setStringProperty(key, stringValue.get(key));
			}
		}
		else {
			this.stringValue = stringValue;
		}
	}
	public void setDoubleValue(Map<String, Collection<Double>> doubleValue) {
		if ( doubleValue != null ) {
			for (String key :  doubleValue.keySet()) {
				setDoubleProperty(key, doubleValue.get(key));
			}
		}
		else {
			this.doubleValue = doubleValue;
		}
	}
	
	public void setProperty(String qualifier, Boolean value) {
		this.booleanValue.put(dynamicKey(qualifier,propertyMap), value);
	}
	public void setProperty(String qualifier, Boolean value, PropertyType meta) {
		setProperty(qualifier, value);
		if ( meta != null) {
			// 
			addCustomProperty(qualifier, meta);
		}
	}
//	
//	public Collection<String> getProperties() {
//		return propertyMap.values();
//	}
//	public void setProperties(Collection<String> qualifier) {
//		this.propertyMap.clear();
//		for ( String c : qualifier) {
//			dynamicKey(c, this.propertyMap);
//		}
//	}
	/**
	 * GETTER for the URI
	 * @return
	 */
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	/**
	 * For proper distinction of catalogue items and nested
	 * documents, the item must hava a type field 
	 * @see #TYPE_VALUE
	 * @return
	 */
	@JsonIgnore
	public String getTypeValue() {
		return type;
	}
	public void setTypeValue(String type) {
		this.type = type;
	}
	public String getCatalogueId() {
		return catalogueId;
	}
	public void setCatalogueId(String catalogueId) {
		this.catalogueId = catalogueId;
	}
//		this.languages = language;
	public Boolean getFreeOfCharge() {
		return freeOfCharge;
	}
	public void setFreeOfCharge(Boolean freeOfCharge) {
		this.freeOfCharge = freeOfCharge;
	}
	public Set<String> getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(Set<String> certificateType) {
		this.certificateType = certificateType;
	}
	public Set<String> getApplicableCountries() {
		return applicableCountries;
	}
	public void setApplicableCountries(Set<String> applicableCountries) {
		this.applicableCountries = applicableCountries;
	}


	public String getManufacturerId() {
		if (manufacturer != null && manufacturer.getId() != null) {
			return manufacturer.getId();
		}
		return manufacturerId;
	}
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public Set<String> getServiceType() {
		return serviceType;
	}
	public void setServiceType(Set<String> serviceType) {
		this.serviceType = serviceType;
	}
	public String getSupportedProductNature() {
		return supportedProductNature;
	}
	public void setSupportedProductNature(String supportedProductNature) {
		this.supportedProductNature = supportedProductNature;
	}
	public String getSupportedCargoType() {
		return supportedCargoType;
	}
	public void setSupportedCargoType(String supportedCargoType) {
		this.supportedCargoType = supportedCargoType;
	}
	public String getEmissionStandard() {
		return emissionStandard;
	}
	public void setEmissionStandard(String emissionStandard) {
		this.emissionStandard = emissionStandard;
	}

	/**
	 * Helper method adding a (language based) description to the item
	 * @param language The language (en, es, de)
	 * @param desc The description in the provided language
	 */
	public void addDescription(String language, String desc) {
		if ( this.description == null) {
			this.description = new HashMap<>();
		}
		this.description.put(language, desc);
		// 
		addLanguage(language);
	}
	/**
	 * Helper method used to maintain the list of 
	 * used languages
	 * @param language
	 */
	private void addLanguage(String language) {
		if ( this.languages == null) {
			this.languages = new HashSet<String>();
		}
		if ( ! this.languages.contains(language)) {
			this.languages.add(language);
		}
	}
	public void addPrice(String currency, Double price) {
		this.currencyValue.put(dynamicKey(currency, this.currencyMap), price);
	}
	public Collection<String> getCurrency() {
		return this.currencyMap.values();
	}

	public void setCurrency(Collection<String> currency) {
		this.currencyMap.clear();
		for ( String c : currency) {
			dynamicKey(c, this.currencyMap);
		}
	}
	public Map<String, Double> getPrice() {
		Map<String, Double> ret = new HashMap<>();
		for ( String key : currencyMap.keySet()) {
			ret.put(currencyMap.get(key), currencyValue.get(key));
		}
		return ret;
	}
	public void setPrice(Map<String, Double> price) {
		this.currencyValue.clear();
		for ( String c : price.keySet()) {
			addPrice(c, price.get(c));
		}
	}
	@JsonIgnore
	public Map<String, String> getDeliveryTimeUnit() {
		return deliveryTimeUnit;
	}
	public Collection<String> getDeliveryTimeUnits() {
		return this.deliveryTimeUnit.values();
	}
	public void setDeliveryTimeUnits(Collection<String> units) {
		this.deliveryTimeUnit.clear();
		for ( String unit : units ) {
			// update the packageUnit
			dynamicKey(unit,  this.deliveryTimeUnit);
		}
	}
	/**
	 * Add a new delivery time to the item. 
	 * @param unit The unit such as <i>Week(s)</i>, <i>Day(s)</i>
	 * @param time The amount of the delivery time unit
	 */
	public void addDeliveryTime(String unit, Double time) {
		this.deliveryTime.put(dynamicKey(unit, this.deliveryTimeUnit), time);
	}
	/**
	 * Getter for the delivery times per unit
	 * @return
	 */
	public Map<String, Double> getDeliveryTime() {
		Map<String, Double> result = new HashMap<>();
		for ( String dynUnitKey : this.deliveryTimeUnit.keySet()) {
			result.put(deliveryTimeUnit.get(dynUnitKey), deliveryTime.get(dynUnitKey));
		}
		return result;
	}
	public void setDeliveryTime(Map<String, Double> deliveryTime) {
		this.deliveryTime.clear();
		for ( String c : deliveryTime.keySet()) {
			addDeliveryTime(c, deliveryTime.get(c));
		}
	}

	/**
	 * Internally the package units hold
	 * @return
	 */
	@JsonIgnore
	public Map<String, String> getPackageUnit() {
		return packageUnit;
	}
	public Collection<String> getPackageUnits() {
		return this.packageUnit.values();
	}
	public void setPackageUnits(Collection<String> units) {
		this.packageUnit.clear();
		for ( String unit : units ) {
			// update the packageUnit
			dynamicKey(unit,  this.packageUnit);
		}
	}
	public void addPackageAmounts(String unit, List<Double> amounts) {
		this.packageAmounts.put(dynamicKey(unit, this.packageUnit), amounts);;
	}
	/**
	 * Getter for the package amounts per unit
	 * @return
	 */
	public Map<String, List<Double>> getPackageAmounts() {
		Map<String, List<Double>> result = new HashMap<>();
		for ( String dynUnitKey : this.packageUnit.keySet()) {
			result.put(packageUnit.get(dynUnitKey), packageAmounts.get(dynUnitKey));
		}
		return result;
	}
	public void setPackageAmounts(Map<String, List<Double>> packageAmountPerUnit) {
		this.packageAmounts.clear();
		for ( String key : packageAmountPerUnit.keySet()) {
			addPackageAmounts(key, packageAmountPerUnit.get(key));
		}
	}
	public PartyType getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(PartyType manufacturer) {
		this.manufacturer = manufacturer;
	}
	public List<Concept> getClassification() {
		if ( classification == null) {
			classification = new ArrayList<>();
		}
		return classification;
	}
	public void addClassification(Concept c) {
		getClassification().add(c);
	}
	public void setClassification(List<Concept> classification) {
		this.classification = classification;
	}
	public List<String> getClassificationUri() {
		return classificationUri;
	}
	public void setClassificationUri(List<String> classificationUri) {
		this.classificationUri = classificationUri;
	}
	public Collection<String> getImgageUri() {
		return imgageUri;
	}
	public void setImgageUri(Collection<String> imgageUri) {
		this.imgageUri = imgageUri;
	}
	
	public Map<String, PropertyType> getCustomProperties() {
		return customProperties;
	}


	public void setCustomProperties(Map<String, PropertyType> customProperties) {
		this.customProperties = customProperties;
	}
	/**
	 * Helper method to create the index field's name part and
	 * to maintain the label for the corresponding name map
	 * @param keyVal
	 * @param keyMap
	 * @return
	 */
	private String dynamicKey(String keyVal, Map<String, String> keyMap) {
		String key = dynamicFieldPart(keyVal);
		keyMap.put(key, keyVal);
		return key;
	}
	private String dynamicKey(Map<String, String> keyMap, String ... keyPart) {
		String key = dynamicFieldPart(keyPart);
		keyMap.put(key, String.join(" ", keyPart));
		return key;
	}
	public static String dynamicFieldPart(String fieldPart) {
		if (! StringUtils.hasText(fieldPart)) {
			// when no unit code specified - use "undefined";
			return "undefined";
		}
		String dynamicFieldPart = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, fieldPart);
		dynamicFieldPart = dynamicFieldPart.replaceAll("[^a-zA-Z0-9_ ]", "");
		dynamicFieldPart = dynamicFieldPart.trim().replaceAll(" ", "_").toUpperCase();
		dynamicFieldPart = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, dynamicFieldPart);
		return dynamicFieldPart;
		
	}
	
	public static String dynamicFieldPart(String ...strings) {
		List<String> parts = new ArrayList<>();
		for ( String part : strings ) {
			parts.add(dynamicFieldPart(part));
		}
		return String.join("_", parts);
	}

}
