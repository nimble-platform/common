package eu.nimble.utility.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nimble.utility.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

/**
 * Created by suat on 01-Jan-19.
 */
@Component
public class JPARepositoryFactory implements ApplicationContextAware {
    // this variables is defined as static so that the new instances of the factory can use the application context
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public GenericJPARepository withEmf(String emfBeanName) {
        GenericJPARepositoryImpl repository = new GenericJPARepositoryImpl();
        EntityManagerFactory emf = applicationContext.getBean(emfBeanName, EntityManagerFactory.class);
        repository.emf = emf;
        return repository;
    }

    public GenericJPARepository forBpRepository() {
        return withEmf("bpdbEntityManagerFactory");
    }

    public GenericJPARepository forCatalogueRepository() {
        return forCatalogueRepository(Configuration.Standard.UBL,true);
    }

    public GenericJPARepository forCatalogueRepository(boolean lazyEnabled){
        return forCatalogueRepository(Configuration.Standard.UBL,lazyEnabled);
    }

    public GenericJPARepository forCatalogueRepository(Configuration.Standard catalogueStandard, boolean lazyEnabled) {
        GenericJPARepositoryImpl repository = new GenericJPARepositoryImpl();
        if(catalogueStandard.equals(Configuration.Standard.UBL)) {
            if(lazyEnabled)
                return withEmf(Configuration.UBL_ENTITY_MANAGER_FACTORY);
            else
                return withEmf(Configuration.UBL_LAZY_DISABLED_ENTITY_MANAGER_FACTORY);
        } else if(catalogueStandard.equals(Configuration.Standard.MODAML)) {
            throw new RuntimeException("Configurations for MODA-ML catalogues are not set");
        }
        return repository;
    }
}
