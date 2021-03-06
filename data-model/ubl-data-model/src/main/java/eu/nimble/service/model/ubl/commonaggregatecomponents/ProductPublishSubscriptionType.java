//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2021.03.03 at 10:08:57 AM EET 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.nimble.service.model.ubl.commonbasiccomponents.CodeType;
import org.hibernate.annotations.Cascade;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for ProductPublishSubscriptionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductPublishSubscriptionType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CompanyID" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CategoryCode" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductPublishSubscriptionType", propOrder = {
    "companyID",
    "categoryCode"
})
@Entity(name = "ProductPublishSubscriptionType")
@Table(name = "PRODUCT_PUBLISH_SUBSCRIPTION_1")
@Inheritance(strategy = InheritanceType.JOINED)
public class ProductPublishSubscriptionType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "CompanyID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<String> companyID;
    @XmlElement(name = "CategoryCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<CodeType> categoryCode;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;
    protected transient List<ProductPublishSubscriptionTypeCompanyIDItem> companyIDItems;

    /**
     * Gets the value of the companyID property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the companyID property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompanyID().add(newItem);
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
    public List<String> getCompanyID() {
        if (companyID == null) {
            companyID = new ArrayList<String>();
        }
        return this.companyID;
    }

    /**
     * 
     * 
     */
    public void setCompanyID(List<String> companyID) {
        this.companyID = companyID;
    }

    /**
     * Gets the value of the categoryCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the categoryCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategoryCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = CodeType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "CATEGORY_CODE_PRODUCT_PUBLIS_0")
    public List<CodeType> getCategoryCode() {
        if (categoryCode == null) {
            categoryCode = new ArrayList<CodeType>();
        }
        return this.categoryCode;
    }

    /**
     * 
     * 
     */
    public void setCategoryCode(List<CodeType> categoryCode) {
        this.categoryCode = categoryCode;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ProductPublishSubscriptionType that = ((ProductPublishSubscriptionType) object);
        {
            List<String> lhsCompanyID;
            lhsCompanyID = (((this.companyID!= null)&&(!this.companyID.isEmpty()))?this.getCompanyID():null);
            List<String> rhsCompanyID;
            rhsCompanyID = (((that.companyID!= null)&&(!that.companyID.isEmpty()))?that.getCompanyID():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "companyID", lhsCompanyID), LocatorUtils.property(thatLocator, "companyID", rhsCompanyID), lhsCompanyID, rhsCompanyID)) {
                return false;
            }
        }
        {
            List<CodeType> lhsCategoryCode;
            lhsCategoryCode = (((this.categoryCode!= null)&&(!this.categoryCode.isEmpty()))?this.getCategoryCode():null);
            List<CodeType> rhsCategoryCode;
            rhsCategoryCode = (((that.categoryCode!= null)&&(!that.categoryCode.isEmpty()))?that.getCategoryCode():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "categoryCode", lhsCategoryCode), LocatorUtils.property(thatLocator, "categoryCode", rhsCategoryCode), lhsCategoryCode, rhsCategoryCode)) {
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

    @OneToMany(orphanRemoval = true, targetEntity = ProductPublishSubscriptionTypeCompanyIDItem.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "COMPANY_IDITEMS_PRODUCT_PUBL_0")
    public List<ProductPublishSubscriptionTypeCompanyIDItem> getCompanyIDItems() {
        if (this.companyIDItems == null) {
            this.companyIDItems = new ArrayList<ProductPublishSubscriptionTypeCompanyIDItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.companyID)) {
            this.companyID = ItemUtils.wrap(this.companyID, this.companyIDItems, ProductPublishSubscriptionTypeCompanyIDItem.class);
        }
        return this.companyIDItems;
    }

    public void setCompanyIDItems(List<ProductPublishSubscriptionTypeCompanyIDItem> value) {
        this.companyID = null;
        this.companyIDItems = null;
        this.companyIDItems = value;
        if (this.companyIDItems == null) {
            this.companyIDItems = new ArrayList<ProductPublishSubscriptionTypeCompanyIDItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.companyID)) {
            this.companyID = ItemUtils.wrap(this.companyID, this.companyIDItems, ProductPublishSubscriptionTypeCompanyIDItem.class);
        }
    }

}
