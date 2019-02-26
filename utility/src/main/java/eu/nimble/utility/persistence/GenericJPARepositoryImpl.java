package eu.nimble.utility.persistence;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by suat on 27-Nov-18.
 */
public class GenericJPARepositoryImpl implements GenericJPARepository, ApplicationContextAware {

    private static final String QUERY_SELECT_ALL_BY_CLASS = "SELECT e FROM %s e";

    private static ApplicationContext applicationContext;

    // Entity manager factory required to be used in cases where a clean EntityManager is required
    protected EntityManagerFactory emf;

    public EntityManagerFactory getEmf() {
        return emf;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public <T> T getSingleEntityByHjid(Class<T> klass, long hjid) {
        EntityManager em = emf.createEntityManager();
        T entity = null;
        try {
            em.getTransaction().begin();
            entity = em.find(klass, hjid);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to get single entity result by hjid",e);
        }finally {
            em.close();
        }
        return entity;
    }

    public <T> T getSingleEntity(String queryStr, String[] parameterNames, Object[] parameterValues) {
        EntityManager em = emf.createEntityManager();

        T result = null;
        try {
            em.getTransaction().begin();
            Query query = createQuery(queryStr, parameterNames, parameterValues, em);
            result = (T) query.getSingleResult();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to get single entity result",e);
        } finally {
            em.close();
        }
        return result;
    }

    @Override
    public <T> List<T> getEntities(String query) {
        return getEntities(query, null, null);
    }

    @Override
    public <T> List<T> getEntities(String queryStr, String[] parameterNames, Object[] parameterValues) {
        return getEntities(queryStr, parameterNames, parameterValues, null, null);
    }

    @Override
    public <T> List<T> getEntities(String queryStr, String[] parameterNames, Object[] parameterValues, Integer limit, Integer offset) {
        return getEntities(queryStr, parameterNames, parameterValues, limit, offset,false);
    }

    @Override
    public <T> List<T> getEntities(String queryStr, String[] parameterNames, Object[] parameterValues, Integer limit, Integer offset, boolean isNative) {
        EntityManager em = emf.createEntityManager();

        List<T> result = null;
        try {
            em.getTransaction().begin();
            Query query = createQuery(queryStr, parameterNames, parameterValues, em,isNative);

            if (limit != null && offset != null) {
                query.setFirstResult(offset);
                query.setMaxResults(limit);
            }

            result = query.getResultList();
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to get entities",e);
        }finally {
            em.close();
        }
        return result;
    }

    @Override
    public <T> List<T> getEntities(Class<T> klass) {
        EntityManager em = emf.createEntityManager();

        List<T> results = null;
        try {
            em.getTransaction().begin();
            results = em.createQuery(String.format(QUERY_SELECT_ALL_BY_CLASS, klass.getName())).getResultList();
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to get entities",e);
        }finally {
            em.close();
        }
        return results;
    }

    @Override
    public <T> T updateEntity(T entity) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            entity = em.merge(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to update the entity",e);
        }finally {
            em.close();
        }
        return entity;
    }

    @Override
    public <T> void deleteEntity(T entity) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            if (!em.contains(entity)) {
                entity = em.merge(entity);
            }
            em.remove(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to delete the entity",e);
        }finally {
            em.close();
        }
    }

    @Override
    public <T> void deleteEntityByHjid(Class<T> klass, long hjid) {
        T entity = getSingleEntityByHjid(klass, hjid);
        if (entity != null) {
            deleteEntity(entity);
        }
    }

    @Override
    public <T> void persistEntity(T entity) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to persist the entity",e);
        }finally {
            em.close();
        }
    }

    @Override
    public <T> void persistEntities(Iterable<T> entities) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            for (T entity : entities) {
                em.persist(entity);
            }
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to persist entities",e);
        }finally {
            em.close();
        }
    }

    @Override
    public void executeUpdate(String query, String[] parameterNames, Object[] parameterValues) {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            Query queryObj = createQuery(query, parameterNames, parameterValues, em);
            queryObj.executeUpdate();
            em.getTransaction().commit();
        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to execute update query",e);
        }finally {
            em.close();
        }
    }

    private Query createQuery(String queryStr, String[] parameterNames, Object[] parameterValues, EntityManager em) {
        return createQuery(queryStr,parameterNames,parameterValues,em,false);
    }
    private Query createQuery(String queryStr, String[] parameterNames, Object[] parameterValues, EntityManager em,boolean isNative) {
        Query query;
        if(isNative)
            query = em.createNativeQuery(queryStr);
        else
            query = em.createQuery(queryStr);
        if (!ArrayUtils.isEmpty(parameterNames) && !ArrayUtils.isEmpty(parameterValues)) {
            if (parameterNames.length != parameterValues.length) {
                throw new RuntimeException("Non matching sizes of parameter names ");
            }
            for (int i = 0; i < parameterNames.length; i++) {
                query.setParameter(parameterNames[i], parameterValues[i]);
            }
        }
        return query;
    }
}
