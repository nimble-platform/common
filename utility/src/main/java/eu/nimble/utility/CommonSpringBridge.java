package eu.nimble.utility;

import eu.nimble.utility.persistence.GenericJPARepositoryImpl;
import eu.nimble.utility.persistence.binary.BinaryContentService;
import eu.nimble.utility.persistence.resource.ResourceValidationUtil;
import eu.nimble.utility.serialization.TransactionEnabledSerializationUtility;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Created by suat on 10-Dec-18.
 */
@Component
public class CommonSpringBridge implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Autowired(required = false) // it's assumed that catalogue repository would be injected by default
    private GenericJPARepositoryImpl genericJPARepository;
    @Autowired(required = false)
    private EntityManagerFactory emf;

    @Autowired
    private TransactionEnabledSerializationUtility transactionEnabledSerializationUtility;
    @Autowired
    private BinaryContentService binaryContentService;
    @Autowired
    private ResourceValidationUtil resourceValidationUtil;

    public static CommonSpringBridge getInstance() {
        return applicationContext.getBean(CommonSpringBridge.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public TransactionEnabledSerializationUtility getTransactionEnabledSerializationUtility() {
        return transactionEnabledSerializationUtility;
    }

    public BinaryContentService getBinaryContentService() {
        return binaryContentService;
    }

    public GenericJPARepositoryImpl getGenericJPARepository() {
        return genericJPARepository.withEmf("ubldbEntityManagerFactory");
    }

    public ResourceValidationUtil getResourceValidationUtil() {
        return resourceValidationUtil;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }
}
