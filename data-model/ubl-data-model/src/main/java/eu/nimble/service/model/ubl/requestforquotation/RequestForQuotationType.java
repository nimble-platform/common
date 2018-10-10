//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.10 at 10:02:02 AM EET 
//


package eu.nimble.service.model.ubl.requestforquotation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
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
import eu.nimble.service.model.ubl.commonaggregatecomponents.CustomerPartyType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.DeliveryType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.DocumentReferenceType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PaymentMeansType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.PaymentTermsType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.RequestForQuotationLineType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.SupplierPartyType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.TradingTermType;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDate;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsTime;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for RequestForQuotationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RequestForQuotationType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IssueDate"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IssueTime"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}DataMonitoringRequested" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}SellerSupplierParty"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}BuyerCustomerParty"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Delivery" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}RequestForQuotationLine" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PaymentMeans" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PaymentTerms" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TradingTerms" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AdditionalDocumentReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RequestForQuotationType", propOrder = {
    "id",
    "issueDate",
    "issueTime",
    "note",
    "dataMonitoringRequested",
    "sellerSupplierParty",
    "buyerCustomerParty",
    "delivery",
    "requestForQuotationLine",
    "paymentMeans",
    "paymentTerms",
    "tradingTerms",
    "additionalDocumentReference"
})
@Entity(name = "RequestForQuotationType")
@Table(name = "REQUEST_FOR_QUOTATION_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class RequestForQuotationType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected String id;
    @XmlElement(name = "IssueDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar issueDate;
    @XmlElement(name = "IssueTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar issueTime;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<String> note;
    @XmlElement(name = "DataMonitoringRequested", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected Boolean dataMonitoringRequested;
    @XmlElement(name = "SellerSupplierParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected SupplierPartyType sellerSupplierParty;
    @XmlElement(name = "BuyerCustomerParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected CustomerPartyType buyerCustomerParty;
    @XmlElement(name = "Delivery", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected DeliveryType delivery;
    @XmlElement(name = "RequestForQuotationLine", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected List<RequestForQuotationLineType> requestForQuotationLine;
    @XmlElement(name = "PaymentMeans", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected PaymentMeansType paymentMeans;
    @XmlElement(name = "PaymentTerms", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected PaymentTermsType paymentTerms;
    @XmlElement(name = "TradingTerms", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<TradingTermType> tradingTerms;
    @XmlElement(name = "AdditionalDocumentReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<DocumentReferenceType> additionalDocumentReference;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;
    protected transient List<RequestForQuotationTypeNoteItem> noteItems;

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
     * Gets the value of the issueDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Transient
    public XMLGregorianCalendar getIssueDate() {
        return issueDate;
    }

    /**
     * Sets the value of the issueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssueDate(XMLGregorianCalendar value) {
        this.issueDate = value;
    }

    /**
     * Gets the value of the issueTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    @Transient
    public XMLGregorianCalendar getIssueTime() {
        return issueTime;
    }

    /**
     * Sets the value of the issueTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setIssueTime(XMLGregorianCalendar value) {
        this.issueTime = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the note property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNote().add(newItem);
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
    public List<String> getNote() {
        if (note == null) {
            note = new ArrayList<String>();
        }
        return this.note;
    }

    /**
     * 
     * 
     */
    public void setNote(List<String> note) {
        this.note = note;
    }

    /**
     * Gets the value of the dataMonitoringRequested property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    @Basic
    @Column(name = "DATA_MONITORING_REQUESTED")
    public Boolean isDataMonitoringRequested() {
        return dataMonitoringRequested;
    }

    /**
     * Sets the value of the dataMonitoringRequested property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDataMonitoringRequested(Boolean value) {
        this.dataMonitoringRequested = value;
    }

    /**
     * Gets the value of the sellerSupplierParty property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierPartyType }
     *     
     */
    @ManyToOne(targetEntity = SupplierPartyType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "SELLER_SUPPLIER_PARTY_REQUES_0")
    public SupplierPartyType getSellerSupplierParty() {
        return sellerSupplierParty;
    }

    /**
     * Sets the value of the sellerSupplierParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierPartyType }
     *     
     */
    public void setSellerSupplierParty(SupplierPartyType value) {
        this.sellerSupplierParty = value;
    }

    /**
     * Gets the value of the buyerCustomerParty property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerPartyType }
     *     
     */
    @ManyToOne(targetEntity = CustomerPartyType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "BUYER_CUSTOMER_PARTY_REQUEST_0")
    public CustomerPartyType getBuyerCustomerParty() {
        return buyerCustomerParty;
    }

    /**
     * Sets the value of the buyerCustomerParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerPartyType }
     *     
     */
    public void setBuyerCustomerParty(CustomerPartyType value) {
        this.buyerCustomerParty = value;
    }

    /**
     * Gets the value of the delivery property.
     * 
     * @return
     *     possible object is
     *     {@link DeliveryType }
     *     
     */
    @ManyToOne(targetEntity = DeliveryType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "DELIVERY_REQUEST_FOR_QUOTATI_0")
    public DeliveryType getDelivery() {
        return delivery;
    }

    /**
     * Sets the value of the delivery property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeliveryType }
     *     
     */
    public void setDelivery(DeliveryType value) {
        this.delivery = value;
    }

    /**
     * Gets the value of the requestForQuotationLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requestForQuotationLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequestForQuotationLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RequestForQuotationLineType }
     * 
     * 
     */
    @OneToMany(targetEntity = RequestForQuotationLineType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "REQUEST_FOR_QUOTATION_LINE_R_0")
    public List<RequestForQuotationLineType> getRequestForQuotationLine() {
        if (requestForQuotationLine == null) {
            requestForQuotationLine = new ArrayList<RequestForQuotationLineType>();
        }
        return this.requestForQuotationLine;
    }

    /**
     * 
     * 
     */
    public void setRequestForQuotationLine(List<RequestForQuotationLineType> requestForQuotationLine) {
        this.requestForQuotationLine = requestForQuotationLine;
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
        CascadeType.ALL
    })
    @JoinColumn(name = "PAYMENT_MEANS_REQUEST_FOR_QU_0")
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
        CascadeType.ALL
    })
    @JoinColumn(name = "PAYMENT_TERMS_REQUEST_FOR_QU_0")
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
    @OneToMany(targetEntity = TradingTermType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "TRADING_TERMS_REQUEST_FOR_QU_0")
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
     * Gets the value of the additionalDocumentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the additionalDocumentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdditionalDocumentReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    @OneToMany(targetEntity = DocumentReferenceType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "ADDITIONAL_DOCUMENT_REFERENC_7")
    public List<DocumentReferenceType> getAdditionalDocumentReference() {
        if (additionalDocumentReference == null) {
            additionalDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.additionalDocumentReference;
    }

    /**
     * 
     * 
     */
    public void setAdditionalDocumentReference(List<DocumentReferenceType> additionalDocumentReference) {
        this.additionalDocumentReference = additionalDocumentReference;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final RequestForQuotationType that = ((RequestForQuotationType) object);
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
            XMLGregorianCalendar lhsIssueDate;
            lhsIssueDate = this.getIssueDate();
            XMLGregorianCalendar rhsIssueDate;
            rhsIssueDate = that.getIssueDate();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "issueDate", lhsIssueDate), LocatorUtils.property(thatLocator, "issueDate", rhsIssueDate), lhsIssueDate, rhsIssueDate)) {
                return false;
            }
        }
        {
            XMLGregorianCalendar lhsIssueTime;
            lhsIssueTime = this.getIssueTime();
            XMLGregorianCalendar rhsIssueTime;
            rhsIssueTime = that.getIssueTime();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "issueTime", lhsIssueTime), LocatorUtils.property(thatLocator, "issueTime", rhsIssueTime), lhsIssueTime, rhsIssueTime)) {
                return false;
            }
        }
        {
            List<String> lhsNote;
            lhsNote = (((this.note!= null)&&(!this.note.isEmpty()))?this.getNote():null);
            List<String> rhsNote;
            rhsNote = (((that.note!= null)&&(!that.note.isEmpty()))?that.getNote():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "note", lhsNote), LocatorUtils.property(thatLocator, "note", rhsNote), lhsNote, rhsNote)) {
                return false;
            }
        }
        {
            Boolean lhsDataMonitoringRequested;
            lhsDataMonitoringRequested = this.isDataMonitoringRequested();
            Boolean rhsDataMonitoringRequested;
            rhsDataMonitoringRequested = that.isDataMonitoringRequested();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "dataMonitoringRequested", lhsDataMonitoringRequested), LocatorUtils.property(thatLocator, "dataMonitoringRequested", rhsDataMonitoringRequested), lhsDataMonitoringRequested, rhsDataMonitoringRequested)) {
                return false;
            }
        }
        {
            SupplierPartyType lhsSellerSupplierParty;
            lhsSellerSupplierParty = this.getSellerSupplierParty();
            SupplierPartyType rhsSellerSupplierParty;
            rhsSellerSupplierParty = that.getSellerSupplierParty();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "sellerSupplierParty", lhsSellerSupplierParty), LocatorUtils.property(thatLocator, "sellerSupplierParty", rhsSellerSupplierParty), lhsSellerSupplierParty, rhsSellerSupplierParty)) {
                return false;
            }
        }
        {
            CustomerPartyType lhsBuyerCustomerParty;
            lhsBuyerCustomerParty = this.getBuyerCustomerParty();
            CustomerPartyType rhsBuyerCustomerParty;
            rhsBuyerCustomerParty = that.getBuyerCustomerParty();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "buyerCustomerParty", lhsBuyerCustomerParty), LocatorUtils.property(thatLocator, "buyerCustomerParty", rhsBuyerCustomerParty), lhsBuyerCustomerParty, rhsBuyerCustomerParty)) {
                return false;
            }
        }
        {
            DeliveryType lhsDelivery;
            lhsDelivery = this.getDelivery();
            DeliveryType rhsDelivery;
            rhsDelivery = that.getDelivery();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "delivery", lhsDelivery), LocatorUtils.property(thatLocator, "delivery", rhsDelivery), lhsDelivery, rhsDelivery)) {
                return false;
            }
        }
        {
            List<RequestForQuotationLineType> lhsRequestForQuotationLine;
            lhsRequestForQuotationLine = (((this.requestForQuotationLine!= null)&&(!this.requestForQuotationLine.isEmpty()))?this.getRequestForQuotationLine():null);
            List<RequestForQuotationLineType> rhsRequestForQuotationLine;
            rhsRequestForQuotationLine = (((that.requestForQuotationLine!= null)&&(!that.requestForQuotationLine.isEmpty()))?that.getRequestForQuotationLine():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "requestForQuotationLine", lhsRequestForQuotationLine), LocatorUtils.property(thatLocator, "requestForQuotationLine", rhsRequestForQuotationLine), lhsRequestForQuotationLine, rhsRequestForQuotationLine)) {
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
            List<DocumentReferenceType> lhsAdditionalDocumentReference;
            lhsAdditionalDocumentReference = (((this.additionalDocumentReference!= null)&&(!this.additionalDocumentReference.isEmpty()))?this.getAdditionalDocumentReference():null);
            List<DocumentReferenceType> rhsAdditionalDocumentReference;
            rhsAdditionalDocumentReference = (((that.additionalDocumentReference!= null)&&(!that.additionalDocumentReference.isEmpty()))?that.getAdditionalDocumentReference():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "additionalDocumentReference", lhsAdditionalDocumentReference), LocatorUtils.property(thatLocator, "additionalDocumentReference", rhsAdditionalDocumentReference), lhsAdditionalDocumentReference, rhsAdditionalDocumentReference)) {
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
    @Column(name = "ISSUE_DATE_ITEM")
    @Temporal(TemporalType.DATE)
    public Date getIssueDateItem() {
        return XmlAdapterUtils.unmarshall(XMLGregorianCalendarAsDate.class, this.getIssueDate());
    }

    public void setIssueDateItem(Date target) {
        setIssueDate(XmlAdapterUtils.marshall(XMLGregorianCalendarAsDate.class, target));
    }

    @Basic
    @Column(name = "ISSUE_TIME_ITEM")
    @Temporal(TemporalType.TIME)
    public Date getIssueTimeItem() {
        return XmlAdapterUtils.unmarshall(XMLGregorianCalendarAsTime.class, this.getIssueTime());
    }

    public void setIssueTimeItem(Date target) {
        setIssueTime(XmlAdapterUtils.marshall(XMLGregorianCalendarAsTime.class, target));
    }

    @OneToMany(targetEntity = RequestForQuotationTypeNoteItem.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "NOTE_ITEMS_REQUEST_FOR_QUOTA_0")
    public List<RequestForQuotationTypeNoteItem> getNoteItems() {
        if (this.noteItems == null) {
            this.noteItems = new ArrayList<RequestForQuotationTypeNoteItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.note)) {
            this.note = ItemUtils.wrap(this.note, this.noteItems, RequestForQuotationTypeNoteItem.class);
        }
        return this.noteItems;
    }

    public void setNoteItems(List<RequestForQuotationTypeNoteItem> value) {
        this.note = null;
        this.noteItems = null;
        this.noteItems = value;
        if (this.noteItems == null) {
            this.noteItems = new ArrayList<RequestForQuotationTypeNoteItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.note)) {
            this.note = ItemUtils.wrap(this.note, this.noteItems, RequestForQuotationTypeNoteItem.class);
        }
    }

}
