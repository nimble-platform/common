// This class is added to support line-level whitelist functionality.
// It mirrors CatalogueTypePermittedPartyIDItem but is scoped to individual catalogue lines.

package eu.nimble.service.model.ubl.catalogue;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import org.jvnet.hyperjaxb3.item.Item;

@Entity(name = "CatalogueLineTypePermittedPartyIDItem")
@Table(name = "CATALOGUE_LINE_TYPE_PERMITTED_0")
@Inheritance(strategy = InheritanceType.JOINED)
public class CatalogueLineTypePermittedPartyIDItem
    implements Serializable, Item<String>
{

    private final static long serialVersionUID = 1L;
    protected String item;
    protected Long hjid;

    /**
     * Gets the value of the item property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    @Basic
    @Column(name = "ITEM", length = 255)
    public String getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setItem(String value) {
        this.item = value;
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
