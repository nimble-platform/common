package eu.nimble.service.model.ubl.commonaggregatecomponents;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * HCDP-03-03: Demand Response entity.
 * Stores a supplier's offer in response to a buyer's published demand.
 * Links the demand to a specific catalogue line (product) from the supplier's catalogue.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DemandResponseType", propOrder = {
    "demandHJID",
    "responderCompanyId",
    "responderCompanyName",
    "catalogueLineHjid",
    "catalogueUuid",
    "lineId",
    "productName",
    "message",
    "createdDate"
})
@Entity(name = "DemandResponseType")
@Table(name = "DEMAND_RESPONSE_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class DemandResponseType implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "DemandHJID")
    protected long demandHJID;

    @XmlElement(name = "ResponderCompanyId", required = true)
    protected String responderCompanyId;

    @XmlElement(name = "ResponderCompanyName")
    protected String responderCompanyName;

    @XmlElement(name = "CatalogueLineHjid")
    protected long catalogueLineHjid;

    @XmlElement(name = "CatalogueUuid")
    protected String catalogueUuid;

    @XmlElement(name = "LineId")
    protected String lineId;

    @XmlElement(name = "ProductName")
    protected String productName;

    @XmlElement(name = "Message")
    protected String message;

    @XmlElement(name = "CreatedDate")
    protected String createdDate;

    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    @Basic
    @Column(name = "DEMAND_HJID", precision = 20, scale = 0)
    public long getDemandHJID() { return demandHJID; }
    public void setDemandHJID(long demandHJID) { this.demandHJID = demandHJID; }

    @Basic
    @Column(name = "RESPONDER_COMPANY_ID", length = 255)
    public String getResponderCompanyId() { return responderCompanyId; }
    public void setResponderCompanyId(String responderCompanyId) { this.responderCompanyId = responderCompanyId; }

    @Basic
    @Column(name = "RESPONDER_COMPANY_NAME", length = 512)
    public String getResponderCompanyName() { return responderCompanyName; }
    public void setResponderCompanyName(String responderCompanyName) { this.responderCompanyName = responderCompanyName; }

    @Basic
    @Column(name = "CATALOGUE_LINE_HJID", precision = 20, scale = 0)
    public long getCatalogueLineHjid() { return catalogueLineHjid; }
    public void setCatalogueLineHjid(long catalogueLineHjid) { this.catalogueLineHjid = catalogueLineHjid; }

    @Basic
    @Column(name = "CATALOGUE_UUID", length = 255)
    public String getCatalogueUuid() { return catalogueUuid; }
    public void setCatalogueUuid(String catalogueUuid) { this.catalogueUuid = catalogueUuid; }

    @Basic
    @Column(name = "LINE_ID", length = 255)
    public String getLineId() { return lineId; }
    public void setLineId(String lineId) { this.lineId = lineId; }

    @Basic
    @Column(name = "PRODUCT_NAME", length = 512)
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    @Basic
    @Column(name = "MESSAGE", length = 2000)
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Basic
    @Column(name = "CREATED_DATE", length = 64)
    public String getCreatedDate() { return createdDate; }
    public void setCreatedDate(String createdDate) { this.createdDate = createdDate; }

    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() { return hjid; }
    public void setHjid(Long hjid) { this.hjid = hjid; }
}
