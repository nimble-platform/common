package eu.nimble.service.model.ubl.commonaggregatecomponents;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * HCDP-05-04: Logistics Provider directory entry.
 *
 * <p>Backs the Replace Provider modal's alternatives dropdown (see
 * {@code replace-provider-modal.component.html}). One row per carrier
 * the demo platform supports. Seeded via {@code seed-demo.sh} STEP 16;
 * read by Angular through {@code GET /logistics-providers}.</p>
 *
 * <p><b>linkedPartyId</b> is intentionally nullable — future iteration may
 * migrate each carrier to a real Nimble Party (HCDP-05-04 Open Item §8.B);
 * the FK is reserved here so that migration is non-breaking.</p>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LogisticsProviderType", propOrder = {
    "slug",
    "name",
    "country",
    "transitTimeHint",
    "active",
    "linkedPartyId"
})
@Entity(name = "LogisticsProviderType")
@Table(name = "LOGISTICS_PROVIDER_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class LogisticsProviderType implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Stable slug, e.g. "dhl-freight". Used by Angular as <option [ngValue]>. */
    @XmlElement(name = "Slug", required = true)
    protected String slug;

    /** Display name, e.g. "DHL Freight". Written into UBL carrierParty.partyName on replacement. */
    @XmlElement(name = "Name", required = true)
    protected String name;

    /** ISO-3166-1 alpha-2 country code, e.g. "DE". */
    @XmlElement(name = "Country")
    protected String country;

    /** Human-readable transit time hint, e.g. "2-4 days". */
    @XmlElement(name = "TransitTimeHint")
    protected String transitTimeHint;

    /** Soft-delete flag. Inactive entries excluded from the alternatives dropdown. */
    @XmlElement(name = "Active")
    protected Boolean active;

    /** Reserved for future migration to Nimble Party-backed carriers (see §8 Open Item). */
    @XmlElement(name = "LinkedPartyId")
    protected String linkedPartyId;

    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    // ---- Getters / Setters ----

    @Basic
    @Column(name = "SLUG", length = 64, nullable = false, unique = true)
    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    @Basic
    @Column(name = "NAME", length = 128, nullable = false)
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Basic
    @Column(name = "COUNTRY", length = 2)
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    @Basic
    @Column(name = "TRANSIT_TIME_HINT", length = 64)
    public String getTransitTimeHint() { return transitTimeHint; }
    public void setTransitTimeHint(String transitTimeHint) { this.transitTimeHint = transitTimeHint; }

    @Basic
    @Column(name = "ACTIVE")
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    @Basic
    @Column(name = "LINKED_PARTY_ID", length = 64)
    public String getLinkedPartyId() { return linkedPartyId; }
    public void setLinkedPartyId(String linkedPartyId) { this.linkedPartyId = linkedPartyId; }

    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() { return hjid; }
    public void setHjid(Long hjid) { this.hjid = hjid; }
}
