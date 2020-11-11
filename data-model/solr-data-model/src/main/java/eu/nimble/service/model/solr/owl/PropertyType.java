package eu.nimble.service.model.solr.owl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.fasterxml.jackson.annotation.JsonIgnore;
/**
 * SOLR Document holding the properties out of 
 * any ontologies including label, range and 
 * the usage in distinct classes
 * 
 * @author dglachs
 *
 */
@SolrDocument(collection=IPropertyType.COLLECTION)
public class PropertyType extends Concept implements IPropertyType {
	/**
	 * The uri of the property including namespace
	 */
	@Indexed(defaultValue=TYPE_VALUE, name=TYPE_FIELD)
	private String type = TYPE_VALUE;

	@Indexed(required=false, name=RANGE_FIELD) 
	private String range;
	
	@Indexed(required=false, type="string", name=VALUE_QUALIFIER_FIELD)
	private ValueQualifier valueQualifier;
	
	@Indexed(required=false, name=USED_WITH_FIELD)
	private Collection<String> product;
	
	@Indexed(required=false, name=USED_BY_FIELD)
	private Collection<String> items;

	@Indexed(required=false, name=IDX_FIELD_NAME_FIELD)
	private Collection<String> itemFieldNames;
	
	@Indexed(required=false, name=IS_FACET_FIELD)
	private boolean facet = true;

	@Indexed(required=false, name=IS_VISIBLE_FIELD)
	private boolean visible = true;

	@Indexed(required=false, name=IS_REQUIRED_FIELD)
	private boolean required = false;

	@Indexed(required=false, name=BOOST_FIELD, type="pdouble")
	private Double boost;
	
	@Indexed(required=false, name=PROPERTY_TYPE_FIELD)
	private String propertyType;

//	@Indexed(required=false, name=PEFERRED_VALUE_CODE_FIELD)
//	private String valueCode;

	@Indexed(required=false, name= CODE_LIST_FIELD)
	private Collection<String> codeList;

	@Indexed(required=false, name=CODE_LIST_ID_FIELD)
	private String codeListId;

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	@JsonIgnore
	public Collection<String> getUnits() {
		return codeList;
	}

	public void setUnits(Collection<String> unitsTypeList) {
		this.codeList = unitsTypeList;
	}

    public Collection<String> getCodeList() {
    	if ( codeList == null) {
    		codeList = new HashSet<String>();
    	}
		return codeList;
	}
	public void setCodeList(Collection<String> valueCodesList) {
		this.codeList = valueCodesList;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public Collection<String> getProduct() {
		if (product == null ) {
			product = new HashSet<String>(); 
		}
		return product;
	}
	public void addProduct(String className) {
		if ( this.product == null ) {
			this.product = new HashSet<>();
		}
	}
	public void setProduct(Collection<String> className) {
		this.product = className;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Collection<String> getItemFieldNames() {
		return itemFieldNames;
	}

	public void setItemFieldNames(Collection<String> idxFieldNames) {
		this.itemFieldNames = idxFieldNames;
	}
	public void addItemFieldName(String idxField) {
		if (itemFieldNames==null) {
			itemFieldNames=new HashSet<>();
		}
		else if (! (itemFieldNames instanceof Set)) {
			// ensure to have a new set (to avoid duplicates)
			itemFieldNames = itemFieldNames.stream().collect(Collectors.toSet());
		}
		this.itemFieldNames.add(idxField);
	}

	public ValueQualifier getValueQualifier() {
		return valueQualifier;
	}

	public void setValueQualifier(ValueQualifier valueQualifier) {
		this.valueQualifier = valueQualifier;
	}
	@JsonIgnore
	public boolean isFacet() {
		return facet;
	}

	public void setFacet(boolean facet) {
		this.facet = facet;
	}
	@JsonIgnore
	public Double getBoost() {
		return boost;
	}

	public void setBoost(Double boost) {
		this.boost = boost;
	}

	
	public Collection<String> getItems() {
		return items;
	}

	public void setItems(Collection<String> items) {
		this.items = items;
	}
	public void addItem(String uri) {
		if ( items == null ) {
			items = new HashSet<>();
		}
		items.add(uri);
	}
	public boolean removeItem(String item) {
		if ( items == null ) {
			return false;
		}
		return items.remove(item);
		
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getCodeListId() {
		return codeListId;
	}

	public void setCodeListId(String codeListUri) {
		this.codeListId = codeListUri;
	}
}
