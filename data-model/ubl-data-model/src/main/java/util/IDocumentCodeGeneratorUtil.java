package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDocumentCodeGeneratorUtil {

    final static private String regex_bp_documents = "public class (ReceiptAdviceType|QuotationType|ItemInformationResponseType|TransportExecutionPlanType|PpapResponseType|OrderResponseSimpleType|OrderType|PpapRequestType|RequestForQuotationType|DespatchAdviceType|TransportExecutionPlanRequestType|ItemInformationRequestType)\\s+implements Serializable, Equals";

    final static private String regex_order_note_items = "protected transient List<OrderTypeNoteItem> noteItems;";
    final static private String regex_order_response_simple_note_items = "protected transient List<OrderResponseSimpleTypeNoteItem> noteItems;";
    final static private String regex_request_for_quotation_note_items = "protected transient List<RequestForQuotationTypeNoteItem> noteItems;";
    final static private String regex_quotation_note_items = "protected transient List<QuotationTypeNoteItem> noteItems;";
    final static private String regex_ppap_request_document_type_items = "protected transient List<PpapRequestTypeDocumentTypeItem> documentTypeItems;";
    final static private String regex_ppap_response_note_items = "protected transient List<PpapResponseTypeNoteItem> noteItems;";
    final static private String regex_despatch_advice_note_items = "protected transient List<DespatchAdviceTypeNoteItem> noteItems;";
    final static private String regex_receipt_advice_note_items = "protected transient List<ReceiptAdviceTypeNoteItem> noteItems;";
    final static private String regex_transport_execution_plan_requet_note_items = "protected transient List<TransportExecutionPlanRequestTypeNoteItem> noteItems;";
    final static private String regex_transport_execution_plan_note_items = "protected transient List<TransportExecutionPlanTypeNoteItem> noteItems;";
    final static private String regex_item_information_request_note_items = "protected transient List<ItemInformationRequestTypeNoteItem> noteItems;";
    final static private String regex_item_information_response_note_items = "protected transient List<ItemInformationResponseTypeNoteItem> noteItems;";

    final static private String regex_import_serializable = "import java.io.Serializable;";
    final static private String imports_IDocument = "import eu.nimble.service.model.ubl.commonaggregatecomponents.*;\n" +
            "import eu.nimble.service.model.ubl.document.IDocument;";

    public static void extendDocumentsWithIDocumentImplementations(GenerateSourceUtil.FileUpdate fileUpdate) {
        try {
            String fileText = fileUpdate.getContent();
            Pattern p = Pattern.compile(regex_bp_documents, Pattern.DOTALL);
            Matcher m = p.matcher(fileText);

            // find (request/response) Bp Documents
            if (m.find()) {
                String group = m.group();
                // implement IDocument
                String newGroup = group + ", IDocument";
                fileText = fileText.replace(group, newGroup);

                fileUpdate.setFileUpdated(true);
                fileUpdate.setContent(fileText);

                if (group.contains("OrderType")) {
                    p = Pattern.compile(regex_order_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_order_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return sellerSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return buyerCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        List<ItemType> itemTypes = new ArrayList<>();\n" +
                                "        for (OrderLineType orderLineType : orderLine) {\n" +
                                "            itemTypes.add(orderLineType.getLineItem().getItem());\n" +
                                "        }\n" +
                                "        return itemTypes;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("PpapRequestType")) {
                    p = Pattern.compile(regex_ppap_request_document_type_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_ppap_request_document_type_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return sellerSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return buyerCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        List<ItemType> itemTypes = new ArrayList<>();\n" +
                                "        itemTypes.add(lineItem.getItem());\n" +
                                "        return itemTypes;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("RequestForQuotationType")) {
                    p = Pattern.compile(regex_request_for_quotation_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_request_for_quotation_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return sellerSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return buyerCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        List<ItemType> itemTypes = new ArrayList<>();\n" +
                                "        for (RequestForQuotationLineType requestForQuotationLineType : requestForQuotationLine) {\n" +
                                "            itemTypes.add(requestForQuotationLineType.getLineItem().getItem());\n" +
                                "        }\n" +
                                "        return itemTypes;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("DespatchAdviceType")) {
                    p = Pattern.compile(regex_despatch_advice_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_despatch_advice_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return despatchSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return deliveryCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return despatchSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return despatchSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return deliveryCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return deliveryCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        List<ItemType> itemTypes = new ArrayList<>();\n" +
                                "        for (DespatchLineType despatchLineType : despatchLine) {\n" +
                                "            itemTypes.add(despatchLineType.getItem());\n" +
                                "        }\n" +
                                "        return itemTypes;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("TransportExecutionPlanRequestType")) {
                    p = Pattern.compile(regex_transport_execution_plan_requet_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_transport_execution_plan_requet_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return transportServiceProviderParty;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return transportUserParty;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return transportServiceProviderParty.getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return transportServiceProviderParty.getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return transportUserParty.getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return transportUserParty.getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        List<ItemType> itemTypes = new ArrayList<>();\n" +
                                "        itemTypes.add(mainTransportationService);\n" +
                                "        return itemTypes;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("ItemInformationRequestType")) {
                    p = Pattern.compile(regex_item_information_request_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_item_information_request_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return sellerSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return buyerCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        List<ItemType> itemTypes = new ArrayList<>();\n" +
                                "        for (ItemInformationRequestLineType itemInformationRequestLineType : itemInformationRequestLine) {\n" +
                                "            for (SalesItemType salesItemType : itemInformationRequestLineType.getSalesItem()) {\n" +
                                "                itemTypes.add(salesItemType.getItem());\n" +
                                "            }\n" +
                                "        }\n" +
                                "        return itemTypes;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("OrderResponseSimpleType")) {
                    p = Pattern.compile(regex_order_response_simple_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_order_response_simple_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return orderReference.getDocumentReference().getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return sellerSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return buyerCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return Boolean.toString(acceptedIndicator);\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("PpapResponseType")) {
                    p = Pattern.compile(regex_ppap_response_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_ppap_response_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return ppapDocumentReference.getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return sellerSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return buyerCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return Boolean.toString(acceptedIndicator);\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("TransportExecutionPlanType")) {
                    p = Pattern.compile(regex_transport_execution_plan_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_transport_execution_plan_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return transportExecutionPlanRequestDocumentReference.getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return transportServiceProviderParty;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return transportUserParty;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return transportServiceProviderParty.getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return transportServiceProviderParty.getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return transportUserParty.getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return transportUserParty.getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return documentStatusCode.getName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("ItemInformationResponseType")) {
                    p = Pattern.compile(regex_item_information_response_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_item_information_response_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return itemInformationRequestDocumentReference.getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return sellerSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return buyerCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "    \n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes(){\n" +
                                "        return item;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("QuotationType")) {
                    p = Pattern.compile(regex_quotation_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        newGroup = regex_quotation_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return requestForQuotationDocumentReference.getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return sellerSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return buyerCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return sellerSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return buyerCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return documentStatusCode.getName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        List<ItemType> itemTypes = new ArrayList<>();\n" +
                                "        for (QuotationLineType quotationLineType : quotationLine) {\n" +
                                "            itemTypes.add(quotationLineType.getLineItem().getItem());\n" +
                                "        }\n" +
                                "        return itemTypes;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                } else if (group.contains("ReceiptAdviceType")) {
                    p = Pattern.compile(regex_receipt_advice_note_items, Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if (m.find()) {
                        group = m.group();
                        // first replace the annotations with full package names
                        newGroup = regex_receipt_advice_note_items + "\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentId() {\n" +
                                "        return id;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getRequestDocumentId() {\n" +
                                "        return despatchDocumentReference.get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getSellerParty() {\n" +
                                "        return despatchSupplierParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public PartyType getBuyerParty() {\n" +
                                "        return deliveryCustomerParty.getParty();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getSellerPartyId() {\n" +
                                "        return despatchSupplierParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getSellerPartyName() {\n" +
                                "        return despatchSupplierParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getBuyerPartyId() {\n" +
                                "        return deliveryCustomerParty.getParty().getPartyIdentification().get(0).getID();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<PartyNameType> getBuyerPartyName() {\n" +
                                "        return deliveryCustomerParty.getParty().getPartyName();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public String getDocumentStatus() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<ItemType> getItemTypes() {\n" +
                                "        List<ItemType> itemTypes = new ArrayList<>();\n" +
                                "        for (ReceiptLineType receiptLineType : receiptLine) {\n" +
                                "            itemTypes.add(receiptLineType.getItem());\n" +
                                "        }\n" +
                                "        return itemTypes;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group, newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }

                p = Pattern.compile(regex_import_serializable, Pattern.DOTALL);
                m = p.matcher(fileText);
                // add import statements
                if (m.find()) {
                    group = m.group();
                    newGroup = regex_import_serializable + "\n" + imports_IDocument;
                    fileText = fileText.replace(group, newGroup);

                    fileUpdate.setFileUpdated(true);
                    fileUpdate.setContent(fileText);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to extend documents with IDocument:", e);
        }
    }

}
