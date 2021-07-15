package eu.nimble.service.model.solr.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.solr.core.mapping.SolrDocument;

import eu.nimble.service.model.solr.owl.PropertyType;
/**
 * Interface providing the functionality for indexing arbitrary dynamic properties. A
 * dynamic property is further qualified by key parts.
 * 
 * <p>
 * Implementors inherit default functionality for handling dynamic properties. They may
 * then add custom properties with 
 * <pre>
 * addProperty(&lt;value&gt;, &lt;qualifier 1&gt;, ..., &lt;qualifier n&gt;)
 * </pre>
 * where the provided qualifiers are used to create the dynamic key part for the value. 
 * The kvalue is indexed depending on its type in the following fields:
 * <ul>
 * <li>String {@link #CUSTOM_STRING_PROPERTY}
 * <li>Integer {@link #CUSTOM_INTEGER_PROPERTY}
 * <li>Double {@link #CUSTOM_DOUBLE_PROPERTY}
 * <li>Boolean {@link #CUSTOM_BOOLEAN_PROPERTY}
 * </ul>
 * with the <code>key</code> as dynamic filed part. 
 * 
 * The qualifiers are concatenated with @ as delimiter and stored in {@link #CUSTOM_KEY_FIELD}.
 * The original qualifiers are also indexed in the {@link #CUSTOM_KEY_FIELD} for later retrieval:
 * <pre>
 * get&lt;type&gt;PropertyValues(&lt;qualifier 1&gt;, ..., &lt;qualifier n&gt;)
 * </pre>
 * will return again create the dynamic key an access the indexed values!
 * </p>
 * <p>
 * A more concrete 
 * example for indexing 
 * <pre>
 * addProperty(3.14, "Rotation", "Max")
 * </pre>
 * will first create the dynamic <code>key</code> (see {@link DynamicName#getDynamicFieldPart(String...)} for details) which 
 * results in an eligible index field name part <code>rotation_max</code>. Since the provided value is a {@link Double} value, the resulting index field name will be
 * <code>rotation_max_ds</code>.
 * <br/>
 * For Retrieval, appropriate methods distinguish the type to return:
 * <pre>
 * getDoublePropertyValues("Rotation", "Max")
 * </pre>
 * will provide the stored values from the index field named <code>rotation_max_ds</code>.
 * </p>
 * @author dglachs
 *
 */
public interface ICustomPropertyAware {
	String getUri();
	/**
	 * Custom Property Key's including their label used
	 */
	String CUSTOM_KEY_FIELD = "*_key";
	/**
	 * The custom string properties field
	 */
	String CUSTOM_STRING_PROPERTY = "*_ss";
	/**
	 * The custom double properties field
	 */
	String CUSTOM_DOUBLE_PROPERTY = "*_ds";
	/**
	 * The custom integer properties field
	 */
	String CUSTOM_INTEGER_PROPERTY = "*_is";
	/**
	 * The custom boolean properties field
	 */
	String CUSTOM_BOOLEAN_PROPERTY = "*_b";
	/**
	 * Helper method to extract the collection
	 * @return
	 */
	default String getCollection() {
		SolrDocument solrDocument = getClass().getAnnotation(SolrDocument.class);
		
		if ( solrDocument != null && solrDocument.collection() != null) {
			return solrDocument.collection();
		}
		throw new IllegalStateException("No @SolrDocument annotation found ...");
	}

