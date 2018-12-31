package eu.nimble.utility.persistence.resource;

import eu.nimble.utility.BinaryContentUtil;
import eu.nimble.utility.CommonSpringBridge;
import eu.nimble.utility.Configuration;
import eu.nimble.utility.JsonSerializationUtility;
import eu.nimble.utility.persistence.GenericJPARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * This class is intended to be used while connecting to catalogue databases. Other JPA-based repositories e.g.
 * businessprocessdb are not supposed to need the identifier and binary content check functionalities provided by this
 * wrapper.
 * <p>
 * This class is a wrapper on {@link GenericJPARepository} and {@link JpaRepository} interfaces so that resource-id
 * mappings can be managed from a single entry point considering the modifying operations (create, update and delete).
 * The class associates entity ids with party ids and user ids (if available) so that each party can only update entities
 * containing only the identifiers associated to it. Identifiers included in the {@link eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType} and {@link eu.nimble.service.model.ubl.commonaggregatecomponents.QualifyingPartyType}
 * objects are excluded since these entities are managed in a singleton manner and can be reused in documents
 * (e.g. {@link eu.nimble.service.model.ubl.order.OrderType} or {@link eu.nimble.service.model.ubl.iteminformationrequest.ItemInformationRequestType}).
 * All other entities are assumed to be associated with distinct parties.
 * <p>
 * Such a wrapper is required concerning especially the UBLDB since there are modifying updates where complex database
 * entities are passed with several database identifiers. There might be data integrity issues when the identifiers
 * passed in the entities are somehow mixed, are not proper.
 * <p>
 * <p>
 * Created by suat on 17-Dec-18.
 */
public class EntityIdAwareRepositoryWrapper<T> implements GenericJPARepository, JpaRepository<T, Long> {
    private static final Logger logger = LoggerFactory.getLogger(EntityIdAwareRepositoryWrapper.class);

    private GenericJPARepository genericJPARepository;
    private JpaRepository<T, Long> jpaRepository;
    private String partyId;
    private String userId;
    private String catalogueRepositoryName;

    public EntityIdAwareRepositoryWrapper(String partyId) {
        this(null, partyId, null, Configuration.Standard.UBL.toString());
    }

    public EntityIdAwareRepositoryWrapper(JpaRepository jpaRepository) {
        this(jpaRepository, null, null, Configuration.Standard.UBL.toString());
    }

    public EntityIdAwareRepositoryWrapper(JpaRepository jpaRepository, String partyId) {
        this(jpaRepository, partyId, null, Configuration.Standard.UBL.toString());
    }

    public EntityIdAwareRepositoryWrapper(JpaRepository jpaRepository, String partyId, String catalogueRepositoryName) {
        this(jpaRepository, partyId, null, catalogueRepositoryName);
    }

    public EntityIdAwareRepositoryWrapper(JpaRepository jpaRepository, String partyId, String userId, String catalogueRepositoryName) {
        this.jpaRepository = jpaRepository;
        this.partyId = partyId;
        this.userId = userId;
        this.catalogueRepositoryName = catalogueRepositoryName;
        this.genericJPARepository = CommonSpringBridge.getInstance().getGenericJPARepository();
    }

    @Override
    public <T> T getSingleEntityByHjid(Class<T> klass, long hjid) {
        return genericJPARepository.getSingleEntityByHjid(klass, hjid);
    }

    @Override
    public <T> T getSingleEntityByHjidWithCleanEm(Class<T> klass, long hjid) {
        return genericJPARepository.getSingleEntityByHjidWithCleanEm(klass, hjid);
    }

    @Override
    public <T> T getSingleEntity(String query, String[] parameterNames, Object[] parameterValues) {
        return genericJPARepository.getSingleEntity(query, parameterNames, parameterValues);
    }

    @Override
    public <T> List<T> getEntities(String query) {
        return genericJPARepository.getEntities(query);
    }

    @Override
    public <T> List<T> getEntities(String query, String[] parameterNames, Object[] parameterValues) {
        return genericJPARepository.getEntities(query, parameterNames, parameterValues);
    }

