package eu.nimble.service.model.ubl.commonaggregatecomponents;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * HCDP-05-05: Logistics Provider DTO surfaced by {@code GET /logistics-providers}.
 *
 * <p>Originally a JPA entity backing an admin-curated directory table
 * (HCDP-05-04 §3.3). The directory has since been consolidated with the
 * catalogue: the endpoint now proxies {@code POST /party/search} on
 * indexing-service with {@code fq=businessType:"Logistics Provider"}, so
 * every entry corresponds to a real Nimble Party (seeded via STEP 7c) that
 * is also discoverable through the Find Logistics search.</p>
 *
 * <p>Pure transport object — no JPA mapping, no DB table.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogisticsProviderType", propOrder = {
    "slug",
    "name",
    "country",
    "partyId",
    "federationInstanceID"
})
public class LogisticsProviderType implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Stable slug derived from vatNumber.toLowerCase(), e.g. "de-dhl-logi-001". */
    @XmlElement(name = "Slug", required = true)
    protected String slug;

    /** Display name (legalName from indexing-service party search). */
    @XmlElement(name = "Name", required = true)
    protected String name;

    /** ISO-3166-1 alpha-2 country code derived from vatNumber prefix. */
    @XmlElement(name = "Country")
    protected String country;

    /** Real Nimble Party identifier — written into UBL carrierParty.partyIdentification[0].id on replacement. */
    @XmlElement(name = "PartyId", required = true)
    protected String partyId;

    /** Federation instance identifier for the party; null in federation-OFF deployments. */
    @XmlElement(name = "FederationInstanceID")
    protected String federationInstanceID;

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPartyId() { return partyId; }
    public void setPartyId(String partyId) { this.partyId = partyId; }

    public String getFederationInstanceID() { return federationInstanceID; }
    public void setFederationInstanceID(String federationInstanceID) { this.federationInstanceID = federationInstanceID; }
}
