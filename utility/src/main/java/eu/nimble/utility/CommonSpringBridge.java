package eu.nimble.utility;

import eu.nimble.utility.config.CommonConfig;
import eu.nimble.utility.persistence.binary.BinaryContentService;
import eu.nimble.utility.persistence.resource.ResourceValidationUtility;
import eu.nimble.utility.serialization.TransactionEnabledSerializationUtility;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by suat on 10-Dec-18.
 */
@Component
public class CommonSpringBridge implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Autowired
    private TransactionEnabledSerializationUtility transactionEnabledSerializationUtility;
    @Autowired
    private BinaryContentService binaryContentService;
    @Autowired
    private ResourceValidationUtility resourceValidationUtil;
    @Autowired(required = false)
    @Qualifier("ubldbDataSource")
    private DataSource ubldbDataSource;
    @Autowired
    private CommonConfig commonConfig;

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

    public ResourceValidationUtility getResourceValidationUtil() {
        return resourceValidationUtil;
    }

    public DataSource getUbldbDataSource() {
        return ubldbDataSource;
    }

    public CommonConfig getCommonConfig() {
        return commonConfig;
    }
}
