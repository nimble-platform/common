//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.10.22 at 05:33:25 PM MSK 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.nimble.service.model.ubl.commonbasiccomponents.QuantityType;
import org.hibernate.annotations.Cascade;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for LineItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineItemType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Quantity" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DataMonitoringRequested"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryTerms" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Delivery" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Price" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Item"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}WarrantyValidityPeriod" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}LineReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PaymentMeans"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PaymentTerms"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TradingTerms" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Clause" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineItemType", propOrder = {
    "id",
    "quantity",
    "dataMonitoringRequested",
    "deliveryTerms",
    "delivery",
    "price",
    "item",
    "warrantyValidityPeriod",
    "lineReference",
    "paymentMeans",
    "paymentTerms",
    "tradingTerms",
    "clause"
})
@Entity(name = "LineItemType")
@Table(name = "LINE_ITEM_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class LineItemType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String id;
    @XmlElement(name = "Quantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected QuantityType quantity;
    @XmlElement(name = "DataMonitoringRequested", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected boolean dataMonitoringRequested;
    @XmlElement(name = "DeliveryTerms")
    protected DeliveryTermsType deliveryTerms;
    @XmlElement(name = "Delivery")
    protected List<DeliveryType> delivery;
    @XmlElement(name = "Price")
    protected PriceType price;
    @XmlElement(name = "Item", required = true)
    protected ItemType item;
    @XmlElement(name = "WarrantyValidityPeriod")
    protected PeriodType warrantyValidityPeriod;
    @XmlElement(name = "LineReference")
    protected List<LineReferenceType> lineReference;
    @XmlElement(name = "PaymentMeans", required = true)
    protected PaymentMeansType paymentMeans;
    @XmlElement(name = "PaymentTerms", required = true)
    protected PaymentTermsType paymentTerms;
    @XmlElement(name = "TradingTerms")
    protected List<TradingTermType> tradingTerms;
    @XmlElement(name = "Clause")
    protected List<ClauseType> clause;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;

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
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    @ManyToOne(targetEntity = QuantityType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "QUANTITY_LINE_ITEM_TYPE_HJID")
    public QuantityType getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setQuantity(QuantityType value) {
        this.quantity = value;
    }

    /**
     * Gets the value of the dataMonitoringRequested property.
     * 
     */
    @Basic
    @Column(name = "DATA_MONITORING_REQUESTED")
    public boolean isDataMonitoringRequested() {
        return dataMonitoringRequested;
    }

    /**
     * Sets the value of the dataMonitoringRequested property.
     * 
     */
    public void setDataMonitoringRequested(boolean value) {
        this.dataMonitoringRequested = value;
    }

    /**
     * Gets the value of the deliveryTerms property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryTermsType }
     *     
     */
    @ManyToOne(targetEntity = DeliveryTermsType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "DELIVERY_TERMS_LINE_ITEM_TYP_0")
    public DeliveryTermsType getDeliveryTerms() {
        return deliveryTerms;
    }

    /**
     * Sets the value of the deliveryTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryTermsType }
     *     
     */
    public void setDeliveryTerms(DeliveryTermsType value) {
        this.deliveryTerms = value;
    }

    /**
     * Gets the value of the delivery property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the delivery property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDelivery().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeliveryType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = DeliveryType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "DELIVERY_LINE_ITEM_TYPE_HJID")
    public List<DeliveryType> getDelivery() {
        if (delivery == null) {
            delivery = new ArrayList<DeliveryType>();
        }
        return this.delivery;
    }

    /**
     * 
     * 
     */
    public void setDelivery(List<DeliveryType> delivery) {
        this.delivery = delivery;
    }

    /**
     * Gets the value of the price property.
     * 
     * @return
     *     possible object is
     *     {@link PriceType }
     *     
     */
    @ManyToOne(targetEntity = PriceType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "PRICE_LINE_ITEM_TYPE_HJID")
    public PriceType getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceType }
     *     
     */
    public void setPrice(PriceType value) {
        this.price = value;
    }

    /**
     * Gets the value of the item property.
     * 
     * @return
     *     possible object is
     *     {@link ItemType }
     *     
     */
    @ManyToOne(targetEntity = ItemType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "ITEM_LINE_ITEM_TYPE_HJID")
    public ItemType getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemType }
     *     
     */
    public void setItem(ItemType value) {
        this.item = value;
    }

    /**
     * Gets the value of the warrantyValidityPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link PeriodType }
     *     
     */
    @ManyToOne(targetEntity = PeriodType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "WARRANTY_VALIDITY_PERIOD_LIN_0")
    public PeriodType getWarrantyValidityPeriod() {
        return warrantyValidityPeriod;
    }

    /**
     * Sets the value of the warrantyValidityPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link PeriodType }
     *     
     */
    public void setWarrantyValidityPeriod(PeriodType value) {
        this.warrantyValidityPeriod = value;
    }

    /**
     * Gets the value of the lineReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LineReferenceType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = LineReferenceType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "LINE_REFERENCE_LINE_ITEM_TYP_0")
    public List<LineReferenceType> getLineReference() {
        if (lineReference == null) {
            lineReference = new ArrayList<LineReferenceType>();
        }
        return this.lineReference;
    }

    /**
     * 
     * 
     */
    public void setLineReference(List<LineReferenceType> lineReference) {
        this.lineReference = lineReference;
    }

    /**
     * Gets the value of the paymentMeans property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentMeansType }
     *     
     */
    @ManyToOne(targetEntity = PaymentMeansType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "PAYMENT_MEANS_LINE_ITEM_TYPE_0")
    public PaymentMeansType getPaymentMeans() {
        return paymentMeans;
    }

    /**
     * Sets the value of the paymentMeans property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentMeansType }
     *     
     */
    public void setPaymentMeans(PaymentMeansType value) {
        this.paymentMeans = value;
    }

    /**
     * Gets the value of the paymentTerms property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTermsType }
     *     
     */
    @ManyToOne(targetEntity = PaymentTermsType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "PAYMENT_TERMS_LINE_ITEM_TYPE_0")
    public PaymentTermsType getPaymentTerms() {
        return paymentTerms;
    }

    /**
     * Sets the value of the paymentTerms property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTermsType }
     *     
     */
    public void setPaymentTerms(PaymentTermsType value) {
        this.paymentTerms = value;
    }

    /**
     * Gets the value of the tradingTerms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tradingTerms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTradingTerms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TradingTermType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = TradingTermType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "TRADING_TERMS_LINE_ITEM_TYPE_0")
    public List<TradingTermType> getTradingTerms() {
        if (tradingTerms == null) {
            tradingTerms = new ArrayList<TradingTermType>();
        }
        return this.tradingTerms;
    }

    /**
     * 
     * 
     */
    public void setTradingTerms(List<TradingTermType> tradingTerms) {
        this.tradingTerms = tradingTerms;
    }

    /**
     * Gets the value of the clause property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clause property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClause().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ClauseType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = ClauseType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "CLAUSE_LINE_ITEM_TYPE_HJID")
    public List<ClauseType> getClause() {
        if (clause == null) {
            clause = new ArrayList<ClauseType>();
        }
        return this.clause;
    }

    /**
     * 
     * 
     */
    public void setClause(List<ClauseType> clause) {
        this.clause = clause;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final LineItemType that = ((LineItemType) object);
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
            QuantityType lhsQuantity;
            lhsQuantity = this.getQuantity();
            QuantityType rhsQuantity;
            rhsQuantity = that.getQuantity();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "quantity", lhsQuantity), LocatorUtils.property(thatLocator, "quantity", rhsQuantity), lhsQuantity, rhsQuantity)) {
                return false;
            }
        }
        {
            boolean lhsDataMonitoringRequested;
            lhsDataMonitoringRequested = this.isDataMonitoringRequested();
            boolean rhsDataMonitoringRequested;
            rhsDataMonitoringRequested = that.isDataMonitoringRequested();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dataMonitoringRequested", lhsDataMonitoringRequested), LocatorUtils.property(thatLocator, "dataMonitoringRequested", rhsDataMonitoringRequested), lhsDataMonitoringRequested, rhsDataMonitoringRequested)) {
                return false;
            }
        }
        {
            DeliveryTermsType lhsDeliveryTerms;
            lhsDeliveryTerms = this.getDeliveryTerms();
            DeliveryTermsType rhsDeliveryTerms;
            rhsDeliveryTerms = that.getDeliveryTerms();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "deliveryTerms", lhsDeliveryTerms), LocatorUtils.property(thatLocator, "deliveryTerms", rhsDeliveryTerms), lhsDeliveryTerms, rhsDeliveryTerms)) {
                return false;
            }
        }
        {
            List<DeliveryType> lhsDelivery;
            lhsDelivery = (((this.delivery!= null)&&(!this.delivery.isEmpty()))?this.getDelivery():null);
            List<DeliveryType> rhsDelivery;
            rhsDelivery = (((that.delivery!= null)&&(!that.delivery.isEmpty()))?that.getDelivery():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "delivery", lhsDelivery), LocatorUtils.property(thatLocator, "delivery", rhsDelivery), lhsDelivery, rhsDelivery)) {
                return false;
            }
        }
        {
            PriceType lhsPrice;
            lhsPrice = this.getPrice();
            PriceType rhsPrice;
            rhsPrice = that.getPrice();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "price", lhsPrice), LocatorUtils.property(thatLocator, "price", rhsPrice), lhsPrice, rhsPrice)) {
                return false;
            }
        }
        {
            ItemType lhsItem;
            lhsItem = this.getItem();
            ItemType rhsItem;
            rhsItem = that.getItem();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "item", lhsItem), LocatorUtils.property(thatLocator, "item", rhsItem), lhsItem, rhsItem)) {
                return false;
            }
        }
        {
            PeriodType lhsWarrantyValidityPeriod;
            lhsWarrantyValidityPeriod = this.getWarrantyValidityPeriod();
            PeriodType rhsWarrantyValidityPeriod;
            rhsWarrantyValidityPeriod = that.getWarrantyValidityPeriod();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "warrantyValidityPeriod", lhsWarrantyValidityPeriod), LocatorUtils.property(thatLocator, "warrantyValidityPeriod", rhsWarrantyValidityPeriod), lhsWarrantyValidityPeriod, rhsWarrantyValidityPeriod)) {
                return false;
            }
        }
        {
            List<LineReferenceType> lhsLineReference;
            lhsLineReference = (((this.lineReference!= null)&&(!this.lineReference.isEmpty()))?this.getLineReference():null);
            List<LineReferenceType> rhsLineReference;
            rhsLineReference = (((that.lineReference!= null)&&(!that.lineReference.isEmpty()))?that.getLineReference():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "lineReference", lhsLineReference), LocatorUtils.property(thatLocator, "lineReference", rhsLineReference), lhsLineReference, rhsLineReference)) {
                return false;
            }
        }
        {
            PaymentMeansType lhsPaymentMeans;
            lhsPaymentMeans = this.getPaymentMeans();
            PaymentMeansType rhsPaymentMeans;
            rhsPaymentMeans = that.getPaymentMeans();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "paymentMeans", lhsPaymentMeans), LocatorUtils.property(thatLocator, "paymentMeans", rhsPaymentMeans), lhsPaymentMeans, rhsPaymentMeans)) {
                return false;
            }
        }
        {
            PaymentTermsType lhsPaymentTerms;
            lhsPaymentTerms = this.getPaymentTerms();
            PaymentTermsType rhsPaymentTerms;
            rhsPaymentTerms = that.getPaymentTerms();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "paymentTerms", lhsPaymentTerms), LocatorUtils.property(thatLocator, "paymentTerms", rhsPaymentTerms), lhsPaymentTerms, rhsPaymentTerms)) {
                return false;
            }
        }
        {
            List<TradingTermType> lhsTradingTerms;
            lhsTradingTerms = (((this.tradingTerms!= null)&&(!this.tradingTerms.isEmpty()))?this.getTradingTerms():null);
            List<TradingTermType> rhsTradingTerms;
            rhsTradingTerms = (((that.tradingTerms!= null)&&(!that.tradingTerms.isEmpty()))?that.getTradingTerms():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "tradingTerms", lhsTradingTerms), LocatorUtils.property(thatLocator, "tradingTerms", rhsTradingTerms), lhsTradingTerms, rhsTradingTerms)) {
                return false;
            }
        }
        {
            List<ClauseType> lhsClause;
            lhsClause = (((this.clause!= null)&&(!this.clause.isEmpty()))?this.getClause():null);
            List<ClauseType> rhsClause;
            rhsClause = (((that.clause!= null)&&(!that.clause.isEmpty()))?that.getClause():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "clause", lhsClause), LocatorUtils.property(thatLocator, "clause", rhsClause), lhsClause, rhsClause)) {
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
