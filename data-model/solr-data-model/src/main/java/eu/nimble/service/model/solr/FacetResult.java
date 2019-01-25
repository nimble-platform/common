package eu.nimble.service.model.solr;

import java.util.HashSet;
import java.util.Set;

public class FacetResult extends IndexField {

	public FacetResult(String name) {
		super(name);
	}
	
	private Set<Entry> entry;
	
	public class Entry {
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
	}

	public Set<Entry> getEntry() {
		return entry;
	}

	public void setEntry(Set<Entry> entry) {
		this.entry = entry;
	}
	
	public void addEntry(String label, long count) {
		if ( entry == null ) {
			entry = new HashSet<>();
		}
		entry.add(new Entry(label,count));
	}
	

}
