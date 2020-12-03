package eu.nimble.common.rest.identity.model;

public class PersonPartyTuple {
    private String companyID;
    private String personID;

    public PersonPartyTuple() {
    }

    public PersonPartyTuple(String companyID, String personID) {
        this.companyID = companyID;
        this.personID = personID;
    }

    public String getCompanyID() {
        return companyID;
    }

    public void setCompanyID(String companyID) {
        this.companyID = companyID;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
