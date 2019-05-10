package eu.nimble.service.model.solr.owl;

public interface ICodedType extends IConcept {
	/**
	 * Name of the properties collection
	 */
	String COLLECTION = "codes";
	/**
	 * Field pointing to the contained SOLR documents
	 */
	String TYPE_FIELD = "doctype";
	/**
	 * Value Constant for the contained SOLR documents
	 */
	String TYPE_VALUE = "code";
	/**
	 * Value constant for the list resource
	 */
	String LIST_ID_FIELD = "codedList";
	

}
