package eu.nimble.utility.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nimble.utility.JsonSerializationUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * In a Spring-managed component serialization of lazy collections requires transaction management. Therefore, the
 * the client classes should be accessing the serialization utility by Spring means i.e. by Autowiring.
 *
 *
 * Created by suat on 28-Nov-18.
 */
@Component
public class TransactionEnabledSerializationUtility {

    private static Logger log = LoggerFactory.getLogger(TransactionEnabledSerializationUtility.class);

    @Autowired
    private EntityManagerFactory emf;

    public String serializeUBLObject(Object object) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
//            object = em.merge(object);

            ObjectMapper objectMapper = JsonSerializationUtility.getObjectMapper();
            String serializedObject = null;
            try {
                serializedObject = objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                String msg = String.format("Failed to serialize object: %s", object.getClass().getName());
                log.error(msg);
                throw new RuntimeException(msg, e);
            }
            if(em.getTransaction().isActive()) {
                em.getTransaction().commit();
            }
            return serializedObject;

        }catch (Exception e){
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to serialize ubl object",e);
        } finally {
            em.close();
        }
    }
}
