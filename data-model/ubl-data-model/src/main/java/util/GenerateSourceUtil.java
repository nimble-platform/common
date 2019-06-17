package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateSourceUtil {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    final private String regex_party_relations = "@ManyToOne.targetEntity = PartyType.class,.*public PartyType";
    final private String regex_onetomanys_with_orphan_removal = "@Cascade.*DELETE_ORPHAN.*@OneToMany.";
    final private String regex_onetoones_with_orphan_removal = "@Cascade.*DELETE_ORPHAN.*@OneToOne.";
    final private String regex_transientListsWithOrphanRemovals = "@Cascade.*\\s*.*DELETE_ORPHAN\\s*.*\\s+@Transient\\s*.*List.*";
    final private String regex_transientListDefs = "protected transient List.*";
    final private String regex_builtInListWithOrm = "target.*\\s+.*\\s+.*\\s+.*\\s+.*";

    final private String regex_bp_documents = "public class (ReceiptAdviceType|QuotationType|ItemInformationResponseType|TransportExecutionPlanType|PpapResponseType|OrderResponseSimpleType|OrderType|PpapRequestType|RequestForQuotationType|DespatchAdviceType|TransportExecutionPlanRequestType|ItemInformationRequestType)\\s+implements Serializable, Equals";

    final private String regex_order_note_items = "protected transient List<OrderTypeNoteItem> noteItems;";
    final private String regex_order_response_simple_note_items = "protected transient List<OrderResponseSimpleTypeNoteItem> noteItems;";
    final private String regex_request_for_quotation_note_items = "protected transient List<RequestForQuotationTypeNoteItem> noteItems;";
    final private String regex_quotation_note_items = "protected transient List<QuotationTypeNoteItem> noteItems;";
    final private String regex_ppap_request_document_type_items = "protected transient List<PpapRequestTypeDocumentTypeItem> documentTypeItems;";
    final private String regex_ppap_response_note_items = "protected transient List<PpapResponseTypeNoteItem> noteItems;";
    final private String regex_despatch_advice_note_items = "protected transient List<DespatchAdviceTypeNoteItem> noteItems;";
    final private String regex_receipt_advice_note_items = "protected transient List<ReceiptAdviceTypeNoteItem> noteItems;";
    final private String regex_transport_execution_plan_requet_note_items = "protected transient List<TransportExecutionPlanRequestTypeNoteItem> noteItems;";
    final private String regex_transport_execution_plan_note_items = "protected transient List<TransportExecutionPlanTypeNoteItem> noteItems;";
    final private String regex_item_information_request_note_items = "protected transient List<ItemInformationRequestTypeNoteItem> noteItems;";
    final private String regex_item_information_response_note_items = "protected transient List<ItemInformationResponseTypeNoteItem> noteItems;";

    final private String regex_import_serializable = "import java.io.Serializable;";
    final private String imports_IDocument = "import eu.nimble.service.model.ubl.commonaggregatecomponents.*;\n" +
            "import eu.nimble.service.model.ubl.document.IDocument;";

    public static void main(String [] args){
        GenerateSourceUtil generateSourceUtil = new GenerateSourceUtil();
        generateSourceUtil.postProcessORMAnnotations(args[0]);
    }

    public void postProcessORMAnnotations(String path){
        logger.debug("Started to change party cascade types");
        File directory = new File(path);
        searchDirectory(directory);
        logger.debug("Changed party cascade types successfully");
    }

    public void searchDirectory(File directory){
        File[] filesAndDirs = directory.listFiles();
        for(File file : filesAndDirs){
            if(file.isFile()){
                String fileContent = getFileContent(file);
                FileUpdate fileUpdate = new FileUpdate();
                fileUpdate.setContent(fileContent);
                removeRemoveCascadesFromPartyTypes(fileUpdate);
                upgradeDeprecatedOrphanRemovalAnnotations(fileUpdate);
                removeOrphanRemovalFromTransientLists(fileUpdate);
                addOrphanRemovalsToTransientLists(fileUpdate);
                extendDocumentsWithIDocumentImplementations(fileUpdate);

                if(file.getName().contentEquals("TextType.java")) {
                    updateTextTypeValueField(fileUpdate);
                }

                updateFile(file, fileUpdate);
            }
            else {
                searchDirectory(file);
            }
        }
    }

    private void extendDocumentsWithIDocumentImplementations(FileUpdate fileUpdate){
        try {
            String fileText = fileUpdate.getContent();
            Pattern p = Pattern.compile(regex_bp_documents,Pattern.DOTALL);
            Matcher m = p.matcher(fileText);

            // find (request/response) Bp Documents
            if(m.find()){
                String group = m.group();
                // implement IDocument
                String newGroup = group +", IDocument";
                fileText = fileText.replace(group,newGroup);

                fileUpdate.setFileUpdated(true);
                fileUpdate.setContent(fileText);

                if(group.contains("OrderType")){
                    p = Pattern.compile(regex_order_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_order_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return orderLine.get(0).getLineItem().getItem();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("PpapRequestType")){
                    p = Pattern.compile(regex_ppap_request_document_type_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_ppap_request_document_type_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return lineItem.getItem();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("RequestForQuotationType")){
                    p = Pattern.compile(regex_request_for_quotation_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_request_for_quotation_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return requestForQuotationLine.get(0).getLineItem().getItem();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("DespatchAdviceType")){
                    p = Pattern.compile(regex_despatch_advice_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_despatch_advice_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return despatchLine.get(0).getItem();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("TransportExecutionPlanRequestType")){
                    p = Pattern.compile(regex_transport_execution_plan_requet_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_transport_execution_plan_requet_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return mainTransportationService;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("ItemInformationRequestType")){
                    p = Pattern.compile(regex_item_information_request_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_item_information_request_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return itemInformationRequestLine.get(0).getSalesItem().get(0).getItem();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("OrderResponseSimpleType")){
                    p = Pattern.compile(regex_order_response_simple_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_order_response_simple_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("PpapResponseType")){
                    p = Pattern.compile(regex_ppap_response_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_ppap_response_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("TransportExecutionPlanType")){
                    p = Pattern.compile(regex_transport_execution_plan_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_transport_execution_plan_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return null;\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("ItemInformationResponseType")){
                    p = Pattern.compile(regex_item_information_response_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_item_information_response_note_items + "\n" +
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
                                "    public ItemType getItemType(){\n" +
                                "        return item.get(0);\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("QuotationType")){
                    p = Pattern.compile(regex_quotation_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        newGroup = regex_quotation_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return quotationLine.get(0).getLineItem().getItem();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }
                else if(group.contains("ReceiptAdviceType")){
                    p = Pattern.compile(regex_receipt_advice_note_items,Pattern.DOTALL);
                    m = p.matcher(fileText);
                    // implement methods
                    if(m.find()){
                        group = m.group();
                        // first replace the annotations with full package names
                        newGroup = regex_receipt_advice_note_items + "\n" +
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
                                "    public ItemType getItemType() {\n" +
                                "        return receiptLine.get(0).getItem();\n" +
                                "    }\n" +
                                "\n" +
                                "    @Override\n\t@Transient\n" +
                                "    public List<DocumentReferenceType> getAdditionalDocuments() {\n" +
                                "        return additionalDocumentReference;\n" +
                                "    }";
                        fileText = fileText.replace(group,newGroup);

                        fileUpdate.setFileUpdated(true);
                        fileUpdate.setContent(fileText);
                    }
                }

                p = Pattern.compile(regex_import_serializable,Pattern.DOTALL);
                m = p.matcher(fileText);
                // add import statements
                if(m.find()){
                    group = m.group();
                    newGroup = regex_import_serializable + "\n" + imports_IDocument;
                    fileText = fileText.replace(group,newGroup);

                    fileUpdate.setFileUpdated(true);
                    fileUpdate.setContent(fileText);
                }
            }
        }
        catch (Exception e){
            throw new RuntimeException("Failed to extend documents with IDocument:",e);
        }
    }

    private void removeRemoveCascadesFromPartyTypes(FileUpdate fileUpdate) {
        try {
            String fileText = fileUpdate.getContent();
            Pattern p = Pattern.compile(regex_party_relations,Pattern.DOTALL);
            Matcher m = p.matcher(fileText);

            // now try to find at least one match
            if (m.find()){
                String group = m.group();
                // first replace the annotations with full package names
                String newGroup = group.replace("javax.persistence.CascadeType.ALL","javax.persistence.CascadeType.PERSIST,javax.persistence.CascadeType.MERGE,javax.persistence.CascadeType.REFRESH");
                // in case the annotations do not have full package names, the line below has effect
                newGroup = newGroup.replace("CascadeType.ALL","javax.persistence.CascadeType.PERSIST,javax.persistence.CascadeType.MERGE,javax.persistence.CascadeType.REFRESH");
                fileText = fileText.replace(group,newGroup);

                fileUpdate.setFileUpdated(true);
                fileUpdate.setContent(fileText);
            }
        }
        catch (Exception e){
            throw new RuntimeException("Failed to change cascade type of parties",e);
        }
    }

    public void upgradeDeprecatedOrphanRemovalAnnotations(FileUpdate fileUpdate){
        try {
            String fileText = fileUpdate.getContent();
            Pattern p = Pattern.compile(regex_onetomanys_with_orphan_removal,Pattern.DOTALL);
            Pattern p2 = Pattern.compile(regex_onetoones_with_orphan_removal,Pattern.DOTALL);
            Matcher m = p.matcher(fileText);
            Matcher m2 = p2.matcher(fileText);

            if (m.find() || m2.find()){
                fileText = fileText.replaceAll("@Cascade.+\\s+org.hibernate.annotations.CascadeType.DELETE_ORPHAN\\s+.+\\s+@OneToMany.","@OneToMany(orphanRemoval = true,");
                fileText = fileText.replaceAll("@Cascade.+\\s+org.hibernate.annotations.CascadeType.DELETE_ORPHAN\\s+.+\\s+@OneToOne.","@OneToOne(orphanRemoval = true,");

                fileUpdate.setFileUpdated(true);
                fileUpdate.setContent(fileText);
            }
        }
        catch (Exception e){
            throw new RuntimeException("Failed to change cascade type of parties",e);
        }
    }

    private void addOrphanRemovalsToTransientLists(FileUpdate fileUpdate) {
        String fileContent = fileUpdate.getContent();
        // name of transient fields
        List<String> toBeAnnotatedMethods = new ArrayList<>();
        Pattern p = Pattern.compile(regex_transientListDefs);
        Matcher m = p.matcher(fileContent);

        if(m.find()) {
            m.reset();
            while(m.find()) {
                String matchingPart = m.group();
                String fieldName = matchingPart.substring(matchingPart.indexOf('>')+2, matchingPart.length()-1);
                String fieldNameToUpdate = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                toBeAnnotatedMethods.add(fieldNameToUpdate);
            }

            for(String methodName : toBeAnnotatedMethods) {
                Pattern p2 = Pattern.compile(regex_builtInListWithOrm + methodName);
                Matcher m2 = p2.matcher(fileContent);
                m2.find();
                String matchingPart = m2.group();
                String replacement = "orphanRemoval = true, " + matchingPart;
                fileContent = fileContent.replaceFirst(regex_builtInListWithOrm + methodName, replacement);
            }

            fileUpdate.setContent(fileContent);
            fileUpdate.setFileUpdated(true);
        }
    }

    private void removeOrphanRemovalFromTransientLists(FileUpdate fileUpdate) {
        String fileContent = fileUpdate.getContent();
        Pattern p = Pattern.compile(regex_transientListsWithOrphanRemovals);
        Matcher m = p.matcher(fileContent);
        if(m.find()) {
            m.reset();
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String matchingPart = m.group();
                String remainder = matchingPart.substring(matchingPart.indexOf("@Transient"));
                m.appendReplacement(sb, remainder);
            }
            fileUpdate.setContent(m.appendTail(sb).toString());
            fileUpdate.setFileUpdated(true);
        }
    }

    private void updateTextTypeValueField(FileUpdate fileUpdate) {
        String fileContent = fileUpdate.getContent();
        fileContent = fileContent.replace("@Column(name = \"VALUE_\", length = 255)", "@Column(name = \"VALUE_\", columnDefinition = \"TEXT\", length = 255)");
        fileUpdate.setContent(fileContent);
        fileUpdate.setFileUpdated(true);
    }

    private void updateFile(File file, FileUpdate fileUpdate) {
        if(!fileUpdate.isFileUpdated()) {
            return;
        }

        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(fileUpdate.getContent());

            BufferedWriter bwr = new BufferedWriter(new FileWriter(file));

            //write contents of StringBuffer to the file
            bwr.write(stringBuffer.toString());

            //flush the stream
            bwr.flush();

            //close the stream
            bwr.close();

        } catch(IOException e) {
            throw new RuntimeException("Failed to update file", e);
        }
    }

    private String getFileContent(File file) {
        FileInputStream fileStream = null;
        BufferedReader br = null;
        InputStreamReader inputStreamReader = null;
        try {
            StringBuffer text = new StringBuffer();
            fileStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileStream);
            br = new BufferedReader(inputStreamReader);
            for (String line; (line = br.readLine()) != null; )
                text.append(line + System.lineSeparator());

            String fileText = text.toString();
            return fileText;

        } catch (IOException e) {
            throw new RuntimeException("Failed to get file content", e);
        } finally {
            if(fileStream != null){
                try {
                    fileStream.close();
                } catch (IOException e) {
                    logger.warn("Failed to close file stream: ",e);
                }
            }
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    logger.warn("Failed to close buffered reader: ",e);
                }
            }
            if(inputStreamReader != null){
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    logger.warn("Failed to close input stream reader: ",e);
                }
            }
        }
    }

    private static class FileUpdate {
        private boolean fileUpdated = false;
        private String content;

        public boolean isFileUpdated() {
            return fileUpdated;
        }

        public void setFileUpdated(boolean fileUpdated) {
            this.fileUpdated = fileUpdated;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
