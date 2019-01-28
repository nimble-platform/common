package eu.nimble.service.model.solr;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.Field;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;

public class SearchResult<T> {
	private long totalElements;
	private long totalPages;
	private int pageSize;
	private int currentPage;
	private List<T> result;
	
	private Map<String, FacetResult> facets;
	
	public SearchResult(List<T> result) {
		this.result = result;
		this.currentPage = 0;
		this.pageSize = result.size();
		this.totalElements = result.size();
		this.totalPages =1;
	}
	@Deprecated
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
		if ( page instanceof FacetPage<?>) {
			// 
			FacetPage<T> facetPage = (FacetPage<T>)page;
			handleFacets(facetPage);
		}
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
	private void handleFacets(FacetPage<T> facetPage) {
		for (Field field :  facetPage.getFacetFields()) {
			Page<FacetFieldEntry> page = facetPage.getFacetResultPage(field);
			//
			for (FacetFieldEntry entry : page.getContent() ) {
				this.addFacet(entry.getField().getName(), entry.getValue(), entry.getValueCount());
			}
		}

	}
}