    @Override
    public <T> List<T> getEntities(String query, String[] parameterNames, Object[] parameterValues, Integer limit, Integer offset) {
        return genericJPARepository.getEntities(query, parameterNames, parameterValues, limit, offset);
    }

    @Override
    public <T> T updateEntity(T entity) {
        // check whether the ids included in the entity belongs to the party performing the update
        checkHjidAssociation(entity);
        // clear the entity identifiers for the passed entity
        clearIdsAndBinaryContentsForUpdatedEntity(entity);
        // create binary content references for the entity
        BinaryContentUtil.processBinaryContents(entity);
        // perform the update operation on the database
        entity = genericJPARepository.updateEntity(entity);
        // create entity ids for the entity
        ResourceValidationUtil.insertHjidsForObject(entity, partyId, catalogueRepositoryName);
        return entity;
    }

    @Override
    public <T> void deleteEntity(T entity) {
        checkHjidAssociation(entity);
        genericJPARepository.deleteEntity(entity);
        clearIdsAndBinaryContentsForDeletedEntity(entity);
    }

    @Override
    public <T> void deleteEntityByHjid(Class<T> klass, long hjid) {
        T entity = getSingleEntityByHjid(klass, hjid);
        checkHjidAssociation(entity);
        genericJPARepository.deleteEntity(entity);
        clearIdsAndBinaryContentsForDeletedEntity(entity);
    }

    @Override
    public <T> void persistEntity(T entity) {
        checkHjidExistence(entity);
        genericJPARepository.persistEntity(entity);
        createIdsAndBinaryContentsForEntity(entity);
    }

