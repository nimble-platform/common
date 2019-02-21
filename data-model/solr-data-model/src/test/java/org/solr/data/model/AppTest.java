package org.solr.data.model;

import java.util.Iterator;

import eu.nimble.service.model.solr.FacetResult.Entry;
import eu.nimble.service.model.solr.SearchResult;
import eu.nimble.service.model.solr.item.ItemType;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testItemProperties()
    {
    	ItemType item = new ItemType();
    	// add to stringValue with key "property" and "LangString"
    	item.addMultiLingualProperty("property", "en", "Property");
    	item.addMultiLingualProperty("property", "de", "Attribut");
    	item.addProperty("property2", "m", 2.0);
    	item.addProperty("property2", "cm", 200.0);
    	assertTrue(item.getStringValue().get("property").contains("Property@en"));
    	assertTrue(item.getStringValue().get("property").contains("Attribut@de"));
    	assertTrue(item.getPropertyMap().containsValue("property@m"));
    	assertTrue(item.getPropertyMap().containsValue("property@cm"));
    	
    }
    public void testSorting() {
    	SearchResult<String> result = new SearchResult<>();
    	result.addFacet("text", "XYZ", 2);
    	result.addFacet("text", "Fourth", 1);
    	result.addFacet("text", "First", 10);
    	result.addFacet("text", "ABC", 2);
    	Iterator<Entry> set = result.getFacets().get("text").getEntry().iterator();
    	Entry first = set.next();
    	assertTrue("First".equals(first.getLabel()));
    	Entry second = set.next();
    	assertTrue("ABC".equals(second.getLabel()));
    }
    
}
