package eu.nimble.service.model.solr.owl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.query.Join;
import org.springframework.data.solr.core.query.SimpleField;




@SolrDocument(collection="class")
public class ClassType extends Concept implements IClassType {
	public enum JOIN_TO {
		property(IPropertyType.ID_FIELD, IClassType.PROPERTIES_FIELD, IPropertyType.COLLECTION, "property", "prop"),
		;
		
		String from;
		String to;
		String fromIndex;
		String[] names;
		
		JOIN_TO(String from, String to, String fromIndex, String ... names) {
			this.from = from;
			this.to = to;
			this.fromIndex = fromIndex;
			this.names = names;
		}
		public static Join getJoin(String name) {
			for ( JOIN_TO j : values()) {
				if ( j.names != null ) {
					for (String s : j.names) {
						if ( s.equalsIgnoreCase(name)) {
							return j.getJoin();
						}
					}
				}
			}
			// not found - try the enum name
			try {
				// check for ItemType JOINS
				JOIN_TO join = JOIN_TO.valueOf(name.toLowerCase());
				// 
				return join.getJoin();
			} catch (Exception e) {
				// invalid join
				return null;
			}
		}		public Join getJoin() {
			return new Join(new SimpleField(from), new SimpleField(to), fromIndex);
		}

	}
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
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Collection<String> getProperties() {
		return properties;
	}
	public void addProperty(String property) {
		if (this.properties == null ) {
			this.properties = new HashSet<>();
		}
		this.properties.add(property);
	}
	public void setProperties(Collection<String> properties) {
		this.properties = properties;
	}
	public void addParent(String superClass) {
		if (this.parents == null ) {
			this.parents = new HashSet<>();
		}
		this.parents.add(superClass);
	}
	public Collection<String> getParents() {
		return parents;
	}
	public void setParents(Collection<String> parent) {
		this.parents = parent;
	}
	public void addChild(String childClass) {
		if (this.children == null ) {
			this.children = new HashSet<>();
		}
		this.children.add(childClass);
	}
	public Collection<String> getChildren() {
		return children;
	}
	public void setChildren(Collection<String> child) {
		this.children = child;
	}
	public void addAllParent(String superClass) {
		if (this.allParents == null ) {
			this.allParents = new HashSet<>();
		}
		this.allParents.add(superClass);
	}
	public Collection<String> getAllParents() {
		return allParents;
	}
	public void setAllParents(Collection<String> parent) {
		this.allParents = parent;
	}
	public void addAllChild(String childClass) {
		if (this.allChildren == null ) {
			this.allChildren = new HashSet<>();
		}
		this.allChildren.add(childClass);
	}
	public Collection<String> getAllChildren() {
		return allChildren;
	}
	public void setAllChildren(Collection<String> child) {
		this.allChildren = child;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}

}
