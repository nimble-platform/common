//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.11.02 at 03:35:43 PM MSK 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import eu.nimble.service.model.BigDecimalXmlAdapter;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.service.model.ubl.commonbasiccomponents.CodeType;
import eu.nimble.service.model.ubl.commonbasiccomponents.QuantityType;
import org.hibernate.annotations.Cascade;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for ItemPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemPropertyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Value" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueQuantity" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueDecimal" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueBinary" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ValueQualifier" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}URI"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ItemClassificationCode" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemPropertyType", propOrder = {
    "id",
    "name",
    "value",
    "valueQuantity",
    "valueDecimal",
    "valueBinary",
    "valueQualifier",
    "uri",
    "itemClassificationCode"
})
@Entity(name = "ItemPropertyType")
@Table(name = "ITEM_PROPERTY_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class ItemPropertyType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String id;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String name;
    @XmlElement(name = "Value", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<String> value;
    @XmlElement(name = "ValueQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<QuantityType> valueQuantity;
    @XmlElement(name = "ValueDecimal", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", type = String.class)
    @XmlJavaTypeAdapter(BigDecimalXmlAdapter.class)
    protected List<BigDecimal> valueDecimal;
    @XmlElement(name = "ValueBinary", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<BinaryObjectType> valueBinary;
    @XmlElement(name = "ValueQualifier", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected String valueQualifier;
    @XmlElement(name = "URI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String uri;
    @XmlElement(name = "ItemClassificationCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CodeType itemClassificationCode;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;
    protected transient List<ItemPropertyTypeValueItem> valueItems;
    protected transient List<ItemPropertyTypeValueDecimalItem> valueDecimalItems;

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "ID", length = 255)
    public String getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "NAME_", length = 255)
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
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
    public List<String> getValue() {
        if (value == null) {
            value = new ArrayList<String>();
        }
        return this.value;
    }

    /**
     * 
     * 
     */
    public void setValue(List<String> value) {
        this.value = value;
    }

    /**
     * Gets the value of the valueQuantity property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueQuantity property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueQuantity().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QuantityType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = QuantityType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "VALUE_QUANTITY_ITEM_PROPERTY_0")
    public List<QuantityType> getValueQuantity() {
        if (valueQuantity == null) {
            valueQuantity = new ArrayList<QuantityType>();
        }
        return this.valueQuantity;
    }

    /**
     * 
     * 
     */
    public void setValueQuantity(List<QuantityType> valueQuantity) {
        this.valueQuantity = valueQuantity;
    }

    /**
     * Gets the value of the valueDecimal property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueDecimal property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueDecimal().add(newItem);
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
    public List<BigDecimal> getValueDecimal() {
        if (valueDecimal == null) {
            valueDecimal = new ArrayList<BigDecimal>();
        }
        return this.valueDecimal;
    }

    /**
     * 
     * 
     */
    public void setValueDecimal(List<BigDecimal> valueDecimal) {
        this.valueDecimal = valueDecimal;
    }

    /**
     * Gets the value of the valueBinary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the valueBinary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValueBinary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BinaryObjectType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = BinaryObjectType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "VALUE_BINARY_ITEM_PROPERTY_T_0")
    public List<BinaryObjectType> getValueBinary() {
        if (valueBinary == null) {
            valueBinary = new ArrayList<BinaryObjectType>();
        }
        return this.valueBinary;
    }

    /**
     * 
     * 
     */
    public void setValueBinary(List<BinaryObjectType> valueBinary) {
        this.valueBinary = valueBinary;
    }

    /**
     * Gets the value of the valueQualifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "VALUE_QUALIFIER", length = 255)
    public String getValueQualifier() {
        return valueQualifier;
    }

    /**
     * Sets the value of the valueQualifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueQualifier(String value) {
        this.valueQualifier = value;
    }

    /**
     * Gets the value of the uri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "URI", length = 255)
    public String getURI() {
        return uri;
    }

    /**
     * Sets the value of the uri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURI(String value) {
        this.uri = value;
    }

    /**
     * Gets the value of the itemClassificationCode property.
     * 
     * @return
     *     possible object is
     *     {@link CodeType }
     *     
     */
    @ManyToOne(targetEntity = CodeType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "ITEM_CLASSIFICATION_CODE_ITE_0")
    public CodeType getItemClassificationCode() {
        return itemClassificationCode;
    }

    /**
     * Sets the value of the itemClassificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeType }
     *     
     */
    public void setItemClassificationCode(CodeType value) {
        this.itemClassificationCode = value;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final ItemPropertyType that = ((ItemPropertyType) object);
        {
            String lhsID;
            lhsID = this.getID();
            String rhsID;
            rhsID = that.getID();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "id", lhsID), LocatorUtils.property(thatLocator, "id", rhsID), lhsID, rhsID)) {
                return false;
            }
        }
        {
            String lhsName;
            lhsName = this.getName();
            String rhsName;
            rhsName = that.getName();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "name", lhsName), LocatorUtils.property(thatLocator, "name", rhsName), lhsName, rhsName)) {
                return false;
            }
        }
        {
            List<String> lhsValue;
            lhsValue = (((this.value!= null)&&(!this.value.isEmpty()))?this.getValue():null);
            List<String> rhsValue;
            rhsValue = (((that.value!= null)&&(!that.value.isEmpty()))?that.getValue():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "value", lhsValue), LocatorUtils.property(thatLocator, "value", rhsValue), lhsValue, rhsValue)) {
                return false;
            }
        }
        {
            List<QuantityType> lhsValueQuantity;
            lhsValueQuantity = (((this.valueQuantity!= null)&&(!this.valueQuantity.isEmpty()))?this.getValueQuantity():null);
            List<QuantityType> rhsValueQuantity;
            rhsValueQuantity = (((that.valueQuantity!= null)&&(!that.valueQuantity.isEmpty()))?that.getValueQuantity():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "valueQuantity", lhsValueQuantity), LocatorUtils.property(thatLocator, "valueQuantity", rhsValueQuantity), lhsValueQuantity, rhsValueQuantity)) {
                return false;
            }
        }
        {
            List<BigDecimal> lhsValueDecimal;
            lhsValueDecimal = (((this.valueDecimal!= null)&&(!this.valueDecimal.isEmpty()))?this.getValueDecimal():null);
            List<BigDecimal> rhsValueDecimal;
            rhsValueDecimal = (((that.valueDecimal!= null)&&(!that.valueDecimal.isEmpty()))?that.getValueDecimal():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "valueDecimal", lhsValueDecimal), LocatorUtils.property(thatLocator, "valueDecimal", rhsValueDecimal), lhsValueDecimal, rhsValueDecimal)) {
                return false;
            }
        }
        {
            List<BinaryObjectType> lhsValueBinary;
            lhsValueBinary = (((this.valueBinary!= null)&&(!this.valueBinary.isEmpty()))?this.getValueBinary():null);
            List<BinaryObjectType> rhsValueBinary;
            rhsValueBinary = (((that.valueBinary!= null)&&(!that.valueBinary.isEmpty()))?that.getValueBinary():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "valueBinary", lhsValueBinary), LocatorUtils.property(thatLocator, "valueBinary", rhsValueBinary), lhsValueBinary, rhsValueBinary)) {
                return false;
            }
        }
        {
            String lhsValueQualifier;
            lhsValueQualifier = this.getValueQualifier();
            String rhsValueQualifier;
            rhsValueQualifier = that.getValueQualifier();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "valueQualifier", lhsValueQualifier), LocatorUtils.property(thatLocator, "valueQualifier", rhsValueQualifier), lhsValueQualifier, rhsValueQualifier)) {
                return false;
            }
        }
        {
            String lhsURI;
            lhsURI = this.getURI();
            String rhsURI;
            rhsURI = that.getURI();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "uri", lhsURI), LocatorUtils.property(thatLocator, "uri", rhsURI), lhsURI, rhsURI)) {
                return false;
            }
        }
        {
            CodeType lhsItemClassificationCode;
            lhsItemClassificationCode = this.getItemClassificationCode();
            CodeType rhsItemClassificationCode;
            rhsItemClassificationCode = that.getItemClassificationCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "itemClassificationCode", lhsItemClassificationCode), LocatorUtils.property(thatLocator, "itemClassificationCode", rhsItemClassificationCode), lhsItemClassificationCode, rhsItemClassificationCode)) {
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

    @OneToMany(orphanRemoval = true, targetEntity = ItemPropertyTypeValueItem.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "VALUE_ITEMS_ITEM_PROPERTY_TY_0")
    public List<ItemPropertyTypeValueItem> getValueItems() {
        if (this.valueItems == null) {
            this.valueItems = new ArrayList<ItemPropertyTypeValueItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.value)) {
            this.value = ItemUtils.wrap(this.value, this.valueItems, ItemPropertyTypeValueItem.class);
        }
        return this.valueItems;
    }

    public void setValueItems(List<ItemPropertyTypeValueItem> value) {
        this.value = null;
        this.valueItems = null;
        this.valueItems = value;
        if (this.valueItems == null) {
            this.valueItems = new ArrayList<ItemPropertyTypeValueItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.value)) {
            this.value = ItemUtils.wrap(this.value, this.valueItems, ItemPropertyTypeValueItem.class);
        }
    }

    @OneToMany(orphanRemoval = true, targetEntity = ItemPropertyTypeValueDecimalItem.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "VALUE_DECIMAL_ITEMS_ITEM_PRO_0")
    public List<ItemPropertyTypeValueDecimalItem> getValueDecimalItems() {
        if (this.valueDecimalItems == null) {
            this.valueDecimalItems = new ArrayList<ItemPropertyTypeValueDecimalItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.valueDecimal)) {
            this.valueDecimal = ItemUtils.wrap(this.valueDecimal, this.valueDecimalItems, ItemPropertyTypeValueDecimalItem.class);
        }
        return this.valueDecimalItems;
    }

    public void setValueDecimalItems(List<ItemPropertyTypeValueDecimalItem> value) {
        this.valueDecimal = null;
        this.valueDecimalItems = null;
        this.valueDecimalItems = value;
        if (this.valueDecimalItems == null) {
            this.valueDecimalItems = new ArrayList<ItemPropertyTypeValueDecimalItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.valueDecimal)) {
            this.valueDecimal = ItemUtils.wrap(this.valueDecimal, this.valueDecimalItems, ItemPropertyTypeValueDecimalItem.class);
        }
    }

}
