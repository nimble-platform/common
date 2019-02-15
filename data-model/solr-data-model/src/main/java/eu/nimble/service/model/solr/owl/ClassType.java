package eu.nimble.service.model.solr.owl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.query.Join;
import org.springframework.data.solr.core.query.SimpleField;


/**
 * SOLR Document Class mapping a ClassType Concept.
 * <p>
 * The ClassType class covers owl:Classes and als eCl@ss Concepts.
 * 
 * </p>
 * @author dglachs
 *
 */

@SolrDocument(collection="class")
public class ClassType extends Concept implements IClassType {
	@Indexed(defaultValue=TYPE_VALUE, name=TYPE_FIELD)
	private String type = TYPE_VALUE;
	
	@Indexed(required=false, name=PROPERTIES_FIELD)
	private Collection<String> properties;

	@Indexed(required=false, name=PARENTS_FIELD)
	private Collection<String> parents;
	@Indexed(required=false, name=CHILDREN_FIELD)
	private Collection<String> children;
	
	@Indexed(required=false, name=ALL_PARENTS_FIELD)
	private Collection<String> allParents;
	@Indexed(required=false, name=ALL_CHILDREN_FIELD)
	private Collection<String> allChildren;

	@Indexed(required=false, name=LEVEL_FIELD, type="pint")
	private Integer level;
	/**
	 * Getter for the document type - <code>class</code>
	 * @return
	 */
	public String getType() {
		return type;
	}
	/**
	 * Setter for the document type - do not use
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * Getter for the collection of related properties. 
	 * @return The list of URI's pointing to related properties
	 */
	public Collection<String> getProperties() {
		return properties;
	}
	/**
	 * Helper method for adding single properties to the 
	 * current list
	 * @param property The URI of a related propery
	 */
	public void addProperty(String property) {
		if (this.properties == null ) {
			this.properties = new HashSet<>();
		}
		this.properties.add(property);
	}
	/**
	 * Setter for the properties
	 * @param properties
	 */
	public void setProperties(Collection<String> properties) {
		this.properties = properties;
	}
	public void addParent(String superClass) {
		if (this.parents == null ) {
			this.parents = new HashSet<>();
		}
		this.parents.add(superClass);
	}
	/**
	 * Getter for the URI pointing to direct super classes
	 * @return
	 */
	public Collection<String> getParents() {
		return parents;
	}
	/**
	 * Setter for the direct parents
	 * @param parent A list of URI'S pointing to the direct parents
	 */
	public void setParents(Collection<String> parent) {
		this.parents = parent;
	}
	/**
	 * Helper method for adding a single child (or direct sub-class)
	 * @param childClass
	 */
	public void addChild(String childClass) {
		if (this.children == null ) {
			this.children = new HashSet<>();
		}
		this.children.add(childClass);
	}
	/**
	 * Retrieve the list of all direct sub-classes
	 * @return
	 */
	public Collection<String> getChildren() {
		return children;
	}
	/**
	 * Set the list of all direct subclasses
	 * @param child
	 */
	public void setChildren(Collection<String> child) {
		this.children = child;
	}
	/** 
	 * Helper method for adding a single parent (all uppper levels)
	 * @param superClass
	 */
	public void addAllParent(String superClass) {
		if (this.allParents == null ) {
			this.allParents = new HashSet<>();
		}
		this.allParents.add(superClass);
	}
	/**
	 * Retrieve the list of all parents, this list must include 
	 * all elements of {@link #getParents()}
	 * @return
	 */
	public Collection<String> getAllParents() {
		return allParents;
	}
	/**
	 * Setter for the list of all parents
	 * @param parent
	 */
	public void setAllParents(Collection<String> parent) {
		this.allParents = parent;
	}
	/**
	 * Helper class to add a single element to the {@link #allChildren} list. 
	 * 
	 * @param childClass
	 */
	public void addAllChild(String childClass) {
		if (this.allChildren == null ) {
			this.allChildren = new HashSet<>();
		}
		this.allChildren.add(childClass);
	}
	/**
	 * Retrieve a list of all sub classes, this list must include
	 * all elements of {@link #getChildren()}
	 * @param childClass
	 */	
	public Collection<String> getAllChildren() {
		return allChildren;
	}
	/**
	 * Setter for {@link #allChildren}
	 * @param child
	 */
	public void setAllChildren(Collection<String> child) {
		this.allChildren = child;
	}
	/**
	 * Retrieve the hierarchy level (if set)
	 * @return
	 */
	public Integer getLevel() {
		return level;
	}
	/**
	 * Set the hierarchy level
	 * @param level
	 */
	public void setLevel(Integer level) {
		this.level = level;
	}

}
