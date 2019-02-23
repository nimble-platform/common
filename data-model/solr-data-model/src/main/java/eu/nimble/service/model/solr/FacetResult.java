package eu.nimble.service.model.solr;

import java.util.Set;
import java.util.TreeSet;

import org.apache.jena.ext.com.google.common.base.Strings;

public class FacetResult extends IndexField {

	public FacetResult(String name) {
		super(name);
	}
	
	private Set<Entry> entry;
	
	public class Entry implements Comparable<Entry> {
		final String label;
		final long count;
		public Entry(String l, long c) {
			this.label = l;
			this.count = c;
		}
		public String getLabel() {
			return label;
		}
		public long getCount() {
			return count;
		}
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

	public Set<Entry> getEntry() {
		return entry;
	}

	public void setEntry(Set<Entry> entry) {
		this.entry = entry;
	}
	
	public void addEntry(String label, long count) {
		if ( entry == null ) {
			entry = new TreeSet<>();
//			entry = new HashSet<>();
		}
		if (!Strings.isNullOrEmpty(label)) {
			entry.add(new Entry(label,count));
		}
	}
}
