package eu.nimble.utility.persistence;

import org.apache.commons.lang.ArrayUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by suat on 27-Nov-18.
 */
public class GenericJPARepositoryImpl implements GenericJPARepository {

    private static final String QUERY_DELETE_BY_HJID = "DELETE FROM %s item WHERE item.hjid = :hjid";

    protected EntityManager em;

    public GenericJPARepositoryImpl(EntityManager em) {
        this.em = em;
    }

    public <T> T getSingleEntityByHjid(Class<T> klass, long hjid) {
        return em.find(klass, hjid);
    }

    public <T> T getSingleEntity(String queryStr, String[] parameterNames, Object[] parameterValues) {
        Query query = createQuery(queryStr, parameterNames, parameterValues);

        List<T> result = query.getResultList();
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result.get(0);
        }
    }

    public <T> List<T> getEntities(String query) {
        return getEntities(query, null, null);
    }

    public <T> List<T> getEntities(String queryStr, String[] parameterNames, Object[] parameterValues) {
        return getEntities(queryStr, parameterNames, parameterValues, null, null);
    }

    public <T> List<T> getEntities(String queryStr, String[] parameterNames, Object[] parameterValues, Integer limit, Integer offset) {
        Query query = createQuery(queryStr, parameterNames, parameterValues);

        if(limit != null && offset != null) {
            query.setFirstResult(offset);
            query.setMaxResults(limit);
        }

        List<T> result = query.getResultList();
        return result;
    }

    public <T> T updateEntity(T entity) {
        entity = em.merge(entity);
        return entity;
    }

    public <T> void deleteEntity(T entity) {
        if(!em.contains(entity)) {
            entity = em.merge(entity);
        }
        em.remove(entity);
    }

    public <T> void deleteEntityByHjid(Class<T> klass, long hjid) {
        String queryStr = String.format(QUERY_DELETE_BY_HJID, klass.getSimpleName());
        Query query = em.createQuery(queryStr);
        query.setParameter("hjid", hjid);
        query.executeUpdate();
    }

    public <T> void persistEntity(T entity) {
        em.persist(entity);
    }

    private Query createQuery(String queryStr, String[] parameterNames, Object[] parameterValues) {
        Query query = em.createQuery(queryStr);
        if(!ArrayUtils.isEmpty(parameterNames) && !ArrayUtils.isEmpty(parameterValues)) {
            if(parameterNames.length != parameterValues.length) {
                throw new RuntimeException("Non matching sizes of parameter names ");
            }
            for(int i=0; i<parameterNames.length; i++) {
                query.setParameter(parameterNames[i], parameterValues[i]);
            }
        }
        return query;
    }
}
