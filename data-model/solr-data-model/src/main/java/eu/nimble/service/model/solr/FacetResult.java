package eu.nimble.service.model.solr;

import java.util.Set;
import java.util.TreeSet;

import org.apache.jena.ext.com.google.common.base.Strings;
/**
 * Helper object outlining the faceting entries 
 * of a single facet field. 
 * @author dglachs
 *
 */
public class FacetResult extends IndexField {

	public FacetResult(String name) {
		super(name);
	}
	
	private Set<Entry> entry;
	/**
	 * Object representing a single facet entry with label and count
	 * 
	 * @author dglachs
	 *
	 */
	public class Entry implements Comparable<Entry> {
		final String label;
		final long count;
		public Entry(String l, long c) {
			this.label = l;
			this.count = c;
		}
		/**
		 * Obtain the label's entry
		 * @return
		 */
		public String getLabel() {
			return label;
		}
		/**
		 * Obtain the number of occurrences
		 * @return
		 */
		public long getCount() {
			return count;
		}
		/**
		 * ensure sorting based on occurrences descending
		 */
		@Override
		public int compareTo(Entry o) {
			if (o.count == this.count ) {
				return this.label.compareTo(o.label);
			}
			else if ( this.count >  o.count) {
				return -1;
			}
			else {
				return 1;
			}
		}
	}
	/**
	 * Getter of the set of entries
	 * @return
	 */
	public Set<Entry> getEntry() {
		return entry;
	}
	/**
	 * Setter of the set of entries
	 * @param entry
	 */
	public void setEntry(Set<Entry> entry) {
		this.entry = entry;
	}
	/**
	 * Convenience method for adding entries
	 * @param label
	 * @param count
	 */
	public void addEntry(String label, long count) {
		if ( entry == null ) {
			entry = new TreeSet<>();
		}
		if (!Strings.isNullOrEmpty(label)) {
			entry.add(new Entry(label,count));
		}
	}
}
