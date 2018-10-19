package eu.nimble.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nimble.utility.config.PersistenceConfig;
import org.hibernate.Hibernate;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HibernateUtility {

	public static org.slf4j.Logger log = LoggerFactory.getLogger(HibernateUtility.class);
	private EntityManagerFactory entityManagerFactory;

	private static HashMap<String, HibernateUtility> engineInstances = new HashMap<>();
	private static org.h2.tools.Server server = null;

	public static void startH2DB() {
		try {
			server = org.h2.tools.Server.createTcpServer(
				new String[]{"-tcpAllowOthers"}).start();
		} catch (Exception ex1) {
			ex1.printStackTrace();
		}
	}

	public static void stopH2DB() {
		try {
			server.stop();
		} catch (Exception ex1) {
			ex1.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private HibernateUtility(String persistenceUnitName) {
		this(persistenceUnitName, null);
	}

	public HibernateUtility(String persistenceUnitName, Map persistenceProperties) {
		if(persistenceProperties == null) {
			persistenceProperties = PersistenceConfig.getInstance().getPersistenceParameters(persistenceUnitName);
		}

		entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName, persistenceProperties);
		log.info(" $$$ HibernateUtility is initialized for: {}", persistenceUnitName);
	}

	public static HibernateUtility getInstance() {
		return getInstance(Configuration.UBL_PERSISTENCE_UNIT_NAME);
	}

	public static HibernateUtility getInstance(String persistenceUnitName) {
		if (engineInstances.get(persistenceUnitName) == null) {
			HibernateUtility engineInstance = new HibernateUtility(persistenceUnitName);
			engineInstances.put(persistenceUnitName, engineInstance);
		}

		return engineInstances.get(persistenceUnitName);
	}

	public static HibernateUtility getInstance(String persistenceUnitName, Map<String, String> persistenceProperties) {
		if (engineInstances.get(persistenceUnitName) == null) {
			HibernateUtility engineInstance = new HibernateUtility(persistenceUnitName, persistenceProperties);
			engineInstances.put(persistenceUnitName, engineInstance);
		}

		return engineInstances.get(persistenceUnitName);
	}

	public void persist(Object object) {
		log.debug("Will persist object: {}", object.getClass());
		synchronized (HibernateUtility.class) {
			log.debug("Persisting object: {}", object.getClass());
			EntityManager saveManager = entityManagerFactory
				.createEntityManager();
			saveManager.getTransaction().begin();
			saveManager.persist(object);
			saveManager.getTransaction().commit();
			saveManager.close();
		}
		log.debug("Persisted object: {}", object.getClass());
	}

	public Object update(Object object) {
		log.debug("Will update object: {}", object.getClass());
		Object result;
		synchronized (HibernateUtility.class) {
			log.debug("Updating object: {}", object.getClass());
			EntityManager saveManager = entityManagerFactory
				.createEntityManager();
			saveManager.getTransaction().begin();
			result = saveManager.merge(object);
			saveManager.getTransaction().commit();
			saveManager.close();
		}
		log.debug("Updated object: {}", object.getClass());
		return result;
	}

	public boolean delete(Class<?> c, Long hjid) {
		log.debug("Will delete object: {}, hjid: {}", c.getClass(), hjid);
		boolean deleted = false;
		synchronized (HibernateUtility.class) {
			log.debug("Deleting object: {}, hjid: {}", c.getClass(), hjid);
			EntityManager saveManager = entityManagerFactory
				.createEntityManager();
			saveManager.getTransaction().begin();

			List<?> result = new ArrayList<Object>();
			String query = "select c from " + c.getSimpleName()
				+ " as c where c.hjid=" + hjid.longValue() + "";

			result = saveManager.createQuery(query).getResultList();

			if (result != null && !result.isEmpty()) {
				Object object = result.get(0);
				saveManager.remove(object);
				deleted = true;
			}

			saveManager.getTransaction().commit();
			saveManager.close();
		}
		log.debug("Deleted object: {}, hjid: {}", c.getClass(), hjid);
		return deleted;
	}

	public void delete(Object o) {
		log.debug("Will delete object: {}", o.getClass());
		synchronized (HibernateUtility.class) {
			log.debug("Deleting object: {}", o.getClass());
			boolean deleted = false;
			EntityManager saveManager = entityManagerFactory
					.createEntityManager();
			saveManager.getTransaction().begin();
			o = saveManager.merge(o);
            saveManager.remove(o);
			saveManager.getTransaction().commit();
			saveManager.close();
		}
		log.debug("Deleted object: {}", o.getClass());
	}

	// Queries according to hibernate id...
	public Object load(Class<?> classToLoad, Long hid) {
		log.debug("Will load object: {}, hjid: {}", classToLoad.getClass(), hid);
		Object result;
		synchronized (HibernateUtility.class) {
			log.debug("Loading object: {}, hjid: {}", classToLoad.getClass(), hid);
			EntityManager loadManager = entityManagerFactory
				.createEntityManager();

			result = loadManager.find(classToLoad, hid);
			loadManager.close();
		}
		log.debug("Loaded object: {}, hjid: {}", classToLoad.getClass(), hid);
		return result;
	}

	public <T> T load(String queryStr, Object... parameters) {
		log.debug("Loading objects. query: {}", queryStr);
		EntityManager loadManager = entityManagerFactory
				.createEntityManager();
		loadManager.getTransaction().begin();

		Query query = loadManager.createQuery(queryStr);
		for(int i=0; i<parameters.length; i++) {
			query.setParameter(i+1, parameters[i]);
		}

		T result = null;
		try {
			result = (T) query.getSingleResult();
		} catch (NoResultException e) {
			// do nothing
		}

		loadManager.getTransaction().commit();
		loadManager.close();
		log.debug("Loaded objects. query: {}", queryStr);
		return result;
	}

	public List<?> loadAll(Class<?> classToLoad) {
		log.debug("Will load objects. Class: {}", classToLoad.getClass());
		List<?> result;
		synchronized (HibernateUtility.class) {
			log.debug("Loading objects. Class: {}", classToLoad.getClass());
			String query = "select c from " + classToLoad.getName() + " as c";
			EntityManager loadManager = entityManagerFactory
				.createEntityManager();

			loadManager.getTransaction().begin();

			result = loadManager.createQuery(query).getResultList();
			loadManager.getTransaction().commit();
			loadManager.close();
		}
		log.debug("Loaded objects. Class: {}", classToLoad.getClass());
		return result;
	}

	public List<?> loadAll(String query) {
		log.debug("Will load objects. query: {}", query);
		List<?> result;
		synchronized (HibernateUtility.class) {
			log.debug("Loading objects. query: {}", query);
			EntityManager loadManager = entityManagerFactory
				.createEntityManager();
			loadManager.getTransaction().begin();

			result = loadManager.createQuery(query).getResultList();
			Hibernate.initialize(result);
			loadManager.getTransaction().commit();
			loadManager.close();
		}
		log.debug("Loaded objects. query: {}", query);
		return result;
	}

	public List<?> loadAll(String queryStr, Object... parameters) {
		log.debug("Loading objects. query: {}", queryStr);
		EntityManager loadManager = entityManagerFactory
				.createEntityManager();
		loadManager.getTransaction().begin();

		List<?> result;
		Query query = loadManager.createQuery(queryStr);
		for(int i=0; i<parameters.length; i++) {
			query.setParameter(i+1, parameters[i]);
		}

		result = query.getResultList();
		Hibernate.initialize(result);
		loadManager.getTransaction().commit();
		loadManager.close();
		log.debug("Loaded objects. query: {}", queryStr);
		return result;
	}

	public List<?> loadAll(String query, int offset, int limit) {
		log.debug("Will load objects. query: {}, offset: {}, limit: {}", query, offset, limit);
		List<?> result;
		synchronized (HibernateUtility.class) {
			log.debug("Loading objects. query: {}, offset: {}, limit: {}", query, offset, limit);
			EntityManager loadManager = entityManagerFactory
					.createEntityManager();
			loadManager.getTransaction().begin();

			Query queryObj = loadManager.createQuery(query);
			queryObj.setFirstResult(offset);
			queryObj.setMaxResults(limit);

			result = queryObj.getResultList();
			Hibernate.initialize(result);
			loadManager.getTransaction().commit();
			loadManager.close();
		}
		log.debug("Loaded objects. query: {}, offset: {}, limit: {}", query, offset, limit);
		return result;
	}

	public Object loadIndividualItem(String query) {
		log.debug("Loading individual item. query: {}", query);
		List<?> result;
		EntityManager loadManager = entityManagerFactory.createEntityManager();
		loadManager.getTransaction().begin();

		result = loadManager.createQuery(query).getResultList();

		loadManager.getTransaction().commit();
		loadManager.close();

		log.debug("Loaded individual item. query: {}", query);
		if (result == null || result.size() == 0) {
			return null;
		}
		return result.get(0);
	}

	public void executeUpdate(String query) {
		log.debug("Performing update. query: {}", query);
		EntityManager loadManager = entityManagerFactory.createEntityManager();
		loadManager.getTransaction().begin();

		loadManager.createQuery(query).executeUpdate();

		loadManager.getTransaction().commit();
		loadManager.close();
		log.debug("Performed update. query: {}", query);
	}

	public static Object copySerializableObject(Object object, Class clazz) {
		ObjectMapper om = new ObjectMapper();
		String serializedObject;
		try {
			serializedObject = om.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to serialize the object for class: " + clazz.getName(), e);
		}

		Object copy;
		try {
			copy = om.readValue(serializedObject, clazz);
		} catch (IOException e) {
			throw new RuntimeException("Failed to deserizalize the object for class: " + clazz.getName() + "\nSerialization: " + serializedObject, e);
		}

		return  copy;
	}

	public static void main(String argv[]) {
		try {
			 org.h2.tools.Server server = org.h2.tools.Server.createTcpServer(
				new String[]{"-tcpAllowOthers"}).start();
		} catch (Exception ex1) {
			ex1.printStackTrace();
		}
	}

	private String specifyPersistencePropertiesFileName(String persistenceUnitName) {
		if(persistenceUnitName.equals(Configuration.UBL_PERSISTENCE_UNIT_NAME))
			return Configuration.UBL_PERSISTENCE_PROPERTIES_FILE_NAME;
		else if(persistenceUnitName.equals(Configuration.MODAML_PERSISTENCE_UNIT_NAME))
			return Configuration.MODAML_PERSISTENCE_PROPERTIES_FILE_NAME;
		else return "persistence.properties";
	}
}
