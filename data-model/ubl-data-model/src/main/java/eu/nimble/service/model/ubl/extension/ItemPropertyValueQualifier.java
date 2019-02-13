package eu.nimble.service.model.ubl.extension;

import com.sun.org.apache.xerces.internal.impl.xs.XSDDescription;

import java.util.*;

/**
 * Created by suat on 23-Jan-19.
 */
public enum ItemPropertyValueQualifier {
    TEXT("Text", "String", "String_Translatable"),
    NUMBER("Number", "Int", "Float", "Double", "Real_Count", "Integer_Count", "Rational"),
    QUANTITY("Quantity", "Amount", "Real_Measure", "Integer_Measure", "Rational_Measure", "Integer_Currency", "Real_Currency"),
    BOOLEAN("Boolean"),
    FILE("File", "Image", "Binary");

    private static Set<String> integerQualifiers = new HashSet<>();
    private static Set<String> floatQualifiers = new HashSet<>();
    static {
        integerQualifiers.add("Int");
        integerQualifiers.add("Integer_Count");
        integerQualifiers.add("Integer_Measure");
        integerQualifiers.add("Integer_Currency");
        floatQualifiers.add("Number");
        floatQualifiers.add("Float");
        floatQualifiers.add("Double");
        floatQualifiers.add("Real_Count");
        floatQualifiers.add("Rational");
        floatQualifiers.add("Quantity");
        floatQualifiers.add("Amount");
        floatQualifiers.add("Amount");
        floatQualifiers.add("Real_Measure");
        floatQualifiers.add("Rational_Measure");
        floatQualifiers.add("Real_Currency");
    }

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

    public static String getRange(String qualifier) {
        if(TEXT.alternatives.stream().filter(alternative -> alternative.compareToIgnoreCase(qualifier) == 0).findFirst().isPresent()) {
            return XSDDescription.XML_SCHEMA + "#string";
        } else if(floatQualifiers.stream().filter(alternative -> alternative.compareToIgnoreCase(qualifier) == 0).findFirst().isPresent()) {
            return XSDDescription.XML_SCHEMA + "#float";
        } else if(integerQualifiers.stream().filter(alternative -> alternative.compareToIgnoreCase(qualifier) == 0).findFirst().isPresent()) {
            return XSDDescription.XML_SCHEMA + "#int";
        } else if(BOOLEAN.alternatives.stream().filter(alternative -> alternative.compareToIgnoreCase(qualifier) == 0).findFirst().isPresent()) {
            return XSDDescription.XML_SCHEMA + "#boolean";
        } else if(FILE.alternatives.stream().filter(alternative -> alternative.compareToIgnoreCase(qualifier) == 0).findFirst().isPresent()) {
            return XSDDescription.XML_SCHEMA + "#base64Binary";
        }
        throw new RuntimeException(String.format("Invalid value '%s' for the value qualifier", qualifier));
    }

    public static ItemPropertyValueQualifier getQualifier(String range) {
        if(range.contentEquals(XSDDescription.XML_SCHEMA + "#string")) {
            return TEXT;
        } else if(range.contentEquals(XSDDescription.XML_SCHEMA + "#float") ||
                range.contentEquals(XSDDescription.XML_SCHEMA + "#double") ||
                range.contentEquals(XSDDescription.XML_SCHEMA + "#int")) {
            return NUMBER;
        } else if(range.contentEquals(XSDDescription.XML_SCHEMA + "#boolean")) {
            return BOOLEAN;
        } else if(range.contentEquals(XSDDescription.XML_SCHEMA + "#quantity")) {
            return QUANTITY;
        } else if(range.contentEquals(XSDDescription.XML_SCHEMA + "#base64Binary")) {
            return FILE;
        }

        throw new RuntimeException(String.format("Invalid range '%s'", range));
    }

    public List<String> getAlternatives() {
        return this.alternatives;
    }
}

