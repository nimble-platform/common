package eu.nimble.utility.persistence.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.utility.BinaryContentUtil;
import eu.nimble.utility.Configuration;
import eu.nimble.utility.JsonSerializationUtility;
import eu.nimble.utility.persistence.GenericJPARepository;
import eu.nimble.utility.persistence.JPARepositoryFactory;
import eu.nimble.utility.persistence.binary.BinaryContentService;
import eu.nimble.utility.serialization.binary_processing.BinaryObjectSerializerGetUris;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper implementation of {@link GenericJPARepository} with capability to process binary contents.
 * TODO: regular DB and binary content related operations should be atomic
 */
public class BinaryContentAwareRepositoryWrapper implements GenericJPARepository {
    private static final Logger logger = LoggerFactory.getLogger(BinaryContentAwareRepositoryWrapper.class);

    protected GenericJPARepository genericJPARepository;
    protected String partyId;
    protected String userId;
    protected String catalogueRepositoryName;
    private BinaryContentService binaryContentService = new BinaryContentService();

    public BinaryContentAwareRepositoryWrapper() {
        this(Configuration.Standard.UBL.toString(), new JPARepositoryFactory().forCatalogueRepository(Configuration.Standard.UBL,true,false));
    }

    public BinaryContentAwareRepositoryWrapper(GenericJPARepository repository) {
        this(Configuration.Standard.UBL.toString(),repository);
    }

    public BinaryContentAwareRepositoryWrapper(String catalogueRepositoryName) {
        this(catalogueRepositoryName, new JPARepositoryFactory().forCatalogueRepository(Configuration.Standard.UBL,true,false));
    }

    /**
     * @param repository single-transaction enabled Catalogue repository
     * */
    public BinaryContentAwareRepositoryWrapper(String catalogueRepositoryName, GenericJPARepository repository) {
        this.catalogueRepositoryName = catalogueRepositoryName;
        this.genericJPARepository = repository;
    }

    @Override
    public void beginTransaction() {
        this.binaryContentService.beginTransaction();
    }

    @Override
    public void commit() {
        this.genericJPARepository.commit();
        this.binaryContentService.commit();
    }

