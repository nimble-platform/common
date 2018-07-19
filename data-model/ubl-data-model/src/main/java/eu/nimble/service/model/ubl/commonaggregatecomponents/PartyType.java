//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.07.19 at 05:21:26 PM EET 
//


package eu.nimble.service.model.ubl.commonaggregatecomponents;

import eu.nimble.service.model.ubl.commonbasiccomponents.CodeType;
import jaxb.adapter.BigDecimalXmlAdapter;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for PartyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Name"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}WebsiteURI" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}IndustryClassificationCode" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ExternalAward" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PpapCompatibilityLevel" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PreferredItemClassificationCode" maxOccurs="unbounded"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PostalAddress" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PartyTaxScheme" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Contact" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Person" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Certificate" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}QualityIndicator" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PaymentMeans" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DeliveryTerms" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TradingTerms" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}PpapDocumentReference" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyType", propOrder = {
    "id",
    "name",
    "websiteURI",
    "industryClassificationCode",
    "externalAward",
    "ppapCompatibilityLevel",
    "preferredItemClassificationCode",
    "postalAddress",
    "partyTaxScheme",
    "contact",
    "person",
    "certificate",
    "qualityIndicator",
    "paymentMeans",
    "deliveryTerms",
    "tradingTerms",
    "ppapDocumentReference"
})
@Entity(name = "PartyType")
@Table(name = "PARTY_TYPE")
@Inheritance(strategy = InheritanceType.JOINED)
public class PartyType
    implements Serializable, Equals
{

    private final static long serialVersionUID = 1L;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String id;
    @XmlElement(name = "Name", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected String name;
    @XmlElement(name = "WebsiteURI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected String websiteURI;
    @XmlElement(name = "IndustryClassificationCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CodeType industryClassificationCode;
    @XmlElement(name = "ExternalAward", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected String externalAward;
    @XmlElement(name = "PpapCompatibilityLevel", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", type = String.class)
    @XmlJavaTypeAdapter(BigDecimalXmlAdapter.class)
    @XmlSchemaType(name = "decimal")
    protected BigDecimal ppapCompatibilityLevel;
    @XmlElement(name = "PreferredItemClassificationCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected List<CodeType> preferredItemClassificationCode;
    @XmlElement(name = "PostalAddress")
    protected AddressType postalAddress;
    @XmlElement(name = "PartyTaxScheme")
    protected List<PartyTaxSchemeType> partyTaxScheme;
    @XmlElement(name = "Contact")
    protected ContactType contact;
    @XmlElement(name = "Person")
    protected List<PersonType> person;
    @XmlElement(name = "Certificate")
    protected List<CertificateType> certificate;
    @XmlElement(name = "QualityIndicator")
    protected List<QualityIndicatorType> qualityIndicator;
    @XmlElement(name = "PaymentMeans")
    protected List<PaymentMeansType> paymentMeans;
    @XmlElement(name = "DeliveryTerms")
    protected List<DeliveryTermsType> deliveryTerms;
    @XmlElement(name = "TradingTerms")
    protected List<TradingTermType> tradingTerms;
    @XmlElement(name = "PpapDocumentReference")
    protected List<DocumentReferenceType> ppapDocumentReference;
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
     * Gets the value of the websiteURI property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "WEBSITE_URI", length = 255)
    public String getWebsiteURI() {
        return websiteURI;
    }

    /**
     * Sets the value of the websiteURI property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebsiteURI(String value) {
        this.websiteURI = value;
    }

    /**
     * Gets the value of the industryClassificationCode property.
     * 
     * @return
     *     possible object is
     *     {@link CodeType }
     *     
     */
    @ManyToOne(targetEntity = CodeType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "INDUSTRY_CLASSIFICATION_CODE_0")
    public CodeType getIndustryClassificationCode() {
        return industryClassificationCode;
    }

    /**
     * Sets the value of the industryClassificationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CodeType }
     *     
     */
    public void setIndustryClassificationCode(CodeType value) {
        this.industryClassificationCode = value;
    }

    /**
     * Gets the value of the externalAward property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "EXTERNAL_AWARD", length = 255)
    public String getExternalAward() {
        return externalAward;
    }

    /**
     * Sets the value of the externalAward property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExternalAward(String value) {
        this.externalAward = value;
    }

    /**
     * Gets the value of the ppapCompatibilityLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    @Basic
    @Column(name = "PPAP_COMPATIBILITY_LEVEL", precision = 20, scale = 10)
    public BigDecimal getPpapCompatibilityLevel() {
        return ppapCompatibilityLevel;
    }

    /**
     * Sets the value of the ppapCompatibilityLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPpapCompatibilityLevel(BigDecimal value) {
        this.ppapCompatibilityLevel = value;
    }

    /**
     * Gets the value of the preferredItemClassificationCode property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the preferredItemClassificationCode property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPreferredItemClassificationCode().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CodeType }
     * 
     * 
     */
    @OneToMany(targetEntity = CodeType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "PREFERRED_ITEM_CLASSIFICATIO_1")
    public List<CodeType> getPreferredItemClassificationCode() {
        if (preferredItemClassificationCode == null) {
            preferredItemClassificationCode = new ArrayList<CodeType>();
        }
        return this.preferredItemClassificationCode;
    }

    /**
     * 
     * 
     */
    public void setPreferredItemClassificationCode(List<CodeType> preferredItemClassificationCode) {
        this.preferredItemClassificationCode = preferredItemClassificationCode;
    }

    /**
     * Gets the value of the postalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    @ManyToOne(targetEntity = AddressType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "POSTAL_ADDRESS_PARTY_TYPE_HJ_0")
    public AddressType getPostalAddress() {
        return postalAddress;
    }

    /**
     * Sets the value of the postalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setPostalAddress(AddressType value) {
        this.postalAddress = value;
    }

    /**
     * Gets the value of the partyTaxScheme property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partyTaxScheme property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartyTaxScheme().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PartyTaxSchemeType }
     * 
     * 
     */
    @OneToMany(targetEntity = PartyTaxSchemeType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "PARTY_TAX_SCHEME_PARTY_TYPE__0")
    public List<PartyTaxSchemeType> getPartyTaxScheme() {
        if (partyTaxScheme == null) {
            partyTaxScheme = new ArrayList<PartyTaxSchemeType>();
        }
        return this.partyTaxScheme;
    }

    /**
     * 
     * 
     */
    public void setPartyTaxScheme(List<PartyTaxSchemeType> partyTaxScheme) {
        this.partyTaxScheme = partyTaxScheme;
    }

    /**
     * Gets the value of the contact property.
     * 
     * @return
     *     possible object is
     *     {@link ContactType }
     *     
     */
    @ManyToOne(targetEntity = ContactType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CONTACT_PARTY_TYPE_HJID")
    public ContactType getContact() {
        return contact;
    }

    /**
     * Sets the value of the contact property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContactType }
     *     
     */
    public void setContact(ContactType value) {
        this.contact = value;
    }

    /**
     * Gets the value of the person property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the person property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPerson().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PersonType }
     * 
     * 
     */
    @OneToMany(targetEntity = PersonType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "PERSON_PARTY_TYPE_HJID")
    public List<PersonType> getPerson() {
        if (person == null) {
            person = new ArrayList<PersonType>();
        }
        return this.person;
    }

    /**
     * 
     * 
     */
    public void setPerson(List<PersonType> person) {
        this.person = person;
    }

    /**
     * Gets the value of the certificate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the certificate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCertificate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CertificateType }
     * 
     * 
     */
    @OneToMany(targetEntity = CertificateType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "CERTIFICATE_PARTY_TYPE_HJID")
    public List<CertificateType> getCertificate() {
        if (certificate == null) {
            certificate = new ArrayList<CertificateType>();
        }
        return this.certificate;
    }

    /**
     * 
     * 
     */
    public void setCertificate(List<CertificateType> certificate) {
        this.certificate = certificate;
    }

    /**
     * Gets the value of the qualityIndicator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the qualityIndicator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQualityIndicator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QualityIndicatorType }
     * 
     * 
     */
    @OneToMany(targetEntity = QualityIndicatorType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "QUALITY_INDICATOR_PARTY_TYPE_0")
    public List<QualityIndicatorType> getQualityIndicator() {
        if (qualityIndicator == null) {
            qualityIndicator = new ArrayList<QualityIndicatorType>();
        }
        return this.qualityIndicator;
    }

    /**
     * 
     * 
     */
    public void setQualityIndicator(List<QualityIndicatorType> qualityIndicator) {
        this.qualityIndicator = qualityIndicator;
    }

    /**
     * Gets the value of the paymentMeans property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentMeans property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentMeans().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentMeansType }
     * 
     * 
     */
    @OneToMany(targetEntity = PaymentMeansType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "PAYMENT_MEANS_PARTY_TYPE_HJID")
    public List<PaymentMeansType> getPaymentMeans() {
        if (paymentMeans == null) {
            paymentMeans = new ArrayList<PaymentMeansType>();
        }
        return this.paymentMeans;
    }

    /**
     * 
     * 
     */
    public void setPaymentMeans(List<PaymentMeansType> paymentMeans) {
        this.paymentMeans = paymentMeans;
    }

    /**
     * Gets the value of the deliveryTerms property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deliveryTerms property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeliveryTerms().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeliveryTermsType }
     * 
     * 
     */
    @OneToMany(targetEntity = DeliveryTermsType.class, cascade = {
        CascadeType.ALL
    })
    @JoinColumn(name = "DELIVERY_TERMS_PARTY_TYPE_HJ_0")
    public List<DeliveryTermsType> getDeliveryTerms() {
        if (deliveryTerms == null) {
            deliveryTerms = new ArrayList<DeliveryTermsType>();
        }
        return this.deliveryTerms;
    }

    /**
     * 
     * 
     */
    public void setDeliveryTerms(List<DeliveryTermsType> deliveryTerms) {
        this.deliveryTerms = deliveryTerms;
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
    @JoinColumn(name = "TRADING_TERMS_PARTY_TYPE_HJID")
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
     * Gets the value of the ppapDocumentReference property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the ppapDocumentReference property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPpapDocumentReference().add(newItem);
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
    @JoinColumn(name = "PPAP_DOCUMENT_REFERENCE_PART_0")
    public List<DocumentReferenceType> getPpapDocumentReference() {
        if (ppapDocumentReference == null) {
            ppapDocumentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.ppapDocumentReference;
    }

    /**
     * 
     * 
     */
    public void setPpapDocumentReference(List<DocumentReferenceType> ppapDocumentReference) {
        this.ppapDocumentReference = ppapDocumentReference;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if ((object == null)||(this.getClass()!= object.getClass())) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final PartyType that = ((PartyType) object);
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
            String lhsWebsiteURI;
            lhsWebsiteURI = this.getWebsiteURI();
            String rhsWebsiteURI;
            rhsWebsiteURI = that.getWebsiteURI();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "websiteURI", lhsWebsiteURI), LocatorUtils.property(thatLocator, "websiteURI", rhsWebsiteURI), lhsWebsiteURI, rhsWebsiteURI)) {
                return false;
            }
        }
        {
            CodeType lhsIndustryClassificationCode;
            lhsIndustryClassificationCode = this.getIndustryClassificationCode();
            CodeType rhsIndustryClassificationCode;
            rhsIndustryClassificationCode = that.getIndustryClassificationCode();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "industryClassificationCode", lhsIndustryClassificationCode), LocatorUtils.property(thatLocator, "industryClassificationCode", rhsIndustryClassificationCode), lhsIndustryClassificationCode, rhsIndustryClassificationCode)) {
                return false;
            }
        }
        {
            String lhsExternalAward;
            lhsExternalAward = this.getExternalAward();
            String rhsExternalAward;
            rhsExternalAward = that.getExternalAward();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "externalAward", lhsExternalAward), LocatorUtils.property(thatLocator, "externalAward", rhsExternalAward), lhsExternalAward, rhsExternalAward)) {
                return false;
            }
        }
        {
            BigDecimal lhsPpapCompatibilityLevel;
            lhsPpapCompatibilityLevel = this.getPpapCompatibilityLevel();
            BigDecimal rhsPpapCompatibilityLevel;
            rhsPpapCompatibilityLevel = that.getPpapCompatibilityLevel();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ppapCompatibilityLevel", lhsPpapCompatibilityLevel), LocatorUtils.property(thatLocator, "ppapCompatibilityLevel", rhsPpapCompatibilityLevel), lhsPpapCompatibilityLevel, rhsPpapCompatibilityLevel)) {
                return false;
            }
        }
        {
            List<CodeType> lhsPreferredItemClassificationCode;
            lhsPreferredItemClassificationCode = (((this.preferredItemClassificationCode!= null)&&(!this.preferredItemClassificationCode.isEmpty()))?this.getPreferredItemClassificationCode():null);
            List<CodeType> rhsPreferredItemClassificationCode;
            rhsPreferredItemClassificationCode = (((that.preferredItemClassificationCode!= null)&&(!that.preferredItemClassificationCode.isEmpty()))?that.getPreferredItemClassificationCode():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "preferredItemClassificationCode", lhsPreferredItemClassificationCode), LocatorUtils.property(thatLocator, "preferredItemClassificationCode", rhsPreferredItemClassificationCode), lhsPreferredItemClassificationCode, rhsPreferredItemClassificationCode)) {
                return false;
            }
        }
        {
            AddressType lhsPostalAddress;
            lhsPostalAddress = this.getPostalAddress();
            AddressType rhsPostalAddress;
            rhsPostalAddress = that.getPostalAddress();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "postalAddress", lhsPostalAddress), LocatorUtils.property(thatLocator, "postalAddress", rhsPostalAddress), lhsPostalAddress, rhsPostalAddress)) {
                return false;
            }
        }
        {
            List<PartyTaxSchemeType> lhsPartyTaxScheme;
            lhsPartyTaxScheme = (((this.partyTaxScheme!= null)&&(!this.partyTaxScheme.isEmpty()))?this.getPartyTaxScheme():null);
            List<PartyTaxSchemeType> rhsPartyTaxScheme;
            rhsPartyTaxScheme = (((that.partyTaxScheme!= null)&&(!that.partyTaxScheme.isEmpty()))?that.getPartyTaxScheme():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "partyTaxScheme", lhsPartyTaxScheme), LocatorUtils.property(thatLocator, "partyTaxScheme", rhsPartyTaxScheme), lhsPartyTaxScheme, rhsPartyTaxScheme)) {
                return false;
            }
        }
        {
            ContactType lhsContact;
            lhsContact = this.getContact();
            ContactType rhsContact;
            rhsContact = that.getContact();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "contact", lhsContact), LocatorUtils.property(thatLocator, "contact", rhsContact), lhsContact, rhsContact)) {
                return false;
            }
        }
        {
            List<PersonType> lhsPerson;
            lhsPerson = (((this.person!= null)&&(!this.person.isEmpty()))?this.getPerson():null);
            List<PersonType> rhsPerson;
            rhsPerson = (((that.person!= null)&&(!that.person.isEmpty()))?that.getPerson():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "person", lhsPerson), LocatorUtils.property(thatLocator, "person", rhsPerson), lhsPerson, rhsPerson)) {
                return false;
            }
        }
        {
            List<CertificateType> lhsCertificate;
            lhsCertificate = (((this.certificate!= null)&&(!this.certificate.isEmpty()))?this.getCertificate():null);
            List<CertificateType> rhsCertificate;
            rhsCertificate = (((that.certificate!= null)&&(!that.certificate.isEmpty()))?that.getCertificate():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "certificate", lhsCertificate), LocatorUtils.property(thatLocator, "certificate", rhsCertificate), lhsCertificate, rhsCertificate)) {
                return false;
            }
        }
        {
            List<QualityIndicatorType> lhsQualityIndicator;
            lhsQualityIndicator = (((this.qualityIndicator!= null)&&(!this.qualityIndicator.isEmpty()))?this.getQualityIndicator():null);
            List<QualityIndicatorType> rhsQualityIndicator;
            rhsQualityIndicator = (((that.qualityIndicator!= null)&&(!that.qualityIndicator.isEmpty()))?that.getQualityIndicator():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "qualityIndicator", lhsQualityIndicator), LocatorUtils.property(thatLocator, "qualityIndicator", rhsQualityIndicator), lhsQualityIndicator, rhsQualityIndicator)) {
                return false;
            }
        }
        {
            List<PaymentMeansType> lhsPaymentMeans;
            lhsPaymentMeans = (((this.paymentMeans!= null)&&(!this.paymentMeans.isEmpty()))?this.getPaymentMeans():null);
            List<PaymentMeansType> rhsPaymentMeans;
            rhsPaymentMeans = (((that.paymentMeans!= null)&&(!that.paymentMeans.isEmpty()))?that.getPaymentMeans():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "paymentMeans", lhsPaymentMeans), LocatorUtils.property(thatLocator, "paymentMeans", rhsPaymentMeans), lhsPaymentMeans, rhsPaymentMeans)) {
                return false;
            }
        }
        {
            List<DeliveryTermsType> lhsDeliveryTerms;
            lhsDeliveryTerms = (((this.deliveryTerms!= null)&&(!this.deliveryTerms.isEmpty()))?this.getDeliveryTerms():null);
            List<DeliveryTermsType> rhsDeliveryTerms;
            rhsDeliveryTerms = (((that.deliveryTerms!= null)&&(!that.deliveryTerms.isEmpty()))?that.getDeliveryTerms():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "deliveryTerms", lhsDeliveryTerms), LocatorUtils.property(thatLocator, "deliveryTerms", rhsDeliveryTerms), lhsDeliveryTerms, rhsDeliveryTerms)) {
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
            List<DocumentReferenceType> lhsPpapDocumentReference;
            lhsPpapDocumentReference = (((this.ppapDocumentReference!= null)&&(!this.ppapDocumentReference.isEmpty()))?this.getPpapDocumentReference():null);
            List<DocumentReferenceType> rhsPpapDocumentReference;
            rhsPpapDocumentReference = (((that.ppapDocumentReference!= null)&&(!that.ppapDocumentReference.isEmpty()))?that.getPpapDocumentReference():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "ppapDocumentReference", lhsPpapDocumentReference), LocatorUtils.property(thatLocator, "ppapDocumentReference", rhsPpapDocumentReference), lhsPpapDocumentReference, rhsPpapDocumentReference)) {
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
