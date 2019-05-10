package eu.nimble.service.model.solr.owl;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
/**
 * SOLR Document holding the properties out of 
 * any ontologies including label, range and 
 * the usage in distinct classes
 * 
 * @author dglachs
 *
 */
@SolrDocument(collection=ICodedType.COLLECTION)
public class CodedType extends Concept implements ICodedType {
	/**
	 * The uri of the property including namespace
	 */
	@Indexed(defaultValue=TYPE_VALUE, name=TYPE_FIELD)
	private String type = TYPE_VALUE;
	
	@Indexed(name=LIST_ID_FIELD)
	private String listId;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getListId() {
		return listId;
	}

	public void setListId(String codedList) {
		this.listId = codedList;
	}
	

}
