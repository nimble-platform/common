//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.29 at 11:06:30 AM MSK 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import java.io.Serializable;
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
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for LifeCyclePerformanceAssessmentDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LifeCyclePerformanceAssessmentDetailsType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LCPAInput"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LCPAOutput"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LifeCyclePerformanceAssessmentDetailsType", propOrder = {
    "lcpaInput",
    "lcpaOutput"
})
@Entity(name = "LifeCyclePerformanceAssessmentDetailsType")
@Table(name = "LIFE_CYCLE_PERFORMANCE_ASSES_2")
@Inheritance(strategy = InheritanceType.JOINED)
public class LifeCyclePerformanceAssessmentDetailsType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "LCPAInput", required = true)
    protected LCPAInputType lcpaInput;
    @XmlElement(name = "LCPAOutput", required = true)
    protected LCPAOutputType lcpaOutput;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;

    /**
     * Gets the value of the lcpaInput property.
     * 
     * @return
     *     possible object is
     *     {@link LCPAInputType }
     *     
     */
    @ManyToOne(targetEntity = LCPAInputType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "LCPAINPUT_LIFE_CYCLE_PERFORM_0")
    public LCPAInputType getLCPAInput() {
        return lcpaInput;
    }

    /**
     * Sets the value of the lcpaInput property.
     * 
     * @param value
     *     allowed object is
     *     {@link LCPAInputType }
     *     
     */
    public void setLCPAInput(LCPAInputType value) {
        this.lcpaInput = value;
    }

    /**
     * Gets the value of the lcpaOutput property.
     * 
     * @return
     *     possible object is
     *     {@link LCPAOutputType }
     *     
     */
    @ManyToOne(targetEntity = LCPAOutputType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "LCPAOUTPUT_LIFE_CYCLE_PERFOR_0")
    public LCPAOutputType getLCPAOutput() {
        return lcpaOutput;
    }

    /**
     * Sets the value of the lcpaOutput property.
     * 
     * @param value
     *     allowed object is
     *     {@link LCPAOutputType }
     *     
     */
    public void setLCPAOutput(LCPAOutputType value) {
        this.lcpaOutput = value;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final LifeCyclePerformanceAssessmentDetailsType that = ((LifeCyclePerformanceAssessmentDetailsType) object);
        {
            LCPAInputType lhsLCPAInput;
            lhsLCPAInput = this.getLCPAInput();
            LCPAInputType rhsLCPAInput;
            rhsLCPAInput = that.getLCPAInput();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "lcpaInput", lhsLCPAInput), LocatorUtils.property(thatLocator, "lcpaInput", rhsLCPAInput), lhsLCPAInput, rhsLCPAInput)) {
                return false;
            }
        }
        {
            LCPAOutputType lhsLCPAOutput;
            lhsLCPAOutput = this.getLCPAOutput();
            LCPAOutputType rhsLCPAOutput;
            rhsLCPAOutput = that.getLCPAOutput();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "lcpaOutput", lhsLCPAOutput), LocatorUtils.property(thatLocator, "lcpaOutput", rhsLCPAOutput), lhsLCPAOutput, rhsLCPAOutput)) {
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
