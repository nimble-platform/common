package eu.nimble.utility;

import eu.nimble.service.model.ubl.catalogue.CatalogueType;
import eu.nimble.service.model.ubl.commonaggregatecomponents.*;
import eu.nimble.service.model.ubl.commonbasiccomponents.BinaryObjectType;
import eu.nimble.service.model.ubl.commonbasiccomponents.CodeType;
import eu.nimble.service.model.ubl.commonbasiccomponents.QuantityType;
import eu.nimble.service.model.ubl.commonbasiccomponents.TextType;

import java.util.*;
import java.util.stream.Collectors;

public class UblUtil {

    public enum LanguageID {
        ENGLISH("en"),
        SPANISH("es"),
        GERMAN("de"),
        TURKISH("tr"),
        ITALIAN("it");

        private final String id;

        LanguageID(final String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return id;
        }

    }

    public static String getText(Collection<TextType> textType, String languageID) {
        return textType.stream().filter(tt -> tt.getLanguageID().equals(languageID)).map(TextType::getValue).findFirst().orElse(null);
    }

    public static String getText(Collection<TextType> textType) {
        return textType.stream().filter(tt -> !tt.getValue().isEmpty()).map(TextType::getValue).findFirst().orElse(null);
    }

    public static String getName(Collection<PartyNameType> partyNameTypes, String languageID) {
        List<TextType> textTypes = partyNameTypes.stream().map(PartyNameType::getName).collect(Collectors.toList());
        return getText(textTypes, languageID);
    }

    public static String getName(Collection<PartyNameType> partyNameTypes) {
        List<TextType> textTypes = partyNameTypes.stream().map(PartyNameType::getName).collect(Collectors.toList());
        return getText(textTypes);
    }

    public static String getId(Collection<PartyIdentificationType> partyIdentifications) {
        String partyId = partyIdentifications.stream().map(PartyIdentificationType::getID).findFirst().orElse(null);
        return partyId;
    }

    /**
     * This method will return the party name (legal name) of a company, preference will be given to the default language ID : ENGLISH
     *
     * @param partyType
     * @return
     */
    public static String getName(PartyType partyType) {
        String name = getName(partyType.getPartyName(), LanguageID.ENGLISH.toString());
        if (name == null) {
            name = getName(partyType.getPartyName());
        }
        return name;
    }

    /**
     * Retrieves the identifier of provider party of a catalogue
     * @param catalogue
     * @return
     */
    public static String getCatalogueProviderPartyId(CatalogueType catalogue) {
        return catalogue.getProviderParty().getPartyIdentification().stream().map(PartyIdentificationType::getID).findFirst().orElse(null);
    }

    public static List<BinaryObjectType> getBinaryObjectsFrom(CatalogueLineType catalogueLine) {
        List<BinaryObjectType> binaryObjects = new ArrayList<>();
        binaryObjects.addAll(getBinaryObjectsFrom(catalogueLine.getGoodsItem().getItem()));
        return binaryObjects;
    }

    public static List<BinaryObjectType> getBinaryObjectsFrom(ItemType item) {
        List<BinaryObjectType> binaryObjects = new ArrayList<>();
        if (item.getProductImage() != null) {
            binaryObjects.addAll(item.getProductImage());
        }
        if (item.getItemSpecificationDocumentReference() != null) {
            List<BinaryObjectType> objects = item.getItemSpecificationDocumentReference().stream()
                    .filter(docRef -> docRef.getAttachment().getEmbeddedDocumentBinaryObject() != null)
                    .map(docRef -> docRef.getAttachment().getEmbeddedDocumentBinaryObject())
                    .collect(Collectors.toList());
            binaryObjects.addAll(objects);
        }
        if(item.getAdditionalItemProperty() != null){
            item.getAdditionalItemProperty().stream()
                    .filter(itemProperty -> itemProperty.getValueBinary() != null && itemProperty.getValueBinary().size() > 0)
                    .forEach(itemProperty -> binaryObjects.addAll(itemProperty.getValueBinary()));
        }
        if(item.getCertificate() != null){
            // collect document references
            List<DocumentReferenceType> documentReferenceTypes = new ArrayList<>();
            item.getCertificate().stream()
                    .filter(certificateType -> certificateType.getDocumentReference() != null && certificateType.getDocumentReference().size() > 0)
                    .forEach(certificateType -> documentReferenceTypes.addAll(certificateType.getDocumentReference()));
            // collect binary objects
            List<BinaryObjectType> objects = documentReferenceTypes.stream()
                    .filter(documentReferenceType -> documentReferenceType.getAttachment() != null && documentReferenceType.getAttachment().getEmbeddedDocumentBinaryObject() != null)
                    .map(documentReferenceType -> documentReferenceType.getAttachment().getEmbeddedDocumentBinaryObject())
                    .collect(Collectors.toList());
            binaryObjects.addAll(objects);
        }
        return binaryObjects;
    }

