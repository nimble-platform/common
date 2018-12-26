package eu.nimble.utility;

import eu.nimble.utility.persistence.GenericJPARepository;
import eu.nimble.utility.persistence.binary.BinaryContentService;
import eu.nimble.utility.persistence.binary.BinaryObjectSerializerDelete;
import eu.nimble.utility.persistence.resource.ResourceTypeRepository;
import eu.nimble.utility.serialization.TransactionEnabledSerializationUtility;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by suat on 10-Dec-18.
 */
@Component
public class CommonSpringBridge implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Autowired(required = false)
    private ResourceTypeRepository resourceTypeRepository;
    @Autowired(required = false)
    private TransactionEnabledSerializationUtility transactionEnabledSerializationUtility;
    @Autowired(required = false) // it's assumed that catalogue repository would be injected by default
    private GenericJPARepository genericJPARepository;

    @Autowired
    private BinaryContentService binaryContentService;

    public static CommonSpringBridge getInstance() {
        return applicationContext.getBean(CommonSpringBridge.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ResourceTypeRepository getResourceTypeRepository() {
        return resourceTypeRepository;
    }

    public TransactionEnabledSerializationUtility getTransactionEnabledSerializationUtility() {
        return transactionEnabledSerializationUtility;
    }

    public BinaryContentService getBinaryContentService() {
        return binaryContentService;
    }

    public GenericJPARepository getGenericJPARepository() {
        return genericJPARepository;
    }
}
