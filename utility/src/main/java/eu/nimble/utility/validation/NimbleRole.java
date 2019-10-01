package eu.nimble.utility.validation;

/**
 * Created by suat on 02-Sep-19.
 */
public enum NimbleRole {
    COMPANY_ADMIN("initial_representative"),
    EXTERNAL_REPRESENTATIVE("external_representative"),
    LEGAL_REPRESENTATIVE("legal_representative"),
    MONITOR("monitor"),
    PUBLISHER("publisher"),
    PURCHASER("purchaser"),
    SALES_OFFICER("sales_officer");

    private String name;

    NimbleRole(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