    public static List<BinaryObjectType> getBinaryObjectsFrom(DemandType demand) {
        List<BinaryObjectType> binaryObjects = new ArrayList<>();
        if (demand.getAdditionalDocumentReference() != null) {
            binaryObjects.add(demand.getAdditionalDocumentReference().getAttachment().getEmbeddedDocumentBinaryObject());
        }
        if(demand.getCertificate() != null){
            // collect document references from demand certificates
            List<DocumentReferenceType> documentReferenceTypes = new ArrayList<>();
            demand.getCertificate()
                    .stream().filter(certificateType -> certificateType.getDocumentReference() != null && certificateType.getDocumentReference().size() > 0)
                    .map(CertificateType::getDocumentReference)
            .forEach(documentReferenceTypes::addAll);
            // collect binary objects from the document references
            binaryObjects.addAll(documentReferenceTypes.stream().map(documentReferenceType -> documentReferenceType.getAttachment().getEmbeddedDocumentBinaryObject()).collect(Collectors.toList()));
        }
        return binaryObjects;
    }

    /*
    Methods to copy entity fields from a source to a target entity
     */

    public static void copy(DemandType source, DemandType target) {
        if (source.getTitle() == null) {
            target.setTitle(null);
        } else {
            if (target.getTitle() == null) {
                target.setTitle(new ArrayList<>());
            }
            target.getTitle().clear();
            for (TextType title : source.getTitle()) {
                TextType newTitle = new TextType();
                copy(title, newTitle);
                target.getTitle().add(newTitle);
            }
        }

        if (source.getDescription() == null) {
            target.setDescription(null);
        } else {
            if (target.getDescription() == null) {
                target.setDescription(new ArrayList<>());
            }
            target.getDescription().clear();
            for (TextType description : source.getDescription()) {
                TextType newDescription = new TextType();
                copy(description, newDescription);
                target.getDescription().add(newDescription);
            }
        }

        target.setDueDateItem(null);
        target.setDueDate(source.getDueDate());

        if (source.getItemClassificationCode() == null) {
            target.setItemClassificationCode(null);
        } else {
            if (target.getItemClassificationCode() == null) {
                target.setItemClassificationCode(new ArrayList<>());
            }
            target.getItemClassificationCode().clear();
            for (CodeType itemClassificationCode : source.getItemClassificationCode()) {
                CodeType newCode = new CodeType();
                copy(itemClassificationCode, newCode);
                target.getItemClassificationCode().add(newCode);
            }
        }

        if (source.getBuyerCountry() == null) {
            target.setBuyerCountry(null);
        } else {
            if (target.getBuyerCountry() == null) {
                target.setBuyerCountry(new CodeType());
            }
            copy(source.getBuyerCountry(), target.getBuyerCountry());
        }

        if (source.getDeliveryCountry() == null) {
            target.setDeliveryCountry(null);
        } else {
            if (target.getDeliveryCountry() == null) {
                target.setDeliveryCountry(new CodeType());
            }
            copy(source.getDeliveryCountry(), target.getDeliveryCountry());
        }

        if (source.getAdditionalDocumentReference() == null) {
            target.setAdditionalDocumentReference(null);
        } else {
            if (target.getAdditionalDocumentReference() == null) {
                target.setAdditionalDocumentReference(new DocumentReferenceType());
            }
            copy(source.getAdditionalDocumentReference(), target.getAdditionalDocumentReference());
        }

        if (source.getCertificate() == null) {
            target.setCertificate(null);
        } else {
            if (target.getCertificate() == null) {
                target.setCertificate(new ArrayList<>());
            }
            List<CertificateType> certificateTypes = new ArrayList<>();
            source.getCertificate().forEach(certificateType -> {
                CertificateType targetCertificate = new CertificateType();
                copy(certificateType,targetCertificate);
                certificateTypes.add(targetCertificate);
            });

            target.setCertificate(certificateTypes);
        }

        if (source.getImage() == null) {
            target.setImage(null);
        } else {
            if (target.getImage() == null) {
                target.setImage(new BinaryObjectType());
            }
            copy(source.getImage(), target.getImage());
        }
    }

    public static void copy(TextType source, TextType target) {
        target.setLanguageID(source.getLanguageID());
        target.setValue(source.getValue());
    }

    public static void copy(CodeType source, CodeType target) {
        target.setListID(source.getListID());
        target.setName(source.getName());
        target.setURI(source.getURI());
        target.setValue(source.getValue());
    }