    @Override
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        return jpaRepository.findAll(sort);
    }

    @Override
    public List<T> findAll(Iterable<Long> longs) {
        return jpaRepository.findAll(longs);
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> entities) {
        throw new RuntimeException("This method has not been implemented");
    }

    @Override
    public void flush() {
        jpaRepository.flush();
    }

    @Override
    public <S extends T> S saveAndFlush(S entity) {
        throw new RuntimeException("This method has not been implemented");
    }

    @Override
    public void deleteInBatch(Iterable<T> entities) {
        throw new RuntimeException("This method has not been implemented");
    }

    @Override
    public void deleteAllInBatch() {
        throw new RuntimeException("This method has not been implemented");
    }

    @Override
    public T getOne(Long aLong) {
        return jpaRepository.getOne(aLong);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return jpaRepository.findAll(example);
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return jpaRepository.findAll(example, sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    @Override
    public <S extends T> S save(S entity) {
        // check whether the ids included in the entity belongs to the party performing the update
        checkHjidAssociation(entity);
        // clear the entity identifiers for the passed entity
        clearIdsAndBinaryContentsForUpdatedEntity(entity);
        // perform the update operation on db
        entity = jpaRepository.save(entity);
        // create entity id associations and binary content references
        createIdsAndBinaryContentsForEntity(entity);
        return entity;
    }

    @Override
    public T findOne(Long aLong) {
        return jpaRepository.findOne(aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return jpaRepository.exists(aLong);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public void delete(Long aLong) {
        T entity = jpaRepository.findOne(aLong);
        delete(entity);
    }

    @Override
    public void delete(T entity) {
        checkHjidAssociation(entity);
        jpaRepository.delete(entity);
        clearIdsAndBinaryContentsForDeletedEntity(entity);
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        throw new RuntimeException("This method has not been implemented");
    }

    @Override
    public void deleteAll() {
        throw new RuntimeException("This method has not been implemented");
    }

    @Override
    public <S extends T> S findOne(Example<S> example) {
        return jpaRepository.findOne(example);
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return jpaRepository.findAll(example, pageable);
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return jpaRepository.count(example);
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return jpaRepository.exists(example);
    }

    public <T> void checkHjidAssociation(T entity) {
        boolean entityAssociationCheck = ResourceValidationUtil.hjidsBelongsToParty(entity, partyId, catalogueRepositoryName);
        if (entityAssociationCheck == false) {
            String serializedEntity = JsonSerializationUtility.serializeEntitySilently(entity);
            throw new RepositoryException(String.format("There are entity ids (hjids) belonging to another company in the passed object: %s", serializedEntity));
        }
    }


    public <T> void checkHjidExistence(T entity) {
        boolean hjidsExist = ResourceValidationUtil.hjidsExit(entity);
        if (hjidsExist) {
            String serializedEntity = JsonSerializationUtility.serializeEntitySilently(entity);
            throw new RepositoryException(String.format("There are database ids (hjids) in the entity to be persisted. Make sure there is none. The entity: %s", serializedEntity));
        }
    }

    /**
     * Extracts the {@link Long} value of {@code hjid} field of the given entity.
     *
     * @param entity
     * @param <T>
     * @return
     */
    private <T> Long extractIdFromEntity(T entity) {
        Field field = getIdFieldFromClass(entity.getClass());
        // hjid field is not defined directly in the class of the given entity.
        // look for the field in the super classes
        if (field == null) {
            Class superClass;
            do {
                superClass = entity.getClass().getSuperclass();
                field = getIdFieldFromClass(superClass);
            } while (superClass != null && field == null);
        }

        if (field == null) {
            String serializedObject = JsonSerializationUtility.serializeEntitySilently(entity);
            String msg = String.format("No hjid field exists in the given class: %s, serialized object: %s", entity.getClass(), serializedObject);
            logger.error(msg);
            throw new RuntimeException(msg);
        }

        try {
            field.setAccessible(true);
            Long value = (Long) field.get(entity);
            field.setAccessible(false);
            return value;

        } catch (IllegalAccessException e) {
            String serializedObject = JsonSerializationUtility.serializeEntitySilently(entity);
            String msg = String.format("Failed to access to hjid field from entity with class: %s, serialized object: %s", entity.getClass(), serializedObject);
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    private Field getIdFieldFromClass(Class klass) {
        try {
            return klass.getDeclaredField("hjid");
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private <T> void clearIdsAndBinaryContentsForUpdatedEntity(T entity) {
        clearIdsAndBinaryContents(entity, "Update");
    }

    private <T> void clearIdsAndBinaryContentsForDeletedEntity(T entity) {
        clearIdsAndBinaryContents(entity, "Delete");
    }

    private <T> void clearIdsAndBinaryContents(T entity, String updateMode) {
        // remove the previous ids associated with the entity.
        // this checks the persisted entities that might be present the passed entity
        ResourceValidationUtil.removeHjidsForObject(entity, catalogueRepositoryName);

        List<String> binaryContentUrisToDelete = CommonSpringBridge.getInstance().getTransactionEnabledSerializationUtility().serializeBinaryObject(entity);
        if(updateMode.contentEquals("Update")) {
            // extract the hjid of the entity
            Long id = extractIdFromEntity(entity);
            // null id means that a new entity is being created
            if (id != null) {
                T originalEntity = (T) genericJPARepository.getSingleEntityByHjidWithCleanEm(entity.getClass(), id);
                // check the original entity. since there might be cases where an identifier is present but
                // the entity is not persisted in the database
                // if the entity exists clear the identifiers for that as well
                if (originalEntity != null) {
                    // remove ids for the entity
                    ResourceValidationUtil.removeHjidsForObject(originalEntity, catalogueRepositoryName);

                    // remove binary content from the binary content db for the passed entity
                    // get uris of binary contents in the updated entity
                    List<String> existingUris = CommonSpringBridge.getInstance().getTransactionEnabledSerializationUtility().serializeBinaryObject(originalEntity);
                    existingUris.removeAll(binaryContentUrisToDelete);
                    binaryContentUrisToDelete = existingUris;
                }
            }
        }
        BinaryContentUtil.removeBinaryContentFromDatabase(binaryContentUrisToDelete);
    }

    private <T> void createIdsAndBinaryContentsForEntity(T entity) {
        ResourceValidationUtil.insertHjidsForObject(entity, partyId, catalogueRepositoryName);
        BinaryContentUtil.processBinaryContents(entity);
    }
}
