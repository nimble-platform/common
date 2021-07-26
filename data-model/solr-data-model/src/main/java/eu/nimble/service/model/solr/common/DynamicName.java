package eu.nimble.service.model.solr.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.ext.com.google.common.base.CaseFormat;
import org.springframework.util.StringUtils;

public interface DynamicName {
	/**
	 * Static (mixin) helper method transforming a qualifier into a valid dynamic field part
	 * @param qualifier The qualifiers used when adding dynamic properties
	 */
	static String getDynamicFieldPart(String ... qualifier) {
		List<String> parts = new ArrayList<>();
		for ( String part : qualifier ) {
			parts.add(getDynamicFieldPart(part));
		}
		return getDynamicFieldPart(String.join("_", parts));

		
	}
	/**
	 * Static Helper for creating dynamic field parts where
	 * non alphanumeric characters are deleted and whitespaces are 
	 * replaces with underscores. Finally, the value is formatted
	 * in CamelCase format starting with lower case.
	 * As an example, the text "<b>First Name</b>" results in in a valid indexing name "<code>firstName</code>". 
	 * @param part
	 * @see CaseFormat#LOWER_CAMEL
	 * @return
	 */
	static String getDynamicFieldPart(String part) {
		if (! StringUtils.hasText(part)) {
			// when no unit code specified - use "undefined";
			return "undefined";
		}		
		String dynamicFieldPart = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, part);
		dynamicFieldPart = dynamicFieldPart.replaceAll("[^a-zA-Z0-9_ ]", "");
		dynamicFieldPart = dynamicFieldPart.trim().replaceAll(" ", "_").toUpperCase();
		dynamicFieldPart = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, dynamicFieldPart);
		return dynamicFieldPart;
	}
}
