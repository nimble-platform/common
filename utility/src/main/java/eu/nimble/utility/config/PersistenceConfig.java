package eu.nimble.utility.config;


import eu.nimble.utility.Configuration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by suat on 10-Oct-17.
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "persistence.orm")
@PropertySource("classpath:bootstrap.yml")
public class PersistenceConfig implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static PersistenceConfig getInstance() {
        return applicationContext.getBean(PersistenceConfig.class);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    private Environment environment;

    private Map<String, String> ubl = new HashMap<>();
    private Map<String, String> modaml = new HashMap<>();

    @Bean(name = "ubldbHibernateConfigs")
    public Map<String, String> getUbl() {
        return ubl;
    }

    public void setUbl(Map<String, String> ubl) {
        this.ubl = ubl;
    }

    public Map<String, String> getModaml() {
        return modaml;
    }

    public void setModaml(Map<String, String> modaml) {
        this.modaml = modaml;
    }

    @PostConstruct
    private void setupDbConnections() {
        if (environment != null) {
            // check for "kubernetes" profile
            if (Arrays.stream(environment.getActiveProfiles()).anyMatch(profile -> profile.contentEquals("kubernetes"))) {

                // setup ubl database
                String UblDBCredentialsJson = environment.getProperty("persistence.orm.ubl.bluemix.credentials_json");
                BluemixDatabaseConfig ublDBconfig = new BluemixDatabaseConfig(UblDBCredentialsJson);
                ublDBconfig.copyToHibernatePersistenceParameters(ubl);

                // setup ubl database
                String modaMlDBCredentialsJson = environment.getProperty("persistence.orm.modaml.bluemix.credentials_json");
                BluemixDatabaseConfig modaMlDBconfig = new BluemixDatabaseConfig(modaMlDBCredentialsJson);
                modaMlDBconfig.copyToHibernatePersistenceParameters(modaml);
            }
        }
    }

    public Map<String, String> getPersistenceParameters(String persistenceUnitName) {
        Map<String, String> persistenceParameters = null;
        if(persistenceUnitName.contentEquals(Configuration.UBL_PERSISTENCE_UNIT_NAME)) {
            persistenceParameters = ubl;
        } else if(persistenceUnitName.contentEquals(Configuration.MODAML_PERSISTENCE_UNIT_NAME)) {
            persistenceParameters = modaml;
        } else {
            throw new RuntimeException("Unknown persistence unit name: " + persistenceUnitName);
        }
        return persistenceParameters;
    }

}