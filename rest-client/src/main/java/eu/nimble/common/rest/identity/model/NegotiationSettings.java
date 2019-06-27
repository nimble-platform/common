package eu.nimble.common.rest.identity.model;

import eu.nimble.service.model.ubl.commonaggregatecomponents.PartyType;

import java.util.ArrayList;
import java.util.List;

public class NegotiationSettings {

    private PartyType company;
    private List<String> paymentTerms = new ArrayList<>();
    private List<String> paymentMeans = new ArrayList<>();

    public NegotiationSettings() {
    }

    public NegotiationSettings(PartyType company, List<String> paymentTerms, List<String> paymentMeans) {
        this.company = company;
        this.paymentTerms = paymentTerms;
        this.paymentMeans = paymentMeans;
    }

    public PartyType getCompany() {
        return company;
    }

    public void setCompany(PartyType company) {
        this.company = company;
    }

    public List<String> getPaymentTerms() {
        return paymentTerms;
    }

    public void setPaymentTerms(List<String> paymentTerms) {
        this.paymentTerms = paymentTerms;
    }

    public List<String> getPaymentMeans() {
        return paymentMeans;
    }

    public void setPaymentMeans(List<String> paymentMeans) {
        this.paymentMeans = paymentMeans;
    }
}
