package eu.nimble.utility.persistence.repository;

import eu.nimble.service.model.ubl.commonaggregatecomponents.MetadataType;
import eu.nimble.utility.exception.NimbleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Utility providing methods on the generic {@link MetadataType} of managed entities
 */
public class MetadataUtility {
    private static Logger logger = LoggerFactory.getLogger(MetadataUtility.class);


    public static MetadataType createEntityMetadata(String userId, List<String> companyIds) throws NimbleException {
        MetadataType metadata = new MetadataType();
        metadata.setOwnerUser(userId);
        metadata.setOwnerCompany(companyIds);

        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        try {
            XMLGregorianCalendar currentDateTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            metadata.setCreationDate(currentDateTime);
            metadata.setModificationDate(currentDateTime);
        } catch (DatatypeConfigurationException e) {
            String msg = "Could not create date for metadata";
            logger.warn(msg, e);
            throw new NimbleException(msg, e);
        }
        return metadata;
    }

    public static void updateEntityMetadata(MetadataType metadata) throws NimbleException {
        GregorianCalendar c = new GregorianCalendar();
        c.setTimeInMillis(System.currentTimeMillis());
        try {
            XMLGregorianCalendar currentDateTime = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            metadata.setModificationDate(currentDateTime);
        } catch (DatatypeConfigurationException e) {
            String msg = "Could not update date for metadata";
            logger.warn(msg, e);
            throw new NimbleException(msg, e);
        }
    }

    public static boolean isOwnerCompany(String companyId, MetadataType metadata) {
        if (metadata.getOwnerCompany() != null) {
            return metadata.getOwnerCompany().contains(companyId);
        } else {
            return false;
        }
    }
}
