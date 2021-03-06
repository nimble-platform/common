//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.10 at 10:02:02 AM EET 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.nimble.service.model.ubl.commonbasiccomponents.CodeType;
import eu.nimble.service.model.ubl.commonbasiccomponents.TextType;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for EventType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EventType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IdentificationID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TypeCode" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CompletionIndicator" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OccurenceLocation" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DurationPeriod" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EventType", propOrder = {
    "identificationID",
    "typeCode",
    "description",
    "completionIndicator",
    "occurenceLocation",
    "durationPeriod"
})
@Entity(name = "EventType")
@Table(name = "EVENT_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class EventType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "IdentificationID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected String identificationID;
    @XmlElement(name = "TypeCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CodeType typeCode;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TextType description;
    @XmlElement(name = "CompletionIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected Boolean completionIndicator;
    @XmlElement(name = "OccurenceLocation")
    protected LocationType occurenceLocation;
    @XmlElement(name = "DurationPeriod")
    protected PeriodType durationPeriod;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    /**
     * Gets the value of the identificationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "IDENTIFICATION_ID", length = 255)
    public String getIdentificationID() {
        return identificationID;
    }

    /**
     * Sets the value of the identificationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdentificationID(String value) {
        this.identificationID = value;
    }

    /**
     * Gets the value of the typeCode property.
     * 
     * @return
     *     possible object is
     *     {@link CodeType }
     *     
     */
    @ManyToOne(targetEntity = CodeType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "TYPE_CODE_EVENT_TYPE_HJID")
    public CodeType getTypeCode() {
        return typeCode;
    }

    /**
     * Sets the value of the typeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeType }
     *     
     */
    public void setTypeCode(CodeType value) {
        this.typeCode = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    @ManyToOne(targetEntity = TextType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "DESCRIPTION_EVENT_TYPE_HJID")
    public TextType getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setDescription(TextType value) {
        this.description = value;
    }

    /**
     * Gets the value of the completionIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    @Basic
    @Column(name = "COMPLETION_INDICATOR")
    public Boolean isCompletionIndicator() {
        return completionIndicator;
    }

    /**
     * Sets the value of the completionIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCompletionIndicator(Boolean value) {
        this.completionIndicator = value;
    }

    /**
     * Gets the value of the occurenceLocation property.
     * 
     * @return
     *     possible object is
     *     {@link LocationType }
     *     
     */
    @ManyToOne(targetEntity = LocationType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "OCCURENCE_LOCATION_EVENT_TYP_0")
    public LocationType getOccurenceLocation() {
        return occurenceLocation;
    }

    /**
     * Sets the value of the occurenceLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationType }
     *     
     */
    public void setOccurenceLocation(LocationType value) {
        this.occurenceLocation = value;
    }

    /**
     * Gets the value of the durationPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    @ManyToOne(targetEntity = PeriodType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "DURATION_PERIOD_EVENT_TYPE_H_0")
    public PeriodType getDurationPeriod() {
        return durationPeriod;
    }

    /**
     * Sets the value of the durationPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setDurationPeriod(PeriodType value) {
        this.durationPeriod = value;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final EventType that = ((EventType) object);
        {
            String lhsIdentificationID;
            lhsIdentificationID = this.getIdentificationID();
            String rhsIdentificationID;
            rhsIdentificationID = that.getIdentificationID();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "identificationID", lhsIdentificationID), LocatorUtils.property(thatLocator, "identificationID", rhsIdentificationID), lhsIdentificationID, rhsIdentificationID)) {
                return false;
            }
        }
        {
            CodeType lhsTypeCode;
            lhsTypeCode = this.getTypeCode();
            CodeType rhsTypeCode;
            rhsTypeCode = that.getTypeCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "typeCode", lhsTypeCode), LocatorUtils.property(thatLocator, "typeCode", rhsTypeCode), lhsTypeCode, rhsTypeCode)) {
                return false;
            }
        }
        {
            TextType lhsDescription;
            lhsDescription = this.getDescription();
            TextType rhsDescription;
            rhsDescription = that.getDescription();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "description", lhsDescription), LocatorUtils.property(thatLocator, "description", rhsDescription), lhsDescription, rhsDescription)) {
                return false;
            }
        }
        {
            Boolean lhsCompletionIndicator;
            lhsCompletionIndicator = this.isCompletionIndicator();
            Boolean rhsCompletionIndicator;
            rhsCompletionIndicator = that.isCompletionIndicator();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "completionIndicator", lhsCompletionIndicator), LocatorUtils.property(thatLocator, "completionIndicator", rhsCompletionIndicator), lhsCompletionIndicator, rhsCompletionIndicator)) {
                return false;
            }
        }
        {
            LocationType lhsOccurenceLocation;
            lhsOccurenceLocation = this.getOccurenceLocation();
            LocationType rhsOccurenceLocation;
            rhsOccurenceLocation = that.getOccurenceLocation();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "occurenceLocation", lhsOccurenceLocation), LocatorUtils.property(thatLocator, "occurenceLocation", rhsOccurenceLocation), lhsOccurenceLocation, rhsOccurenceLocation)) {
                return false;
            }
        }
        {
            PeriodType lhsDurationPeriod;
            lhsDurationPeriod = this.getDurationPeriod();
            PeriodType rhsDurationPeriod;
            rhsDurationPeriod = that.getDurationPeriod();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "durationPeriod", lhsDurationPeriod), LocatorUtils.property(thatLocator, "durationPeriod", rhsDurationPeriod), lhsDurationPeriod, rhsDurationPeriod)) {
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

}
