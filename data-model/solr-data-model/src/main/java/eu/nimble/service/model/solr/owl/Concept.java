package eu.nimble.service.model.solr.owl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Dynamic;
import org.springframework.data.solr.core.mapping.Indexed;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(content=Include.NON_EMPTY)
public abstract class Concept implements IConcept {
	/**
	 * The uri of the property including namespace
	 */
	
	@Id
	@Indexed(required=true, name=ID_FIELD) 
	protected String uri;

	@Indexed(name=LOCAL_NAME_FIELD)
	protected String localName;
	
	@Indexed(name=NAME_SPACE_FIELD)
	protected String nameSpace;	

	@Indexed(name=LANGUAGES_FIELD)
	protected Collection<String> languages;
	@Indexed(name=LABEL_FIELD, copyTo= {LANGUAGE_TXT_FIELD, TEXT_FIELD})
	@Dynamic
	protected Map<String, String> label;
	@Indexed(name=ALTERNATE_LABEL_FIELD, type="string", copyTo= {LANGUAGE_TXT_FIELD, TEXT_FIELD})
	@Dynamic
	protected Map<String, Collection<String>> alternateLabel;
	@Indexed(name=HIDDEN_LABEL_FIELD, type="string", copyTo= {LANGUAGE_TXT_FIELD, TEXT_FIELD})
	@Dynamic
	protected Map<String, Collection<String>> hiddenLabel;
	@Indexed(name=DESCRIPTION_FIELD,copyTo= {LANGUAGE_TXT_FIELD, TEXT_FIELD})
	@Dynamic
	protected Map<String, String> description;
	@Indexed(name=COMMENT_FIELD,copyTo= {LANGUAGE_TXT_FIELD, TEXT_FIELD})
	@Dynamic
	protected Map<String, String> comment;

	public Concept() {
		super();
	}
	static class SimpleConcept extends Concept {
		
	}
	public static Concept buildFrom(IConcept other) {
		SimpleConcept c = new Concept.SimpleConcept();
		c.setUri(other.getUri());
		c.setLabel(other.getLabel());
		c.setDescription(other.getDescription());
		c.setComment(other.getComment());
		c.setLanguages(other.getLanguages());
		c.setNameSpace(other.getNameSpace());
		c.setLocalName(other.getLocalName());
		return c;
	}

	public Collection<String> getLanguages() {
		return languages;
	}
	

	public void setLanguages(Collection<String> languages) {
		this.languages = languages;
	}

	public Map<String, String> getLabel() {
		return label;
	}
	public void addLabel(String language, String label) {
		if ( this.label == null) {
			this.label = new HashMap<>();
		}
		this.label.put(language, label);
		// 
		addLanguage(language);
	}
	public void addAlternateLabel(String language, String alternate) {
		if (this.alternateLabel ==null) {
			this.alternateLabel = new HashMap<>();
		}
		if ( !this.alternateLabel.containsKey(language)) {
			this.alternateLabel.put(language, new HashSet<>());
		}
		this.alternateLabel.get(language).add(alternate);
		// 
		addLanguage(language);
	}
	public void addHiddenLabel(String language, String alternate) {
		if (this.hiddenLabel ==null) {
			this.hiddenLabel = new HashMap<>();
		}
		if ( !this.hiddenLabel.containsKey(language)) {
			this.hiddenLabel.put(language, new HashSet<>());
		}
		this.hiddenLabel.get(language).add(alternate);
		// 
		addLanguage(language);
	}
	private void addLanguage(String language) {
		if ( this.languages == null) {
			this.languages = new HashSet<String>();
		}
		this.languages.add(language);
	}

	public void setLabel(Map<String, String> labelMap) {
		if ( labelMap != null ) {
			for ( String key : labelMap.keySet() ) {
				addLabel(key, labelMap.get(key));
			}
		}
		else {
			this.label = null;
		}
	}
	/**
	 * Retrieve all comment entries
	 * @return
	 */
	public Map<String, String> getComment() {
		return comment;
	}
	public void addComment(String language, String comment) {
		if ( this.comment == null) {
			this.comment = new HashMap<>();
		}
		this.comment.put(language, comment);
		// be sure to have all stored languages in the language list
		addLanguage(language);
	}

	public void setComment(Map<String, String> commentMap) {
		if ( commentMap != null ) {
			for ( String key : commentMap.keySet() ) {
				addComment(key, commentMap.get(key));
			}
		}
		else {
			this.comment = null;
		}
	}
	public void addDescription(String language, String desc) {
		if ( this.description == null) {
			this.description = new HashMap<>();
		}
		this.description.put(language, desc);
		// be sure to have all stored languages in the language list
		addLanguage(language);
	}
	public Map<String, String> getDescription() {
		return description;
	}

	public void setDescription(Map<String, String> descMap) {
		if ( descMap != null ) {
			for ( String key : descMap.keySet() ) {
				addComment(key, descMap.get(key));
			}
		}
		else {
			this.comment = null;
		}
	}
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	public Map<String, Collection<String>> getAlternateLabel() {
		return alternateLabel;
	}

	public void setAlternateLabel(Map<String, Collection<String>> alternateLabel) {
		this.alternateLabel = alternateLabel;
	}

	public Map<String, Collection<String>> getHiddenLabel() {
		return hiddenLabel;
	}

	public void setHiddenLabel(Map<String, Collection<String>> hiddenLabel) {
		this.hiddenLabel = hiddenLabel;
	}
	
	

}