package eu.nimble.utility.persistence.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.BinaryContentUtil;
import eu.nimble.utility.CommonSpringBridge;
import eu.nimble.utility.Configuration;
import eu.nimble.utility.JsonSerializationUtility;
import eu.nimble.utility.persistence.GenericJPARepository;
import eu.nimble.utility.persistence.GenericJPARepositoryImpl;
import eu.nimble.utility.serialization.BinaryObjectSerializerGetUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
import java.util.List;

/**
 * <p>
 * This class is intended to be used while connecting to catalogue databases. Other JPA-based repositories e.g.
 * businessprocessdb are not supposed to need the identifier and binary content check functionalities provided by this
 * wrapper.
 * <p>
 * This class is a wrapper on {@link GenericJPARepositoryImpl} and {@link JpaRepository} interfaces so that resource-id
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
public class EntityIdAwareRepositoryWrapper implements GenericJPARepository {
    private static final Logger logger = LoggerFactory.getLogger(EntityIdAwareRepositoryWrapper.class);

    protected GenericJPARepositoryImpl genericJPARepository;
    protected String partyId;
    protected String userId;
    protected String catalogueRepositoryName;

    public EntityIdAwareRepositoryWrapper() {
        this(null, null, Configuration.Standard.UBL.toString());
    }

    public EntityIdAwareRepositoryWrapper(String partyId) {
        this(partyId, null, Configuration.Standard.UBL.toString());
    }

    public EntityIdAwareRepositoryWrapper(String partyId, String catalogueRepositoryName) {
        this(partyId, null, catalogueRepositoryName);
    }

    public EntityIdAwareRepositoryWrapper(String partyId, String userId, String catalogueRepositoryName) {
        this.partyId = partyId;
        this.userId = userId;
        this.catalogueRepositoryName = catalogueRepositoryName;
        this.genericJPARepository = CommonSpringBridge.getInstance().getGenericJPARepository().withEmf("ubldbEntityManagerFactory");
    }

    @Override
    public GenericJPARepository withEmf(String emfBeanName) {
        return this;
    }

    @Override
    public EntityManagerFactory getEmf() {
        return genericJPARepository.getEmf();
    }

    public <T> T getSingleEntityByHjid(Class<T> klass, long hjid) {
        return genericJPARepository.getSingleEntityByHjid(klass, hjid);
    }

    public <T> T getSingleEntity(String query, String[] parameterNames, Object[] parameterValues) {
        return genericJPARepository.getSingleEntity(query, parameterNames, parameterValues);
    }

    public <T> List<T> getEntities(String query) {
        return genericJPARepository.getEntities(query);
    }

    public <T> List<T> getEntities(String query, String[] parameterNames, Object[] parameterValues) {
        return genericJPARepository.getEntities(query, parameterNames, parameterValues);
    }

    public <T> List<T> getEntities(String query, String[] parameterNames, Object[] parameterValues, Integer limit, Integer offset) {
        return genericJPARepository.getEntities(query, parameterNames, parameterValues, limit, offset);
    }

    @Override
    public <T> List<T> getEntities(Class<T> klass) {
        return genericJPARepository.getEntities(klass);
    }

    @Override
    public <T> T updateEntity(T entity) {
        return proceedWithEntityUpdates(entity, UpdateMode.UPDATE);
    }

    public <T> T updateEntityForPersistCases(T entity) {
        return proceedWithEntityUpdates(entity, UpdateMode.PERSIST);
    }

    private <T> T proceedWithEntityUpdates(T entity, UpdateMode updateMode) {
        // check whether the ids included in the entity belongs to the party performing the update
        checkHjidAssociation(entity, updateMode);
        // clear the entity identifiers for the passed entity
        clearIdsAndBinaryContents(entity, updateMode);
        // create binary content references for the entity
        BinaryContentUtil.processBinaryContents(entity);
        // perform the update operation on the database
        entity = genericJPARepository.updateEntity(entity);
        // create entity ids for the entity
        CommonSpringBridge.getInstance().getResourceValidationUtil().insertHjidsForObject(entity, partyId, catalogueRepositoryName);
        return entity;
    }

    @Override
    public <T> void deleteEntity(T entity) {
        checkHjidAssociationWithTransaction(entity);
        genericJPARepository.deleteEntity(entity);
        clearIdsAndBinaryContents(entity, UpdateMode.DELETE);
    }

    @Override
    public <T> void deleteEntityByHjid(Class<T> klass, long hjid) {
        T entity = getSingleEntityByHjid(klass, hjid);
        checkHjidAssociationWithTransaction(entity);
        genericJPARepository.deleteEntity(entity);
        clearIdsAndBinaryContents(entity, UpdateMode.DELETE);
    }

    @Override
    public <T> void persistEntity(T entity) {
        checkHjidExistence(entity);
        genericJPARepository.persistEntity(entity);
        createIdsAndBinaryContentsForEntity(entity);
    }

    @Override
    public <T> void persistEntities(Iterable<T> entities) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void executeUpdate(String query, String[] parameterNames, Object[] parameterValues) {
        throw new RuntimeException("Not implemented yet");
    }

    private <T> void checkHjidAssociation(T entity, UpdateMode updateMode) {
        if(updateMode.equals(UpdateMode.PERSIST)) {
            checkHjidAssociation(entity);
        } else {
            checkHjidAssociationWithTransaction(entity);
        }
    }

    private <T> void checkHjidAssociationWithTransaction(T entity) {
        EntityManager em = genericJPARepository.getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            checkHjidAssociation(entity);

        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
    }

    private <T> void checkHjidAssociation(T entity) {
        boolean entityAssociationCheck = CommonSpringBridge.getInstance().getResourceValidationUtil().hjidsBelongsToParty(entity, partyId, catalogueRepositoryName);
        if (entityAssociationCheck == false) {
            String serializedEntity = JsonSerializationUtility.serializeEntitySilently(entity);
            throw new RepositoryException(String.format("There are entity ids (hjids) belonging to another company in the passed object: %s", serializedEntity));
        }
    }

    private <T> void checkHjidExistence(T entity) {
        boolean hjidsExist = CommonSpringBridge.getInstance().getResourceValidationUtil().hjidsExit(entity);
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

    private <T> void clearIdsAndBinaryContents(T entity, UpdateMode updateMode) {
        // remove the previous ids associated with the entity.
        // this checks the persisted entities that might be present the passed entity
        CommonSpringBridge.getInstance().getResourceValidationUtil().removeHjidsForObject(entity, catalogueRepositoryName);

        List<String> binaryContentUrisToDelete;
        // do not use a transaction-enabled serializer in persist method to prevent the passed entity from being
        // persisted as a side-effect of serialization procedure
        if(updateMode.equals(UpdateMode.PERSIST)) {
            binaryContentUrisToDelete = getBinaryObjectUrisToDelete(entity);
        } else {
            binaryContentUrisToDelete = getBinaryObjectUrisToDeleteWithTransaction(entity);
        }

        if(!updateMode.equals(UpdateMode.DELETE)) {
            // extract the hjid of the entity
            if(updateMode.equals(UpdateMode.UPDATE)) {
                Long id = extractIdFromEntity(entity);
                T originalEntity = (T) genericJPARepository.getSingleEntityByHjid(entity.getClass(), id);
                // remove ids for the entity
                CommonSpringBridge.getInstance().getResourceValidationUtil().removeHjidsForObject(originalEntity, catalogueRepositoryName);

                // remove binary content from the binary content db for the passed entity
                // get uris of binary contents in the updated entity
                List<String> existingUris = getBinaryObjectUrisToDeleteWithTransaction(originalEntity);
                existingUris.removeAll(binaryContentUrisToDelete);
                binaryContentUrisToDelete = existingUris;
            }
        }
        BinaryContentUtil.removeBinaryContentFromDatabase(binaryContentUrisToDelete);
    }

    private <T> void createIdsAndBinaryContentsForEntity(T entity) {
        CommonSpringBridge.getInstance().getResourceValidationUtil().insertHjidsForObject(entity, partyId, catalogueRepositoryName);
        BinaryContentUtil.processBinaryContents(entity);
    }

    private <T> List<String> getBinaryObjectUrisToDeleteWithTransaction(T entity) {
        EntityManager em = genericJPARepository.getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            return getBinaryObjectUrisToDelete(entity);

        } finally {
            if(em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            em.close();
        }
    }

    private <T> List<String> getBinaryObjectUrisToDelete(T entity) {
        ObjectMapper objectMapper = JsonSerializationUtility.getObjectMapper();
        BinaryObjectSerializerGetUris serializer = new BinaryObjectSerializerGetUris();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(BinaryObjectType.class, serializer);
        objectMapper.registerModule(simpleModule);

        try {
            objectMapper.writeValueAsString(entity);

        } catch (JsonProcessingException e) {
            String msg = String.format("Failed to serialize object: %s", entity.getClass().getName());
            logger.error(msg);
            throw new RuntimeException(msg, e);
        }
        return serializer.getListOfUris();
    }

    private enum UpdateMode {
        PERSIST, UPDATE, DELETE
    }
}
