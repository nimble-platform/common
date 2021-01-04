package eu.nimble.utility.serialization;

import java.util.ArrayList;
import java.util.List;

public enum SerializerConfig {
    EXCLUDE_BINARY_BYTES(1),
    FILLED_FIELDS(2),
    EXCLUDE_LAZY_COLLECTIONS(4);

    private int configCode;

    SerializerConfig(int number) {
        this.configCode = number;
    }

    public int getConfigCode() {
        return configCode;
    }

    public static void main(String[] a) {
        System.out.println(fragmentConfig(0));
        System.out.println(fragmentConfig(1));
        System.out.println(fragmentConfig(2));
        System.out.println(fragmentConfig(3));
        System.out.println(fragmentConfig(4));
        System.out.println(fragmentConfig(5));
    }

    /**
     * Fragments the given config code to binary representation for determining the configs to be applied
     * @param configCode
     * @return
     */
    public static List<Integer> fragmentConfig(int configCode) {
        List<Integer> configs = new ArrayList<>();

        while(configCode > 0) {
            configs.add(configCode % 2);
            configCode /= 2;
        }

        return configs;
    }
}