    @Override
    public void rollback() {
        this.genericJPARepository.rollback();
        this.binaryContentService.rollback();
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
    public <T> List<T> getEntities(String query, String[] parameterNames, Object[] parameterValues, Integer limit, Integer offset, boolean isNative){
        return genericJPARepository.getEntities(query,parameterNames,parameterValues,limit,offset,isNative);
    }

    @Override
    public <T> T updateEntity(T entity) {
        // get binary object identifiers that are not included in the updated entity
        Long id = extractIdFromEntity(entity);
        T originalEntity = (T) genericJPARepository.getSingleEntityByHjid(entity.getClass(), id);
        List<String> binaryContentUrisToDelete = getBinaryObjectUrisToDeleteWithTransaction(entity, originalEntity, UpdateMode.UPDATE);
        // create binary content references for the entity
        BinaryContentUtil.processBinaryContents(entity,binaryContentService);
        // perform the update operation on the database
        entity = genericJPARepository.updateEntity(entity);
        // clear binary contents
        BinaryContentUtil.removeBinaryContentFromDatabase(binaryContentUrisToDelete,binaryContentService);
        return entity;
    }

    /**
     * This method is required e.g. when storing a document object in business process operations. In such cases, an {@link eu.nimble.service.model.ubl.commonaggregatecomponents.ItemType}
     * object and/or a {@link eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType} object would be included in the document.
     * Therefore, we cannot use persist operation and have to use update.
     * @param entity
     * @param <T>
     * @return
     */
    public <T> T updateEntityForPersistCases(T entity) {
        // create binary content references for the entity
        BinaryContentUtil.processBinaryContents(entity,binaryContentService);
        // perform the update operation on the database
        entity = genericJPARepository.updateEntity(entity);
        return entity;
    }

    /**
     * Updates the given entity and persists the given binary objects
     * @param entity
     * @param binaryObjectsToPersist
     * @return
     */
    public <T> T updateEntity(T entity, List<BinaryObjectType> binaryObjectsToPersist) {
        return updateEntity(entity, binaryObjectsToPersist, new ArrayList<String>());
    }

    public <T> T updateEntity(T entity, List<BinaryObjectType> binaryObjectsToPersist, List<String> binaryObjectsToDelete) {
        binaryContentService.persistBinaryObjects(binaryObjectsToPersist);
        // update operation should be done before deleting the contents as the references should be updated to be able to
        // check reference counts binary objects
        entity = genericJPARepository.updateEntity(entity);
        binaryContentService.deleteContents(binaryObjectsToDelete);
        return entity;
    }

    @Override
    public <T> void deleteEntity(T entity) {
//        checkHjidAssociationWithTransaction(entity);
        List<String> binaryContentUrisToDelete = getBinaryObjectUrisToDeleteWithTransaction(entity, UpdateMode.DELETE);
        genericJPARepository.deleteEntity(entity);
        // clear binary contents
        BinaryContentUtil.removeBinaryContentFromDatabase(binaryContentUrisToDelete,binaryContentService);
    }

    public <T> void deleteEntityByHjid(Class<T> klass, long hjid, List<String> binaryObjectsToDelete) {
        genericJPARepository.deleteEntityByHjid(klass, hjid);
        BinaryContentUtil.removeBinaryContentFromDatabase(binaryObjectsToDelete, binaryContentService);
    }

    @Override
    public <T> void deleteEntityByHjid(Class<T> klass, long hjid) {
        T entity = getSingleEntityByHjid(klass, hjid);
//        checkHjidAssociationWithTransaction(entity);
        List<String> binaryContentUrisToDelete = getBinaryObjectUrisToDeleteWithTransaction(entity, UpdateMode.DELETE);
        genericJPARepository.deleteEntity(entity);
        // clear binary contents
        BinaryContentUtil.removeBinaryContentFromDatabase(binaryContentUrisToDelete,binaryContentService);
    }

    @Override
    public <T> void persistEntity(T entity) {
        genericJPARepository.persistEntity(entity);
        createBinaryContentsForEntity(entity);
    }

    public <T> void persistEntity(T entity, List<BinaryObjectType> binaryObjects) {
        binaryContentService.persistBinaryObjects(binaryObjects);
        genericJPARepository.persistEntity(entity);
    }

    @Override
    public <T> void persistEntities(Iterable<T> entities) {
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void executeUpdate(String query, String[] parameterNames, Object[] parameterValues) {
        throw new RuntimeException("Not implemented yet");
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

    private <T> void createBinaryContentsForEntity(T entity) {
        BinaryContentUtil.processBinaryContents(entity,binaryContentService);
    }

    private <T> List<String> getBinaryObjectUrisToDeleteWithTransaction(T entity, UpdateMode updateMode) {
        return getBinaryObjectUrisToDeleteWithTransaction(entity, null, updateMode);
    }

    private <T> List<String> getBinaryObjectUrisToDeleteWithTransaction(T entity, T originalEntity, UpdateMode updateMode) {
        List<String> binaryContentUrisToDelete;
        // do not use a transaction-enabled serializer in persist method to prevent the passed entity from being
        // persisted as a side-effect of serialization procedure
//        if(updateMode.equals(UpdateMode.PERSIST)) {
//            binaryContentUrisToDelete = getBinaryObjectUrisToDelete(entity);
//        } else {
//            binaryContentUrisToDelete = getBinaryObjectUrisToDeleteWithTransaction(entity);
//        }
        binaryContentUrisToDelete = getBinaryObjectUrisToDelete(entity);

        if(updateMode.equals(UpdateMode.UPDATE)) {
            // remove binary content from the binary content db for the passed entity
            // get uris of binary contents in the updated entity
//            List<String> existingUris = getBinaryObjectUrisToDeleteWithTransaction(originalEntity);
            List<String> existingUris = getBinaryObjectUrisToDelete(originalEntity);
            existingUris.removeAll(binaryContentUrisToDelete);
            binaryContentUrisToDelete = existingUris;
        }

        return binaryContentUrisToDelete;
    }

//    private <T> List<String> getBinaryObjectUrisToDeleteWithTransaction(T entity) {
//        EntityManager em = genericJPARepository.getEmf().createEntityManager();
//        try {
//            em.getTransaction().begin();
//            if(em.getTransaction().isActive()) {
//                em.getTransaction().commit();
//            }
//            return getBinaryObjectUrisToDelete(entity);
//        } catch (Exception e){
//            if(em.getTransaction().isActive()) {
//                em.getTransaction().rollback();
//            }
//            throw new RuntimeException("Failed to get binary object uris to delete",e);
//        }finally {
//            em.close();
//        }
//    }

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
        UPDATE, DELETE
    }
}
