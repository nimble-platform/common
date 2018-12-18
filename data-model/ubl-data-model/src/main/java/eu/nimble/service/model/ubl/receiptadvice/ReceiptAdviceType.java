//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.11.02 at 03:35:43 PM MSK 
//


package eu.nimble.service.model.ubl.receiptadvice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import eu.nimble.service.model.ubl.commonaggregatecomponents.DocumentReferenceType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.ReceiptLineType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.SupplierPartyType;
import org.hibernate.annotations.Cascade;
import org.jvnet.hyperjaxb3.item.ItemUtils;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XMLGregorianCalendarAsDate;
import org.jvnet.hyperjaxb3.xml.bind.annotation.adapters.XmlAdapterUtils;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for ReceiptAdviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReceiptAdviceType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IssueDate"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DespatchDocumentReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryCustomerParty"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DespatchSupplierParty"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ReceiptLine" maxOccurs="unbounded"/&gt;
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
@XmlType(name = "ReceiptAdviceType", propOrder = {
    "id",
    "issueDate",
    "note",
    "despatchDocumentReference",
    "deliveryCustomerParty",
    "despatchSupplierParty",
    "receiptLine",
    "additionalDocumentReference"
})
@Entity(name = "ReceiptAdviceType")
@Table(name = "RECEIPT_ADVICE_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class ReceiptAdviceType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String id;
    @XmlElement(name = "IssueDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar issueDate;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<String> note;
    @XmlElement(name = "DespatchDocumentReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<DocumentReferenceType> despatchDocumentReference;
    @XmlElement(name = "DeliveryCustomerParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected CustomerPartyType deliveryCustomerParty;
    @XmlElement(name = "DespatchSupplierParty", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected SupplierPartyType despatchSupplierParty;
    @XmlElement(name = "ReceiptLine", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2", required = true)
    protected List<ReceiptLineType> receiptLine;
    @XmlElement(name = "AdditionalDocumentReference", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2")
    protected List<DocumentReferenceType> additionalDocumentReference;
    @XmlAttribute(name = "Hjid")
    protected Long hjid;
    protected transient List<ReceiptAdviceTypeNoteItem> noteItems;

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
     * Gets the value of the despatchDocumentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the despatchDocumentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDespatchDocumentReference().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    @OneToMany(orphanRemoval = true,targetEntity = DocumentReferenceType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "DESPATCH_DOCUMENT_REFERENCE__0")
    public List<DocumentReferenceType> getDespatchDocumentReference() {
        if (despatchDocumentReference == null) {
            despatchDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.despatchDocumentReference;
    }

    /**
     * 
     * 
     */
    public void setDespatchDocumentReference(List<DocumentReferenceType> despatchDocumentReference) {
        this.despatchDocumentReference = despatchDocumentReference;
    }

    /**
     * Gets the value of the deliveryCustomerParty property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerPartyType }
     *     
     */
    @ManyToOne(targetEntity = CustomerPartyType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "DELIVERY_CUSTOMER_PARTY_RECE_0")
    public CustomerPartyType getDeliveryCustomerParty() {
        return deliveryCustomerParty;
    }

    /**
     * Sets the value of the deliveryCustomerParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerPartyType }
     *     
     */
    public void setDeliveryCustomerParty(CustomerPartyType value) {
        this.deliveryCustomerParty = value;
    }

    /**
     * Gets the value of the despatchSupplierParty property.
     * 
     * @return
     *     possible object is
     *     {@link SupplierPartyType }
     *     
     */
    @ManyToOne(targetEntity = SupplierPartyType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "DESPATCH_SUPPLIER_PARTY_RECE_0")
    public SupplierPartyType getDespatchSupplierParty() {
        return despatchSupplierParty;
    }

    /**
     * Sets the value of the despatchSupplierParty property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplierPartyType }
     *     
     */
    public void setDespatchSupplierParty(SupplierPartyType value) {
        this.despatchSupplierParty = value;
    }

    /**
     * Gets the value of the receiptLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the receiptLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getReceiptLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReceiptLineType }
     * 
     * 
     */
    @OneToMany(targetEntity = ReceiptLineType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "RECEIPT_LINE_RECEIPT_ADVICE__0")
    public List<ReceiptLineType> getReceiptLine() {
        if (receiptLine == null) {
            receiptLine = new ArrayList<ReceiptLineType>();
        }
        return this.receiptLine;
    }

    /**
     * 
     * 
     */
    public void setReceiptLine(List<ReceiptLineType> receiptLine) {
        this.receiptLine = receiptLine;
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
    @OneToMany(orphanRemoval = true,targetEntity = DocumentReferenceType.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "ADDITIONAL_DOCUMENT_REFERENC_2")
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
        final ReceiptAdviceType that = ((ReceiptAdviceType) object);
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
            List<String> lhsNote;
            lhsNote = (((this.note!= null)&&(!this.note.isEmpty()))?this.getNote():null);
            List<String> rhsNote;
            rhsNote = (((that.note!= null)&&(!that.note.isEmpty()))?that.getNote():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "note", lhsNote), LocatorUtils.property(thatLocator, "note", rhsNote), lhsNote, rhsNote)) {
                return false;
            }
        }
        {
            List<DocumentReferenceType> lhsDespatchDocumentReference;
            lhsDespatchDocumentReference = (((this.despatchDocumentReference!= null)&&(!this.despatchDocumentReference.isEmpty()))?this.getDespatchDocumentReference():null);
            List<DocumentReferenceType> rhsDespatchDocumentReference;
            rhsDespatchDocumentReference = (((that.despatchDocumentReference!= null)&&(!that.despatchDocumentReference.isEmpty()))?that.getDespatchDocumentReference():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "despatchDocumentReference", lhsDespatchDocumentReference), LocatorUtils.property(thatLocator, "despatchDocumentReference", rhsDespatchDocumentReference), lhsDespatchDocumentReference, rhsDespatchDocumentReference)) {
                return false;
            }
        }
        {
            CustomerPartyType lhsDeliveryCustomerParty;
            lhsDeliveryCustomerParty = this.getDeliveryCustomerParty();
            CustomerPartyType rhsDeliveryCustomerParty;
            rhsDeliveryCustomerParty = that.getDeliveryCustomerParty();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "deliveryCustomerParty", lhsDeliveryCustomerParty), LocatorUtils.property(thatLocator, "deliveryCustomerParty", rhsDeliveryCustomerParty), lhsDeliveryCustomerParty, rhsDeliveryCustomerParty)) {
                return false;
            }
        }
        {
            SupplierPartyType lhsDespatchSupplierParty;
            lhsDespatchSupplierParty = this.getDespatchSupplierParty();
            SupplierPartyType rhsDespatchSupplierParty;
            rhsDespatchSupplierParty = that.getDespatchSupplierParty();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "despatchSupplierParty", lhsDespatchSupplierParty), LocatorUtils.property(thatLocator, "despatchSupplierParty", rhsDespatchSupplierParty), lhsDespatchSupplierParty, rhsDespatchSupplierParty)) {
                return false;
            }
        }
        {
            List<ReceiptLineType> lhsReceiptLine;
            lhsReceiptLine = (((this.receiptLine!= null)&&(!this.receiptLine.isEmpty()))?this.getReceiptLine():null);
            List<ReceiptLineType> rhsReceiptLine;
            rhsReceiptLine = (((that.receiptLine!= null)&&(!that.receiptLine.isEmpty()))?that.getReceiptLine():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "receiptLine", lhsReceiptLine), LocatorUtils.property(thatLocator, "receiptLine", rhsReceiptLine), lhsReceiptLine, rhsReceiptLine)) {
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

    @OneToMany(orphanRemoval = true, targetEntity = ReceiptAdviceTypeNoteItem.class, cascade = {
        javax.persistence.CascadeType.ALL
    })
    @JoinColumn(name = "NOTE_ITEMS_RECEIPT_ADVICE_TY_0")
    public List<ReceiptAdviceTypeNoteItem> getNoteItems() {
        if (this.noteItems == null) {
            this.noteItems = new ArrayList<ReceiptAdviceTypeNoteItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.note)) {
            this.note = ItemUtils.wrap(this.note, this.noteItems, ReceiptAdviceTypeNoteItem.class);
        }
        return this.noteItems;
    }

    public void setNoteItems(List<ReceiptAdviceTypeNoteItem> value) {
        this.note = null;
        this.noteItems = null;
        this.noteItems = value;
        if (this.noteItems == null) {
            this.noteItems = new ArrayList<ReceiptAdviceTypeNoteItem>();
        }
        if (ItemUtils.shouldBeWrapped(this.note)) {
            this.note = ItemUtils.wrap(this.note, this.noteItems, ReceiptAdviceTypeNoteItem.class);
        }
    }

}
