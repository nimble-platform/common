package eu.nimble.service.model.ubl.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by suat on 23-Jan-19.
 */
public enum ItemPropertyValueQualifier {
    TEXT("Text", "String", "String_Translatable"),
    NUMBER("Number", "Int", "Float", "Double", "Real_Measure"),
    QUANTITY("Quantity", "Amount"),
    BOOLEAN("Boolean"),
    FILE("File", "Image", "Binary");

    private final List<String> alternatives;

    ItemPropertyValueQualifier(String... alternatives) {
        this.alternatives = new ArrayList<>();
        this.alternatives.addAll(Arrays.asList(alternatives));
    }


    public static ItemPropertyValueQualifier valueOfAlternative(String value) {
        for (ItemPropertyValueQualifier e : ItemPropertyValueQualifier.values()) {
            for (String alternative : e.alternatives) {
                if (alternative.compareToIgnoreCase(value) == 0) {
                    return e;
                }
            }
        }
        throw new RuntimeException(String.format("Invalid value '%s' for the value qualifier", value));
    }

    public List<String> getAlternatives() {
        return this.alternatives;
    }
}

