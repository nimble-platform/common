package eu.nimble.utility.config;

import com.sun.istack.NotNull;
import eu.nimble.utility.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

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
public class PersistenceConfig {

    private static PersistenceConfig instance;

    private PersistenceConfig() {
        instance = this;
    }

    private static boolean dbInitialized = false;
    public static PersistenceConfig getInstance() {
        if(instance != null && dbInitialized == false) {
            instance.setupDbConnections();
            dbInitialized = true;
        }
        return instance;
    }

    @Autowired
    private Environment environment;

    private Map<String, String> ubl = new HashMap<>();
    private Map<String, String> modaml = new HashMap<>();

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