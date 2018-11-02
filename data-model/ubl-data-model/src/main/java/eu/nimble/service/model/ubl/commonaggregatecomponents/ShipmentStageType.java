//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.11.02 at 03:35:43 PM MSK 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.nimble.service.model.ubl.commonbasiccomponents.CodeType;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDate;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for ShipmentStageType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipmentStageType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TransportModeCode" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EstimatedDeliveryDate" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CarrierParty" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TransportMeans" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipmentStageType", propOrder = {
    "transportModeCode",
    "estimatedDeliveryDate",
    "carrierParty",
    "transportMeans"
})
@Entity(name = "ShipmentStageType")
@Table(name = "SHIPMENT_STAGE_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class ShipmentStageType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "TransportModeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CodeType transportModeCode;
    @XmlElement(name = "EstimatedDeliveryDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar estimatedDeliveryDate;
    @XmlElement(name = "CarrierParty")
    protected PartyType carrierParty;
    @XmlElement(name = "TransportMeans")
    protected TransportMeansType transportMeans;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    /**
     * Gets the value of the transportModeCode property.
     * 
     * @return
     *     possible object is
     *     {@link CodeType }
     *     
     */
    @ManyToOne(targetEntity = CodeType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "TRANSPORT_MODE_CODE_SHIPMENT_0")
    public CodeType getTransportModeCode() {
        return transportModeCode;
    }

    /**
     * Sets the value of the transportModeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeType }
     *     
     */
    public void setTransportModeCode(CodeType value) {
        this.transportModeCode = value;
    }

    /**
     * Gets the value of the estimatedDeliveryDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Transient
    public XMLGregorianCalendar getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    /**
     * Sets the value of the estimatedDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEstimatedDeliveryDate(XMLGregorianCalendar value) {
        this.estimatedDeliveryDate = value;
    }

    /**
     * Gets the value of the carrierParty property.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    @ManyToOne(targetEntity = PartyType.class, cascade = {
        javax.persistence.CascadeType.PERSIST,javax.persistence.CascadeType.MERGE,javax.persistence.CascadeType.REFRESH
    })
    @JoinColumn(name = "CARRIER_PARTY_SHIPMENT_STAGE_0")
    public PartyType getCarrierParty() {
        return carrierParty;
    }

    /**
     * Sets the value of the carrierParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setCarrierParty(PartyType value) {
        this.carrierParty = value;
    }

    /**
     * Gets the value of the transportMeans property.
     * 
     * @return
     *     possible object is
     *     {@link TransportMeansType }
     *     
     */
    @ManyToOne(targetEntity = TransportMeansType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "TRANSPORT_MEANS_SHIPMENT_STA_0")
    public TransportMeansType getTransportMeans() {
        return transportMeans;
    }

    /**
     * Sets the value of the transportMeans property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransportMeansType }
     *     
     */
    public void setTransportMeans(TransportMeansType value) {
        this.transportMeans = value;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ShipmentStageType that = ((ShipmentStageType) object);
        {
            CodeType lhsTransportModeCode;
            lhsTransportModeCode = this.getTransportModeCode();
            CodeType rhsTransportModeCode;
            rhsTransportModeCode = that.getTransportModeCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "transportModeCode", lhsTransportModeCode), LocatorUtils.property(thatLocator, "transportModeCode", rhsTransportModeCode), lhsTransportModeCode, rhsTransportModeCode)) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsEstimatedDeliveryDate;
            lhsEstimatedDeliveryDate = this.getEstimatedDeliveryDate();
            XMLGregorianCalendar rhsEstimatedDeliveryDate;
            rhsEstimatedDeliveryDate = that.getEstimatedDeliveryDate();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "estimatedDeliveryDate", lhsEstimatedDeliveryDate), LocatorUtils.property(thatLocator, "estimatedDeliveryDate", rhsEstimatedDeliveryDate), lhsEstimatedDeliveryDate, rhsEstimatedDeliveryDate)) {
                return false;
            }
        }
        {
            PartyType lhsCarrierParty;
            lhsCarrierParty = this.getCarrierParty();
            PartyType rhsCarrierParty;
            rhsCarrierParty = that.getCarrierParty();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "carrierParty", lhsCarrierParty), LocatorUtils.property(thatLocator, "carrierParty", rhsCarrierParty), lhsCarrierParty, rhsCarrierParty)) {
                return false;
            }
        }
        {
            TransportMeansType lhsTransportMeans;
            lhsTransportMeans = this.getTransportMeans();
            TransportMeansType rhsTransportMeans;
            rhsTransportMeans = that.getTransportMeans();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "transportMeans", lhsTransportMeans), LocatorUtils.property(thatLocator, "transportMeans", rhsTransportMeans), lhsTransportMeans, rhsTransportMeans)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    /**
     * Gets the value of the hjid property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    @Id
    @Column(name = "HJID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getHjid() {
        return hjid;
    }

    /**
     * Sets the value of the hjid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHjid(Long value) {
        this.hjid = value;
    }

    @Basic
    @Column(name = "ESTIMATED_DELIVERY_DATE_ITEM")
    @Temporal(TemporalType.DATE)
    public Date getEstimatedDeliveryDateItem() {
        return XmlAdapterUtils.unmarshall(XMLGregorianCalendarAsDate.class, this.getEstimatedDeliveryDate());
    }

    public void setEstimatedDeliveryDateItem(Date target) {
        setEstimatedDeliveryDate(XmlAdapterUtils.marshall(XMLGregorianCalendarAsDate.class, target));
    }

}
