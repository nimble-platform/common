package eu.nimble.service.model.solr;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

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
			entry = new TreeSet<>(NumberComparator.INSTANCE);
		}
		entry.add(new Entry(label,count));
	}
	private static class NumberComparator implements Comparator<Entry> {
		static NumberComparator INSTANCE = new NumberComparator();
		@Override
		public int compare(Entry o1, Entry o2) {
			if ( o1.getCount() == o2.getCount()) {
				return o1.getLabel().compareToIgnoreCase(o2.getLabel());
			}
			if ( o1.getCount() > o2.getCount()) {
				return 1;
			}
			else {
				return -1; 
			}
		}
	}
}
