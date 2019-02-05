package eu.nimble.service.model.solr.owl;

public interface IPropertyType extends IConcept {
	String COLLECTION = "props";
	String TYPE_FIELD = "doctype";
	String TYPE_VALUE = "property";
	String IS_FACET_FIELD = "isFacet";
	String BOOST_FIELD = "boost";
	String RANGE_FIELD = "range";
	String VALUE_QUALIFIER_FIELD = "valueQualifier";
	String USED_WITH_FIELD = "used_in";
	String USED_BY_FIELD = "used_by";
	// 
	String IDX_FIELD_NAME_FIELD = "idxField";
	
	String PROPERTY_TYPE_FIELD = "propType";
	
	public static String[] fieldList() {
		return new String[] {TYPE_FIELD, IS_FACET_FIELD, BOOST_FIELD, IDX_FIELD_NAME_FIELD,
			PROPERTY_TYPE_FIELD, LABEL_FIELD, ALTERNATE_LABEL_FIELD, HIDDEN_LABEL_FIELD, LANGUAGE_TXT_FIELD,
			LOCAL_NAME_FIELD, NAME_SPACE_FIELD, ID_FIELD, COMMENT_FIELD, DESCRIPTION_FIELD, RANGE_FIELD,
			VALUE_QUALIFIER_FIELD
		};
	}
}
