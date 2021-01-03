package eu.nimble.utility.country;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class CountryUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // country name map
    // the key is the country iso code
    // the key for the value map is the language id and its value is the country name
    private static Map<String, Map<String, String>> countryNames;
    // the available language ids for country names
    private static Set<String> languageIds;

    @PostConstruct
    private void init() {
        try {
            // read country names
            InputStream inputStream = CountryUtil.class.getResourceAsStream("/country/country-names.json");

            String fileContent = IOUtils.toString(inputStream);
            // set country names map
            countryNames = new ObjectMapper().readValue(fileContent, new TypeReference<Map<String, Map<String, String>>>() {
            });
            // set available language ids
            languageIds = countryNames.get(countryNames.entrySet().iterator().next().getKey()).keySet();
        } catch (IOException e) {
            logger.error("Failed to initialize Country Util", e);
        }
    }

    public static String getCountryNameByISOCode(String isoCode) {
        return getCountryNameByISOCode(isoCode, "en");
    }

    public static String getCountryNameByISOCode(String isoCode, String languageId) {
        // en is the default language
        if (!languageIds.contains(languageId)) {
            languageId = "en";
        }
        // get the country name for the given iso code
        Map<String, String> names = countryNames.get(isoCode);
        if (names != null) {
            return names.get(languageId);
        }
        return null;
    }

    public static Map<String, String> getCountryNamesByISOCode(String isoCode) {
        // get the country names for the given iso code
        return countryNames.get(isoCode);
    }

    public static String getISOCodeByCountryName(String countryName) {
        for (Map.Entry<String, Map<String, String>> isoCodeName : countryNames.entrySet()) {
            for (Map.Entry<String, String> names : isoCodeName.getValue().entrySet()) {
                if (names.getValue().compareToIgnoreCase(countryName) == 0) {
                    return isoCodeName.getKey();
                }
            }
        }
        return null;
    }
}
