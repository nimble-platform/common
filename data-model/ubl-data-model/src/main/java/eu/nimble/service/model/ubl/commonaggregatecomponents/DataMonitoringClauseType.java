//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.05 at 08:49:53 AM MSK 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.hibernate.annotations.Cascade;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for DataMonitoringClauseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DataMonitoringClauseType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ClauseType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MonitoringUserID" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MonitoringCompanyID" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MonitoredCompanyID" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ChannelID"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DataMonitoringClauseType", propOrder = {
    "monitoringUserID",
    "monitoringCompanyID",
    "monitoredCompanyID",
    "channelID"
})
@Entity(name = "DataMonitoringClauseType")
@Table(name = "DATA_MONITORING_CLAUSE_TYPE")
public class DataMonitoringClauseType
    extends ClauseType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "MonitoringUserID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected List<String> monitoringUserID;
    @XmlElement(name = "MonitoringCompanyID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected List<String> monitoringCompanyID;
    @XmlElement(name = "MonitoredCompanyID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected List<String> monitoredCompanyID;
    @XmlElement(name = "ChannelID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String channelID;
    protected transient List<DataMonitoringClauseTypeMonitoringUserIDItem> monitoringUserIDItems;
    protected transient List<DataMonitoringClauseTypeMonitoringCompanyIDItem> monitoringCompanyIDItems;
    protected transient List<DataMonitoringClauseTypeMonitoredCompanyIDItem> monitoredCompanyIDItems;

    /**
     * Gets the value of the monitoringUserID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monitoringUserID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonitoringUserID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    @Transient
    public List<String> getMonitoringUserID() {
        if (monitoringUserID == null) {
            monitoringUserID = new ArrayList<String>();
        }
        return this.monitoringUserID;
    }

    /**
     * 
     * 
     */
    public void setMonitoringUserID(List<String> monitoringUserID) {
        this.monitoringUserID = monitoringUserID;
    }

    /**
     * Gets the value of the monitoringCompanyID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monitoringCompanyID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonitoringCompanyID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    @Transient
    public List<String> getMonitoringCompanyID() {
        if (monitoringCompanyID == null) {
            monitoringCompanyID = new ArrayList<String>();
        }
        return this.monitoringCompanyID;
    }

    /**
     * 
     * 
     */
    public void setMonitoringCompanyID(List<String> monitoringCompanyID) {
        this.monitoringCompanyID = monitoringCompanyID;
    }

    /**
     * Gets the value of the monitoredCompanyID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the monitoredCompanyID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMonitoredCompanyID().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    @Transient
    public List<String> getMonitoredCompanyID() {
        if (monitoredCompanyID == null) {
            monitoredCompanyID = new ArrayList<String>();
        }
        return this.monitoredCompanyID;
    }

    /**
     * 
     * 
     */
    public void setMonitoredCompanyID(List<String> monitoredCompanyID) {
        this.monitoredCompanyID = monitoredCompanyID;
    }

    /**
     * Gets the value of the channelID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "CHANNEL_ID", length = 255)
    public String getChannelID() {
        return channelID;
    }

    /**
     * Sets the value of the channelID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelID(String value) {
        this.channelID = value;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!super.equals(thisLocator, thatLocator, object, strategy)) {
            return false;
        }
        final DataMonitoringClauseType that = ((DataMonitoringClauseType) object);
        {
            List<String> lhsMonitoringUserID;
            lhsMonitoringUserID = (((this.monitoringUserID!= null)&&(!this.monitoringUserID.isEmpty()))?this.getMonitoringUserID():null);
            List<String> rhsMonitoringUserID;
            rhsMonitoringUserID = (((that.monitoringUserID!= null)&&(!that.monitoringUserID.isEmpty()))?that.getMonitoringUserID():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "monitoringUserID", lhsMonitoringUserID), LocatorUtils.property(thatLocator, "monitoringUserID", rhsMonitoringUserID), lhsMonitoringUserID, rhsMonitoringUserID)) {
                return false;
            }
        }
        {
            List<String> lhsMonitoringCompanyID;
            lhsMonitoringCompanyID = (((this.monitoringCompanyID!= null)&&(!this.monitoringCompanyID.isEmpty()))?this.getMonitoringCompanyID():null);
            List<String> rhsMonitoringCompanyID;
            rhsMonitoringCompanyID = (((that.monitoringCompanyID!= null)&&(!that.monitoringCompanyID.isEmpty()))?that.getMonitoringCompanyID():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "monitoringCompanyID", lhsMonitoringCompanyID), LocatorUtils.property(thatLocator, "monitoringCompanyID", rhsMonitoringCompanyID), lhsMonitoringCompanyID, rhsMonitoringCompanyID)) {
                return false;
            }
        }
        {
            List<String> lhsMonitoredCompanyID;
            lhsMonitoredCompanyID = (((this.monitoredCompanyID!= null)&&(!this.monitoredCompanyID.isEmpty()))?this.getMonitoredCompanyID():null);
            List<String> rhsMonitoredCompanyID;
            rhsMonitoredCompanyID = (((that.monitoredCompanyID!= null)&&(!that.monitoredCompanyID.isEmpty()))?that.getMonitoredCompanyID():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "monitoredCompanyID", lhsMonitoredCompanyID), LocatorUtils.property(thatLocator, "monitoredCompanyID", rhsMonitoredCompanyID), lhsMonitoredCompanyID, rhsMonitoredCompanyID)) {
                return false;
            }
        }
        {
            String lhsChannelID;
            lhsChannelID = this.getChannelID();
            String rhsChannelID;
            rhsChannelID = that.getChannelID();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "channelID", lhsChannelID), LocatorUtils.property(thatLocator, "channelID", rhsChannelID), lhsChannelID, rhsChannelID)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    @OneToMany(orphanRemoval = true, targetEntity = DataMonitoringClauseTypeMonitoringUserIDItem.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "MONITORING_USER_IDITEMS_DATA_0")
    public List<DataMonitoringClauseTypeMonitoringUserIDItem> getMonitoringUserIDItems() {
        if (this.monitoringUserIDItems == null) {
            this.monitoringUserIDItems = new ArrayList<DataMonitoringClauseTypeMonitoringUserIDItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.monitoringUserID)) {
            this.monitoringUserID = ItemUtils.wrap(this.monitoringUserID, this.monitoringUserIDItems, DataMonitoringClauseTypeMonitoringUserIDItem.class);
        }
        return this.monitoringUserIDItems;
    }

    public void setMonitoringUserIDItems(List<DataMonitoringClauseTypeMonitoringUserIDItem> value) {
        this.monitoringUserID = null;
        this.monitoringUserIDItems = null;
        this.monitoringUserIDItems = value;
        if (this.monitoringUserIDItems == null) {
            this.monitoringUserIDItems = new ArrayList<DataMonitoringClauseTypeMonitoringUserIDItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.monitoringUserID)) {
            this.monitoringUserID = ItemUtils.wrap(this.monitoringUserID, this.monitoringUserIDItems, DataMonitoringClauseTypeMonitoringUserIDItem.class);
        }
    }

    @OneToMany(orphanRemoval = true, targetEntity = DataMonitoringClauseTypeMonitoringCompanyIDItem.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "MONITORING_COMPANY_IDITEMS_D_0")
    public List<DataMonitoringClauseTypeMonitoringCompanyIDItem> getMonitoringCompanyIDItems() {
        if (this.monitoringCompanyIDItems == null) {
            this.monitoringCompanyIDItems = new ArrayList<DataMonitoringClauseTypeMonitoringCompanyIDItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.monitoringCompanyID)) {
            this.monitoringCompanyID = ItemUtils.wrap(this.monitoringCompanyID, this.monitoringCompanyIDItems, DataMonitoringClauseTypeMonitoringCompanyIDItem.class);
        }
        return this.monitoringCompanyIDItems;
    }

    public void setMonitoringCompanyIDItems(List<DataMonitoringClauseTypeMonitoringCompanyIDItem> value) {
        this.monitoringCompanyID = null;
        this.monitoringCompanyIDItems = null;
        this.monitoringCompanyIDItems = value;
        if (this.monitoringCompanyIDItems == null) {
            this.monitoringCompanyIDItems = new ArrayList<DataMonitoringClauseTypeMonitoringCompanyIDItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.monitoringCompanyID)) {
            this.monitoringCompanyID = ItemUtils.wrap(this.monitoringCompanyID, this.monitoringCompanyIDItems, DataMonitoringClauseTypeMonitoringCompanyIDItem.class);
        }
    }

    @OneToMany(orphanRemoval = true, targetEntity = DataMonitoringClauseTypeMonitoredCompanyIDItem.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "MONITORED_COMPANY_IDITEMS_DA_0")
    public List<DataMonitoringClauseTypeMonitoredCompanyIDItem> getMonitoredCompanyIDItems() {
        if (this.monitoredCompanyIDItems == null) {
            this.monitoredCompanyIDItems = new ArrayList<DataMonitoringClauseTypeMonitoredCompanyIDItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.monitoredCompanyID)) {
            this.monitoredCompanyID = ItemUtils.wrap(this.monitoredCompanyID, this.monitoredCompanyIDItems, DataMonitoringClauseTypeMonitoredCompanyIDItem.class);
        }
        return this.monitoredCompanyIDItems;
    }

    public void setMonitoredCompanyIDItems(List<DataMonitoringClauseTypeMonitoredCompanyIDItem> value) {
        this.monitoredCompanyID = null;
        this.monitoredCompanyIDItems = null;
        this.monitoredCompanyIDItems = value;
        if (this.monitoredCompanyIDItems == null) {
            this.monitoredCompanyIDItems = new ArrayList<DataMonitoringClauseTypeMonitoredCompanyIDItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.monitoredCompanyID)) {
            this.monitoredCompanyID = ItemUtils.wrap(this.monitoredCompanyID, this.monitoredCompanyIDItems, DataMonitoringClauseTypeMonitoredCompanyIDItem.class);
        }
    }

}