    public static void copy(CountryType source, CountryType target) {
        if (source.getIdentificationCode() == null) {
            target.setIdentificationCode(null);
        } else {
            if (target.getIdentificationCode() == null) {
                target.setIdentificationCode(new CodeType());
            }
            copy(source.getIdentificationCode(), target.getIdentificationCode());
        }

        if (source.getName() == null) {
            target.setName(null);
        } else {
            if (target.getName() == null) {
                target.setName(new TextType());
            }
            copy(source.getName(), target.getName());
        }
    }

    public static void copy(DocumentReferenceType source, DocumentReferenceType target) {
        target.setID(source.getID());
        target.setDocumentType(source.getDocumentType());
        target.setUUID(source.getUUID());

        if (source.getAttachment() == null) {
            target.setAttachment(new AttachmentType());
        } else {
            if (target.getAttachment() == null) {
                target.setAttachment(new AttachmentType());
            }
            copy(source.getAttachment(), target.getAttachment());
        }

        if (source.getValidityPeriod() == null) {
            target.setValidityPeriod(null);
        } else {
            if (target.getValidityPeriod() == null) {
                target.setValidityPeriod(new PeriodType());
            }
            copy(source.getValidityPeriod(), target.getValidityPeriod());
        }
    }

    public static void copy(CertificateType source, CertificateType target) {
        target.setCertificateType(source.getCertificateType());
        target.setRemarks(source.getRemarks());
        target.setID(source.getID());

        if (source.getCertificateTypeCode() == null) {
            target.setCertificateTypeCode(new CodeType());
        } else {
            if (target.getCertificateTypeCode() == null) {
                target.setCertificateTypeCode(new CodeType());
            }
            copy(source.getCertificateTypeCode(), target.getCertificateTypeCode());
        }
        if (source.getCountry() == null) {
            target.setCountry(new ArrayList<>());
        } else {
            if (target.getCountry() == null) {
                target.setCountry(new ArrayList<>());
            }
            List<CountryType> countryTypes = new ArrayList<>();
            source.getCountry().forEach(countryType -> {
                CountryType targetCountry = new CountryType();
                copy(countryType,targetCountry);
                countryTypes.add(targetCountry);
            });

            target.setCountry(countryTypes);
        }
        if (source.getDocumentReference() == null) {
            target.setDocumentReference(new ArrayList<>());
        } else {
            if (target.getDocumentReference() == null) {
                target.setDocumentReference(new ArrayList<>());
            }
            List<DocumentReferenceType> documentReferenceTypes = new ArrayList<>();
            source.getDocumentReference().forEach(documentReferenceType -> {
                DocumentReferenceType targetDocumentReference = new DocumentReferenceType();
                copy(documentReferenceType,targetDocumentReference);
                documentReferenceTypes.add(targetDocumentReference);
            });

            target.setDocumentReference(documentReferenceTypes);
        }
    }

    public static void copy(AttachmentType source, AttachmentType target) {
        if (source.getEmbeddedDocumentBinaryObject() == null) {
            target.setEmbeddedDocumentBinaryObject(null);
        } else {
            if (target.getEmbeddedDocumentBinaryObject() == null) {
                target.setEmbeddedDocumentBinaryObject(new BinaryObjectType());
            }
            copy(source.getEmbeddedDocumentBinaryObject(), target.getEmbeddedDocumentBinaryObject());
        }

        if (source.getExternalReference() == null) {
            target.setExternalReference(null);
        } else {
            if (target.getExternalReference() == null) {
                target.setExternalReference(new ExternalReferenceType());
            }
            copy(source.getExternalReference(), target.getExternalReference());
        }
    }

    public static void copy(ExternalReferenceType source, ExternalReferenceType target) {
        target.setURI(source.getURI());
    }

    public static void copy(BinaryObjectType source, BinaryObjectType target) {
        target.setFileName(source.getFileName());
        target.setLanguageID(source.getLanguageID());
        target.setMimeCode(source.getMimeCode());
        target.setUri(source.getUri());
        target.setValue(source.getValue());
    }

    public static void copy(PeriodType source, PeriodType target) {
        if (target == null) {
            target = new PeriodType();
        }

        if (source.getDurationMeasure() == null) {
            target.setDurationMeasure(null);
        } else {
            if (target.getDurationMeasure() == null) {
                target.setDurationMeasure(new QuantityType());
            }
            copy(source.getDurationMeasure(), target.getDurationMeasure());
        }

        target.setStartDateItem(null);
        target.setStartDate(source.getStartDate());
        target.setStartTimeItem(null);
        target.setStartTime(source.getStartTime());
        target.setEndDateItem(null);
        target.setEndDate(source.getEndDate());
        target.setEndTimeItem(null);
        target.setEndTime(source.getEndTime());
    }

    public static void copy(QuantityType source, QuantityType target) {
        target.setUnitCode(source.getUnitCode());
        target.setUnitCodeListID(source.getUnitCodeListID());
        target.setValue(source.getValue());
    }
}
