package eu.nimble.service.model.solr;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.SolrPageRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(value=Include.NON_NULL)
public class Search {
	private static final int START = 0;
	private static final int ROWS = 10;
	private static final int FACET_LIMIT = 15;
	private static final int FACET_MINCOUNT = 1;
	private String query = "*:*";
	private List<String> filterQuery;
	private int rows = ROWS;
	private int start = START;
	private FacetedSearch facet;
	
	public Search() {
		// default
	}
	public Search(String query) {
		this.query = query;
	}
	public Search(String query, List<String> filter) {
		this(query);
		this.filterQuery = filter;
	}
	public Search(String query, List<String> filter, List<String> facets) {
		this(query, filter);
		this.facet = new FacetedSearch(facets);
	}
	
	public Search query(String query) {
		this.query = query;
		return this;
	}
	public Search filter(String filter) {
		if (filterQuery == null) {
			this.filterQuery = new ArrayList<>();
		}
		this.filterQuery.add(filter);
		return this;
	}
	public Search facetField(String field) {
		if ( facet == null ) {
			facet = new FacetedSearch();
		}
		facet.addFacet(field);
		return this;
	}
	public Search facetMinCount(int minCount) {
		if ( facet == null ) {
			facet = new FacetedSearch();
		}
		facet.setMinCount(minCount);
		return this;
	}
	public Search facetLimit(int limit) {
		if ( facet == null ) {
			facet = new FacetedSearch();
		}
		facet.setLimit(limit);
		return this;
	}
	
	public Search forPage(int start, int rows) {
		this.start = start;
		this.rows = rows;
		return this;
	}
	@JsonProperty(value="q")
	public String getQuery() {
		return query;
	}
	@JsonProperty(value="q")
	public void setQuery(String query) {
		this.query = query;
	}
	
	@JsonProperty(value="fq")
	public List<String> getFilterQuery() {
		return filterQuery;
	}
	@JsonProperty(value="fq")
	public void setFilterQuery(List<String> filterQuery) {
		this.filterQuery = filterQuery;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public FacetedSearch getFacet() {
		return facet;
	}
	public void setFacet(FacetedSearch facets) {
		this.facet = facets;
	}
	public class FacetedSearch {
		private List<String> field = new ArrayList<>();
		private int limit = FACET_LIMIT;
		private int minCount = FACET_MINCOUNT;
		public FacetedSearch() {
			// default
		}
		private FacetedSearch(List<String> facets) {
			this.field = facets;
		}
		private void addFacet(String facet) {
			field.add(facet);
		}
		public List<String> getField() {
			return field;
		}
		public void setField(List<String> facetField) {
			this.field = facetField;
		}
		public int getLimit() {
			return limit;
		}
		public void setLimit(int facetLimit) {
			this.limit = facetLimit;
		}
		public int getMinCount() {
			return minCount;
		}
		public void setMinCount(int facetMinCount) {
			this.minCount = facetMinCount;
		}
	}
	@JsonIgnore
	public int getFacetLimit() {
		
		if ( facet!=null) {
			return facet.getLimit();
		}
		return FACET_LIMIT;
	}
	@JsonIgnore
	public int getFacetMinCount() {
		if ( facet!=null) {
			return facet.getMinCount();
		}
		return FACET_MINCOUNT;
		
	}
	@JsonIgnore
	public List<String> getFacetFields() {
		if ( facet != null) {
			return facet.getField();
		}
		return null;
	}

	@JsonIgnore
	public Pageable getPage() {
		return new SolrPageRequest(start, rows);
	}
}
