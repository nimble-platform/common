package eu.nimble.utility;

import eu.nimble.utility.persistence.ResourceTypeRepository;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by suat on 10-Dec-18.
 */
@Component
public class SpringBridge implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    public static SpringBridge getInstance() {
        return applicationContext.getBean(SpringBridge.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    public ResourceTypeRepository getResourceTypeRepository() {
        return resourceTypeRepository;
    }
}