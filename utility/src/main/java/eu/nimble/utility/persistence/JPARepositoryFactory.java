package eu.nimble.utility.persistence;

import eu.nimble.utility.Configuration;
import org.springframework.beans.BeansException;
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

    public GenericJPARepository withEmf(String emfBeanName, Boolean multiTransactions) {
        GenericJPARepositoryImpl repository = new GenericJPARepositoryImpl();
        EntityManagerFactory emf = applicationContext.getBean(emfBeanName, EntityManagerFactory.class);
        repository.emf = emf;

        if(multiTransactions){
            repository.beginTransaction();
        }
        return repository;
    }

    public GenericJPARepository forBpRepositoryMultiTransaction() {
        return forBpRepositoryMultiTransaction(false);
    }

    public GenericJPARepository forBpRepositoryMultiTransaction(boolean lazyDisabled) {
        return lazyDisabled ? withEmf(Configuration.BP_LAZY_DISABLED_ENTITY_MANAGER_FACTORY,true):withEmf(Configuration.BP_ENTITY_MANAGER_FACTORY,true);
    }

    public GenericJPARepository forBpRepository() {
        return forBpRepository(false);
    }

    public GenericJPARepository forBpRepository(boolean lazyDisabled) {
        return lazyDisabled ? withEmf(Configuration.BP_LAZY_DISABLED_ENTITY_MANAGER_FACTORY,false):withEmf(Configuration.BP_ENTITY_MANAGER_FACTORY,false);
    }

    public GenericJPARepository forCatalogueRepositoryMultiTransaction() {
        return forCatalogueRepository(Configuration.Standard.UBL,false,true);
    }

    public GenericJPARepository forCatalogueRepositoryMultiTransaction(boolean lazyDisabled){
        return forCatalogueRepository(Configuration.Standard.UBL,lazyDisabled,true);
    }

    public GenericJPARepository forCatalogueRepository() {
        return forCatalogueRepository(Configuration.Standard.UBL,false,false);
    }

    public GenericJPARepository forCatalogueRepository(boolean lazyDisabled){
        return forCatalogueRepository(Configuration.Standard.UBL,lazyDisabled,false);
    }

    public GenericJPARepository forCatalogueRepository(Configuration.Standard catalogueStandard, boolean lazyDisabled, boolean multiTransactions) {
        GenericJPARepositoryImpl repository = new GenericJPARepositoryImpl();
        if(catalogueStandard.equals(Configuration.Standard.UBL)) {
            if(lazyDisabled)
                return withEmf(Configuration.UBL_LAZY_DISABLED_ENTITY_MANAGER_FACTORY,multiTransactions);
            else
                return withEmf(Configuration.UBL_ENTITY_MANAGER_FACTORY,multiTransactions);
        } else if(catalogueStandard.equals(Configuration.Standard.MODAML)) {
            throw new RuntimeException("Configurations for MODA-ML catalogues are not set");
        }
        return repository;
    }
}
