package eu.nimble.service.model.solr.owl;

public interface IClassType extends IConcept {
	String COLLECTION = "class";
	String TYPE_FIELD = "doctype";
	String TYPE_VALUE = "class";
	String PROPERTIES_FIELD = "properties";
	String PARENTS_FIELD = "parents";
	String ALL_PARENTS_FIELD = "allParents";
	String CHILDREN_FIELD ="children";
	String ALL_CHILDREN_FIELD ="allChildren";
	
	String LEVEL_FIELD = "level";
	
}
