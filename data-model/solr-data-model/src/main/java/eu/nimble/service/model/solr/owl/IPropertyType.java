package eu.nimble.service.model.solr.owl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.solr.core.query.Field;
import org.springframework.data.solr.core.query.SimpleField;

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
	String UNITS_TYPE_FIELD = "units";
	String UNITS_TYPES_LIST_FIELD = "unitsList";
	String VALUE_CODE__FIELD = "valueCode";
	String VALUE_CODES_LIST_FIELD = "valueCodesList";
	String IS_HIDDEN_ON_UI_FIELD = "isHiddenOnUI";
	/**
	 * Define the default field list 
	 * 
	 * @return
	 */
	public static String[] defaultFieldNames() {
		return new String[] {
				TYPE_FIELD, 
				IS_FACET_FIELD, 
				BOOST_FIELD, 
				IDX_FIELD_NAME_FIELD,
				PROPERTY_TYPE_FIELD, 
				LABEL_FIELD, 
				ALTERNATE_LABEL_FIELD, 
				HIDDEN_LABEL_FIELD, 
				LANGUAGES_FIELD, 
				LANGUAGE_TXT_FIELD,
				LOCAL_NAME_FIELD, 
				NAME_SPACE_FIELD, 
				ID_FIELD, 
				COMMENT_FIELD, 
				DESCRIPTION_FIELD,
				RANGE_FIELD,
				VALUE_QUALIFIER_FIELD
		};
		
	}
	public static List<Field> defaultFields() {
		List<Field> f = new ArrayList<>();
		for ( String s : defaultFieldNames()) {
			f.add(new SimpleField(s));
		}
		return f;
	}
}