	/**
	 * Setter for a customized numeric value
	 * @param value The double value
	 * @param qualifier The dynamic name parts
	 */
	default void addProperty(Double value, String ... qualifier) {
		addProperty(value, null, qualifier);

	}
	default void addProperty(Double value, PropertyType customMeta, String ... qualifier) {
		String key = DynamicName.getDynamicFieldPart(qualifier);
		// use @ as delimiter and store the original qualifier
		getCustomPropertyKeys().put(key, String.join("@", qualifier));
		// store the value
		Collection<Double> values = getCustomDoubleValues().get(key);
		if ( values == null ) {
			// use a set to avoid duplicates
			getCustomDoubleValues().put(key, new HashSet<Double>());
		}
		// now add the value to the double map
		getCustomDoubleValues().get(key).add(value);
		if ( customMeta !=null ) {
			// handle custom property metadata
			getCustomProperties().put(key, customMeta);
		}
	}
	/**
	 * Add a integer value to the index. The value is stored in the {@link #CUSTOM_INTEGER_PROPERTY}
	 * field where the dynamic part of the is constructed on behalf of the qualifier.   
	 * @param value The integer value
	 * @param qualifier The dynamic field parts
	 * @see DynamicName#getDynamicFieldPart(String...)
	 */
	default void addProperty(Integer value, String ... qualifier) {
		addProperty(value, null, qualifier);
	}
	/**
	 * Add a integer value to the index. The value is stored in the {@link #CUSTOM_INTEGER_PROPERTY}
	 * field where the dynamic part of the is constructed on behalf of the qualifier.   
	 * @param value The integer value to be added to the index
	 * @param customMeta The custom property definition of the integer value
	 * @param qualifier The dynamic field parts
	 */
	default void addProperty(Integer value, PropertyType customMeta, String ... qualifier) {
		String key = DynamicName.getDynamicFieldPart(qualifier);
		// use @ as delimiter and store the original qualifier
		getCustomPropertyKeys().put(key, String.join("@", qualifier));
		// store the value
		Collection<Integer> values = getCustomIntValues().get(key);
		if ( values == null ) {
			// use a set to avoid duplicates
			getCustomIntValues().put(key, new HashSet<Integer>());
		}
		// now add the value
		getCustomIntValues().get(key).add(value);
		if ( customMeta !=null ) {
			// handle custom property metadata
			getCustomProperties().put(key, customMeta);
		}
	}
	/**
	 * add a property (value)
	 * @param value The value
	 * @param qualifier The qualifier
	 */
	default void addProperty(String value, String ... qualifier) {
		addProperty(value, (PropertyType)null, qualifier);
	}
	default void setProperties(Collection<?> values, PropertyType customMeta, String...qualifier) {
		// obtain the key
		String key = DynamicName.getDynamicFieldPart(qualifier);
		// use @ as delimiter and store the original qualifier
		getCustomPropertyKeys().put(key, String.join("@", qualifier));
		// use the first element for type detection
		Optional<?> first = values.stream().findFirst();
		if ( first.isPresent() ) {
			if ( first.get() instanceof String ) {
				Collection<String> strVal = values.stream().map(new Function<Object, String>() {

					@Override
					public String apply(Object t) {
						return t.toString();
					}
				})
				.collect(Collectors.toList());
				getCustomStringValues().put(key, strVal);
			}
			if ( first.get() instanceof Integer) {
				Collection<Integer> intVal = values.stream().map(new Function<Object, Integer>() {

					@Override
					public Integer apply(Object t) {
						return (Integer)t;
					}
				})
				.collect(Collectors.toList());
				getCustomIntValues().put(key, intVal);
			}
			if ( first.get() instanceof Double) {
				Collection<Double> doubleVal = values.stream().map(new Function<Object, Double>() {

					@Override
					public Double apply(Object t) {
						return (Double)t;
					}
				})
				.collect(Collectors.toList());
				getCustomDoubleValues().put(key, doubleVal);
			}
			if ( first.get() instanceof Boolean) {
				getCustomBooleanValue().put(key, (Boolean)first.get());
			}
			// maintain the custom property
			if ( customMeta !=null ) {
				// handle custom property metadata
				getCustomProperties().put(key, customMeta);
			}
		}

	
	}
	/**
	 * Setter Method for for a dynamic property
	 * @param values A collection holding values of either String, Integer, Double or Boolean values
	 * @param qualifier
	 */
	default void setProperties(Collection<?> values, String ...qualifier) {
		setProperties(values, null, qualifier);
	}
	/**
	 * Obtain the value of the qualified property of the requested type (assuming there 
	 * is only one value stored, so the first value found is returned)
	 * @param <T> String, Integer, Double or Boolean
	 * @param clazz The type class
	 * @param qualifier The qualifier used during indexing
	 * @return The first value when present, {@link Optional#empty()} otherwise.
	 */
	default <T> Optional<T> getProperty(Class<T> clazz, String ...qualifier ) {
		List<T> coll = getProperties(clazz, qualifier);
		if (! coll.isEmpty()) {
			return Optional.of(coll.get(0));
		}
		return Optional.empty();
	}
	/**
	 * Retrieve the (typesafe) collection of stored/indexed values. 
	 * @param <T>
	 * @param clazz The targeted class of the values (String, Double, Integer, Boolean)
	 * @param qualifier The qualifier used during indexing
	 * @return The collection of values, will not return <code>null</code>
	 */
	default <T> List<T> getProperties(Class<T> clazz, String ...qualifier) {
		String key = DynamicName.getDynamicFieldPart(qualifier);
		
		if ( Integer.class.equals(clazz)) {
			Collection<Integer> is = getCustomIntValues().get(key);
			if ( is != null && ! is.isEmpty()) {
				return is.stream()
						.map(new Function<Integer, T>() {
							public T apply(Integer i) {
								return clazz.cast(i);
							}
							
						})
						.collect(Collectors.toList());
			}
		}
		else if ( Double.class.equals(clazz)) {
			Collection<Double> ds = getCustomDoubleValues().get(key);
			if ( ds != null && ! ds.isEmpty()) {
				return ds.stream()
						.map(new Function<Double, T>() {
							public T apply(Double d) {
								return clazz.cast(d);
							}
							
						})
						.collect(Collectors.toList());
			}
		}
		else if ( Boolean.class.equals(clazz)) {
			Boolean b = getCustomBooleanValue().get(key);
			if ( b != null) {
				return Collections.singletonList(clazz.cast(b));
			}
		}
		else {
			Collection<String> ss = getCustomStringValues().get(key);
			if ( ss != null && ! ss.isEmpty()) {
				return ss.stream()
						.map(new Function<String, T>() {
							public T apply(String d) {
								return clazz.cast(d);
							}
							
						})
						.collect(Collectors.toList());
			}
		}
		return new ArrayList<>();
	}
	/**
	 * Add a text property to the index
	 * @param value the text value
	 * @param customMeta The property metadata or null
	 * @param qualifier the qualifier for storing in the index
	 */
	default void addProperty(String value, PropertyType customMeta, String ... qualifier) {
		String key = DynamicName.getDynamicFieldPart(qualifier);
		// use @ as delimiter and store the original qualifier
		getCustomPropertyKeys().put(key, String.join("@", qualifier));
		// store the value
		Collection<String> values = getCustomStringValues().get(key);
		if ( values == null ) {
			// use a set to avoid duplicates
			getCustomStringValues().put(key, new HashSet<String>());
		}
		// now add the value to the double map
		getCustomStringValues().get(key).add(value);
		if ( customMeta !=null ) {
			// handle custom property metadata
			getCustomProperties().put(key, customMeta);
		}
		
	}
	/**
	 * Set a single custom string property
	 * @param value
	 * @param qualifier
	 */
	default void setProperty(String value, String ...qualifier ) {
		setProperties(Collections.singletonList(value), null, qualifier);
	}
	/**
	 * Set a single custom string property
	 * @param value
	 * @param meta
	 * @param qualifier
	 */
	default void setProperty(String value, PropertyType meta, String ...qualifier ) {
		setProperties(Collections.singletonList(value), meta, qualifier);
	}
	/**
	 * Set a single custom integer property
	 * @param value
	 * @param qualifier
	 */
	default void setProperty(Integer value, String ...qualifier ) {
		setProperties(Collections.singletonList(value), null, qualifier);
	}
	/**
	 * Set a single custom integer property
	 * @param value
	 * @param qualifier
	 */
	default void setProperty(Integer value, PropertyType meta, String ...qualifier ) {
		setProperties(Collections.singletonList(value), meta, qualifier);
	}
	/**
	 * Set a single custom double property
	 * @param value
	 * @param qualifier
	 */
	default void setProperty(Double value, String ...qualifier ) {
		setProperties(Collections.singletonList(value), null, qualifier);
	}
	/**
	 * Set a single custom double property
	 * @param value
	 * @param qualifier
	 */
	default void setProperty(Double value, PropertyType meta, String ...qualifier ) {
		setProperties(Collections.singletonList(value), meta, qualifier);
	}
	/**
	 * Set a single custom boolean property
	 * @param value
	 * @param qualifier
	 */
	default void setProperty(Boolean value, String ...qualifier ) {
		setProperties(Collections.singletonList(value), null, qualifier);
	}
	/**
	 * Set a single custom boolean property
	 * @param value
	 * @param qualifier
	 */
	default void setProperty(Boolean value, PropertyType meta, String ...qualifier ) {
		setProperties(Collections.singletonList(value), meta, qualifier);
	}
	
//	/**
//	 * Retrieve the collection of values - may be <code>null</code>
//	 * @param qualifier
//	 * @return
//	 */
//	default Collection<String> getStringPropertyValues(String ... qualifier) {
//		String key = DynamicName.getDynamicFieldPart(qualifier);
//		return getCustomStringValues().get(key);
//		
//	}
//	/**
//	 * Setter for the string property values
//	 * @param values
//	 * @param qualifier The dynamic field part
//	 */
//	default void setStringPropertyValues(Collection<String> values, String ...qualifier ) {
//		String key = DynamicName.getDynamicFieldPart(qualifier);
//		// use @ as delimiter and store the original qualifier
//		getCustomPropertyKeys().put(key, String.join("@", qualifier));
//		// store the collection
//		getCustomStringValues().put(key, values);
//	}
//	default Optional<String> getStringPropertyValue(String ...qualifier) {
//		Collection<String> v = getStringPropertyValues(qualifier);
//		if ( v!=null ) {
//			return v.stream().findFirst();
//		}
//		return Optional.empty();
//	}
//	default Collection<Double> getDoublePropertyValues(String ... qualifier) {
//		String key = DynamicName.getDynamicFieldPart(qualifier);
//		return getCustomDoubleValues().get(key);
//	}
//	/**
//	 * Retrieve the integer values for the provided qualifiers
//	 * @param qualifier The dynamic name parts
//	 * @return
//	 */
//	default Collection<Integer> getIntPropertyValues(String ... qualifier) {
//		String key = DynamicName.getDynamicFieldPart(qualifier);
//		return getCustomIntValues().get(key);
//	}
	
	/**
	 * Getter for the custom Integer values. Must not return <code>null</code>
	 * @return
	 */
	public Map<String, Collection<Integer>> getCustomIntValues();
	/**
	 * Getter for the custom double values. Must not return <code>null</code>
	 * @return
	 */
	public Map<String, Collection<Double>> getCustomDoubleValues();
	/**
	 * Getter for the custom string values. Must not return null;
	 * @return
	 */
	public Map<String, Collection<String>> getCustomStringValues();
	/**
	 * Getter for the custom boolean values, must not return <code>null</code>
	 * @return
	 */
	public Map<String, Boolean> getCustomBooleanValue();
	/**
	 * Getter for the custom property key map holding the original qualifier
	 * and the mapped index field name part.
	 * @return
	 */
	public Map<String,String> getCustomPropertyKeys();
	/**
	 * Getter for the map holding the meta data for the custom 
	 * property.
	 * @return The custom property map, must not return <code>null</code>.
	 */
	@ReadOnlyProperty
	public Map<String, PropertyType> getCustomProperties();
	
}
