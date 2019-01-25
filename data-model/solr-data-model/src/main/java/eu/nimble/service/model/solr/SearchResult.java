package eu.nimble.service.model.solr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

public class SearchResult<T> {
	private long totalElements;
	private long totalPages;
	private int pageSize;
	private int currentPage;
	private List<T> result;
	
	private Map<String, FacetResult> facets;
	
	public SearchResult(List<T> result) {
		this.result = result;
	}
	public SearchResult(List<T> result, int currentPage, int pageSize, long totalElements, long totalPages) {
		this.result = result;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}
	public SearchResult(Page<T> page) {
		this.result = page.getContent();
		this.currentPage = page.getNumber();
		this.pageSize = page.getSize();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}
	public Map<String, FacetResult> getFacets() {
		return facets;
	}

	public void setFacets(Map<String, FacetResult> facets) {
		this.facets = facets;
	}
	public void addFacet(String field, String label, long count) {
		if ( getFacets() == null) {
			facets = new HashMap<String, FacetResult>();
		}
		
		FacetResult set = facets.get(field);
		if ( set == null ) {
			set = new FacetResult(field);
			facets.put(field,set);
		}
		set.addEntry(label, count);
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
