package eu.nimble.service.model.solr;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import eu.nimble.service.model.solr.item.ICatalogueItem;
import eu.nimble.service.model.solr.owl.Concept;

@JsonInclude(value=Include.NON_EMPTY)
public class IndexField {
	/**
	 * name of the field as used in the index
	 */
	final String fieldName;

	/**
	 * The type of the field ( string, boolean, pdouble)
	 */
	String dataType;
	/**
	 * The document count for this attribute
	 */
	Integer docCount;
	/**
	 * The dynamic field name 
	 */
	String dynamicBase;
	/**
	 * The uri pointing to the concept in the <code>property</code> index
	 */
	String uri;
	/**
	 * The muliti-lingual labels obtained from the <code>property</code> index
	 */
	Map<String, String> label;
	/**
	 * The multi-lingual description obtained from the <code>property</code> index
	 */
	Map<String, String> description;
	public IndexField(String name) {
		this.fieldName = name;
	}
	public String getFieldName() {
		return fieldName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Integer getDocCount() {
		return docCount;
	}
	public void setDocCount(Integer docCount) {
		this.docCount = docCount;
	}
	public String getDynamicBase() {
		return dynamicBase;
	}
	public void setDynamicBase(String dynamicBase) {
		this.dynamicBase = dynamicBase;
	}
	/**
	 * Method to inject {@link Concept#getUri()}, {@link Concept#getLabel()} and 
	 * {@link Concept#getComment()} to the current field!
	 * @param property
	 */
	public void withNamed(Concept property) {
		this.uri=property.getUri();
		this.label = property.getLabel();
		this.description = property.getComment();
	}
	public String getDynamicPart() {
		if ( dynamicBase != null) {
			boolean leadingStar = dynamicBase.startsWith("*");
			String strippedWildcard = dynamicBase.replace("*", "");
			
			if ( leadingStar) {
				if ( fieldName.endsWith(strippedWildcard)) {
					return fieldName.substring(0, fieldName.length() - strippedWildcard.length());
				}
			}
			else {
				if ( fieldName.startsWith(strippedWildcard)) {
					return fieldName.substring(strippedWildcard.length());
				}
			}
		}
		return "";
	}
	public String getMappedName() {
		if (getDynamicBase()!=null ) {
			if ( ICatalogueItem.isFixedDynamic(getDynamicBase())) {
				return getDynamicBase();
			}
			if ( ICatalogueItem.isQualifiedDynamic(getDynamicBase())) {
				return getDynamicPart();
			}
		}
		return getFieldName();
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Map<String, String> getLabel() {
		return label;
	}
	public void setLabel(Map<String, String> label) {
		this.label = label;
	}
	public Map<String, String> getDescription() {
		return description;
	}
	public void setDescription(Map<String, String> description) {
		this.description = description;
	}
	
}
