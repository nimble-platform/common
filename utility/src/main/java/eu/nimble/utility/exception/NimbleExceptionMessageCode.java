package eu.nimble.utility.exception;

public enum NimbleExceptionMessageCode {
    BAD_REQUEST_MISSING_PARAMETERS("BAD_REQUEST.missingParameters"),
    UNAUTHORIZED_INVALID_ROLE("UNAUTHORIZED.invalidRole"),
    INTERNAL_SERVER_ERROR_CATALOGUE_PAGINATION_RESPONSE("INTERNAL_SERVER_ERROR.cataloguePaginationResponse"),
    NOT_FOUND_NO_CATALOGUE_FOR_PARTY("NOT_FOUND.noCatalogueForParty"),
    BAD_REQUEST_INVALID_STANDARD("BAD_REQUEST.invalidStandard"),
    INTERNAL_SERVER_ERROR_FAILED_TO_GET_CATALOGUE_FOR_STANDARD("INTERNAL_SERVER_ERROR.failedToGetCatalogueForStandard"),
    BAD_REQUEST_DESERIALIZE_CATALOGUE("BAD_REQUEST.deserializeCatalogue"),
    NOT_FOUND_NO_UNIT_LIST("NOT_FOUND.noUnitList"),
    UNAUTHORIZED_NO_USER_FOR_TOKEN("UNAUTHORIZED.noUserForToken"),
    INTERNAL_SERVER_ERROR_FAILED_TO_CHECK_TOKEN("INTERNAL_SERVER_ERROR.failedToCheckToken"),
    CONFLICT_UNIT_EXISTS("CONFLICT.unitExists"),
    NOT_FOUND_NO_UNIT("NOT_FOUND.noUnit"),
    CONFLICT_UNIT_LIST_EXISTS("CONFLICT.unitListExists"),
    BAD_REQUEST_INVALID_PARAMETERS_TO_GET_CATEGORIES("BAD_REQUEST.invalidParametersToGetCategories"),
    BAD_REQUEST_INVALID_TAXONOMY("BAD_REQUEST.invalidTaxonomy"),
    BAD_REQUEST_MISSING_PARAMETERS_TO_GET_CATEGORIES("BAD_REQUEST.missingParametersToGetCategories"),
    NOT_FOUND_NO_CATALOGUE("NOT_FOUND.noCatalogue"),
    BAD_REQUEST_HJIDS_IN_PRICE_OPTION("BAD_REQUEST.hjidsInPriceOption"),
    NOT_FOUND_NO_CATALOGUE_LINE("NOT_FOUND.noCatalogueLine"),
    INTERNAL_SERVER_ERROR_ADD_PRICE_OPTION("INTERNAL_SERVER_ERROR.addPriceOption"),
    NOT_FOUND_NO_PRICE_OPTION("NOT_FOUND.noPriceOption"),
    INTERNAL_SERVER_ERROR_DELETE_PRICE_OPTION("INTERNAL_SERVER_ERROR.deletePriceOption"),
    BAD_REQUEST_INVALID_HJIDS("BAD_REQUEST.invalidHjids"),
    INTERNAL_SERVER_ERROR_UPDATE_PRICE_OPTION("INTERNAL_SERVER_ERROR.updatePriceOption"),
    INTERNAL_SERVER_ERROR_GET_VAT_RATES("INTERNAL_SERVER_ERROR.getVatRates"),
    INTERNAL_SERVER_ERROR_GET_PRODUCTS_WITHOUT_LCPA_PROCESSING("INTERNAL_SERVER_ERROR.getProductsWithoutLCPAProcessing"),
    NOT_FOUND_NO_CATALOGUE_LINE_FOR_HJID("NOT_FOUND.noCatalogueLineForHjid"),
    BAD_REQUEST_PARSE_LCPA_OUTPUT("BAD_REQUEST.parseLCPAOutput"),
    INTERNAL_SERVER_ERROR_ADD_LCPA_OUTPUT("INTERNAL_SERVER_ERROR.addLCPAOutput"),
    NOT_FOUND_NO_BINARY_CONTENT("NOT_FOUND.noBinaryContent"),
    INTERNAL_SERVER_ERROR_GET_BINARY_CONTENT("INTERNAL_SERVER_ERROR.getBinaryContent"),
    INTERNAL_SERVER_ERROR_GET_BINARY_CONTENTS("INTERNAL_SERVER_ERROR.getBinaryContents"),
    INTERNAL_SERVER_ERROR_WRITE_BINARY_CONTENT_TO_OUTPUT_STREAM("INTERNAL_SERVER_ERROR.writeBinaryContentToOutputStream"),
    INTERNAL_SERVER_ERROR_GET_BASE_64_BINARY_CONTENT("INTERNAL_SERVER_ERROR.getBase64BinaryContent"),
    INTERNAL_SERVER_ERROR_IMPORT_CATALOGUE("INTERNAL_SERVER_ERROR.importCatalogue"),
    INTERNAL_SERVER_ERROR_FAILED_TO_GET_CATALOGUE("INTERNAL_SERVER_ERROR.failedToGetCatalogue"),
    INTERNAL_SERVER_ERROR_WRITE_CATALOGUE_CONTENT_TO_OUTPUT_STREAM("INTERNAL_SERVER_ERROR.writeCatalogueContentToOutputStream"),
    INTERNAL_SERVER_ERROR_EXPORT_CATALOGUE("INTERNAL_SERVER_ERROR.exportCatalogue"),
    INTERNAL_SERVER_ERROR_DOWNLOAD_BOM_TEMPLATE("INTERNAL_SERVER_ERROR.downloadBOMTemplate"),
    INTERNAL_SERVER_ERROR_GET_CATALOGUE_LINE("INTERNAL_SERVER_ERROR.getCatalogueLine"),
    INTERNAL_SERVER_ERROR_GET_CATALOGUE_LINES("INTERNAL_SERVER_ERROR.getCatalogueLines"),
    BAD_REQUEST_GET_CATALOGUE_LINES("BAD_REQUEST.getCatalogueLines"),
    INTERNAL_SERVER_ERROR_GET_CATALOGUE_LINE_WITH_LINE_AND_CATALOGUE_ID("INTERNAL_SERVER_ERROR.getCatalogueLineWithLineAndCatalogueId"),
    NOT_FOUND_NO_CATALOGUE_LINE_WITH_ID("NOT_FOUND.noCatalogueLineWithId"),
    INTERNAL_SERVER_ERROR_GET_MULTIPLE_CATALOGUE_LINES("INTERNAL_SERVER_ERROR.getMultipleCatalogueLines"),
    BAD_REQUEST_DESERIALIZE_CATALOGUE_LINE("BAD_REQUEST.deserializeCatalogueLine"),
    BAD_REQUEST_HJIDS("BAD_REQUEST.hjids"),
    NOT_ACCEPTABLE_ALREADY_EXISTS("NOT_ACCEPTABLE.alreadyExists"),
    INTERNAL_SERVER_ERROR_ADD_CATALOGUE_LINE("INTERNAL_SERVER_ERROR.addCatalogueLine"),
    BAD_REQUEST_INVALID_HJIDS_IN_LINE("BAD_REQUEST.invalidHjidsInLine"),
    INTERNAL_SERVER_ERROR_UPDATE_CATALOGUE_LINE("INTERNAL_SERVER_ERROR.updateCatalogueLine"),
    INTERNAL_SERVER_ERROR_DELETE_CATALOGUE_LINE("INTERNAL_SERVER_ERROR.delegateCatalogueLine"),
    INTERNAL_SERVER_ERROR_GET_PRODUCT_AND_SERVICE_COUNT("INTERNAL_SERVER_ERROR.getProductAndServiceCount"),
    INTERNAL_SERVER_ERROR_INDEX_ECLASS_CATEGORIES("INTERNAL_SERVER_ERROR.indexEclassCategories"),
    INTERNAL_SERVER_ERROR_GET_AVAILABLE_TAXONOMIES("INTERNAL_SERVER_ERROR.getAvailableTaxonomies"),
    NOT_FOUND_NO_CATEGORY("NOT_FOUND.noCategory"),
    INTERNAL_SERVER_ERROR_INDEX_ECLASS_PROPERTIES("INTERNAL_SERVER_ERROR.indexEclassProperties"),
    INTERNAL_SERVER_ERROR_INDEX_ECLASS_RESOURCES("INTERNAL_SERVER_ERROR.indexEclassResources"),
    BAD_REQUEST_NO_ID_FOR_LINE("BAD_REQUEST.noIdForLine"),
    BAD_REQUEST_INVALID_REFERENCE("BAD_REQUEST.invalidReference"),
    BAD_REQUEST_NO_MANUFACTURER_PARTY("BAD_REQUEST.noManufacturerParty"),
    BAD_REQUEST_IDS_DO_NOT_MATCH("BAD_REQUEST.idsDoNotMatch"),
    BAD_REQUEST_NO_NAME_FOR_LINE("BAD_REQUEST.noNameForLine"),
    BAD_REQUEST_NO_COMMODITY_CLASSIFICATION("BAD_REQUEST.noCommodityClassification"),
    BAD_REQUEST_PARTY_IDS_DO_NOT_MATCH("BAD_REQUEST.partyIdsDoNotMatch"),
    BAD_REQUEST_LARGER_THAN_ALLOWED_SIZE("BAD_REQUEST.largerThanAllowedSize"),
    BAD_REQUEST_NO_BINARY_CONTENT_FOR_THE_FILE("BAD_REQUEST.noBinaryContentForTheFile"),
    BAD_REQUEST_NO_ID_FOR_CATALOGUE("BAD_REQUEST.noIdForCatalogue"),
    CONFLICT_CATALOGUE_ID_ALREADY_EXISTS("CONFLICT.catalogueIdAlreadyExists"),
    BAD_REQUEST_NO_UPDATE_OPERATION_FOR_STANDARD("BAD_REQUEST.noUpdateOperationForStandard"),
    INTERNAL_SERVER_ERROR_UPDATE_CATALOGUE("INTERNAL_SERVER_ERROR.updateCatalogue"),
    INTERNAL_SERVER_ERROR_UNEXPECTED_ERROR_WHILE_UPDATING_CATALOGUE("INTERNAL_SERVER_ERROR.unExpectedErrorWhileUpdatingCatalogue"),
    INTERNAL_SERVER_ERROR_DELETE_CATALOGUE("INTERNAL_SERVER_ERROR.deleteCatalogue"),
    INTERNAL_SERVER_ERROR_DELETE_CATALOGUES("INTERNAL_SERVER_ERROR.deleteCatalogues"),
    INTERNAL_SERVER_ERROR_GENERATE_TEMPLATE("INTERNAL_SERVER_ERROR.generateTemplate"),
    INTERNAL_SERVER_ERROR_WRITE_TEMPLATE_CONTENT_TO_OUTPUT_STREAM("INTERNAL_SERVER_ERROR.writeTemplateContentToOutputStream"),
    BAD_REQUEST_PARSE_CATALOGUE("BAD_REQUEST.parseCatalogue"),
    INTERNAL_SERVER_ERROR_GENERATE_URI_FOR_ITEM("INTERNAL_SERVER_ERROR.generateUriForItem"),
    INTERNAL_SERVER_ERROR_GET_STANDARDS("INTERNAL_SERVER_ERROR.getStandards"),
    BAD_REQUEST_GET_ZIP_PACKAGE("BAD_REQUEST.getZipPackage"),
    BAD_REQUEST_UPLOAD_IMAGES("BAD_REQUEST.uploadImages"),
    INTERNAL_SERVER_ERROR_UPLOAD_IMAGES("INTERNAL_SERVER_ERROR.uploadImages"),
    INTERNAL_SERVER_ERROR_UNEXPECTED_ERROR_WHILE_UPLOADING_IMAGES("INTERNAL_SERVER_ERROR.unExpectedErrorWhileUploadingImages"),
    INTERNAL_SERVER_ERROR_DELETE_IMAGES("INTERNAL_SERVER_ERROR.deleteImages"),
    INTERNAL_SERVER_ERROR_GET_CATALOGUE_IN_SEMANTIC_FORMAT("INTERNAL_SERVER_ERROR.getCatalogueInSemanticFormat"),
    INTERNAL_SERVER_ERROR_FAILED_TO_GET_CATALOGUES("INTERNAL_SERVER_ERROR.failedToGetCatalogues"),
    NOT_FOUND_NO_DEFAULT_CATALOGUE("NOT_FOUND.noDefaultCatalogue"),
    NOT_FOUND_ORDER("NOT_FOUND.order"),
    BAD_REQUEST_PAYMENT_DONE("BAD_REQUEST.paymentDone"),
    INTERNAL_SERVER_ERROR_CANCEL_PROCESS("INTERNAL_SERVER_ERROR.cancelProcess"),
    BAD_REQUEST_NO_QUALIFYING_PARTY("BAD_REQUEST.noQualifyingParty"),
    BAD_REQUEST_NO_PROCESS_DOCUMENT_METADATA("BAD_REQUEST.noProcessDocumentMetadata"),
    BAD_REQUEST_NO_PARTY("BAD_REQUEST.noParty"),
    BAD_REQUEST_PARTY_NOT_INCLUDED_IN_PROCESS("BAD_REQUEST.partyNotIncludedInProcess"),
    BAD_REQUEST_MISSING_RATING_PARAMETER("BAD_REQUEST.missingRatingParameter"),
    BAD_REQUEST_NO_COMPLETED_TASK("BAD_REQUEST.noCompletedTask"),
    INTERNAL_SERVER_ERROR_CREATE_RATING_AND_REVIEW("INTERNAL_SERVER_ERROR.createRatingAndReview"),
    INTERNAL_SERVER_ERROR_LIST_ALL_INDIVIDUAL_RATINGS_AND_REVIEWS("INTERNAL_SERVER_ERROR.listAllIndividualRatingsAndReviews"),
    BAD_REQUEST_INVALID_ROLE("BAD_REQUEST.invalidRole"),
    BAD_REQUEST_INVALID_BUSINESS_PROCESS_TYPE("BAD_REQUEST.invalidBusinessProcessType"),
    BAD_REQUEST_INVALID_DOCUMENT_TYPE("BAD_REQUEST.invalidDocumentType"),
    BAD_REQUEST_INVALID_DATE("BAD_REQUEST.invalidDate"),
    BAD_REQUEST_INVALID_STATUS("BAD_REQUEST.invalidStatus"),
    NOT_FOUND_NO_CLASS("NOT_FOUND.noClass"),
    INTERNAL_SERVER_ERROR_FAILED_TO_RETRIEVE_EPC("INTERNAL_SERVER_ERROR.failedToRetrieveEPC"),
    BAD_REQUEST_NOT_USED_IN_ANY_ORDER("BAD_REQUEST.notUsedInAnyOrder"),
    INTERNAL_SERVER_ERROR_GET_CATALOGUE_LINE_FOR_EPC("INTERNAL_SERVER_ERROR.getCatalogueLineForEPCCode"),
    INTERNAL_SERVER_ERROR_GET_EPC_BELONGS_TO_PRODUCT("INTERNAL_SERVER_ERROR.getEPCCodesBelongsToProduct"),
    UNAUTHORIZED_FAILED_TO_CREATE_SHOPPING_CART("UNAUTHORIZED.failedToCreateShoppingCart"),
    INTERNAL_SERVER_ERROR_FAILED_TO_GET_SHOPPING_CART("INTERNAL_SERVER_ERROR.failedToGetShoppingCart"),
    INTERNAL_SERVER_ERROR_FAILED_TO_CREATE_SHOPPING_CART("INTERNAL_SERVER_ERROR.failedToCreateShoppingCart"),
    BAD_REQUEST_NO_PRODUCT("BAD_REQUEST.noProduct"),
    PRECONDITION_FAILED_NO_SHOPPING_CART("PRECONDITION_FAILED.noShoppingCart"),
    INTERNAL_SERVER_ERROR_ADD_PRODUCT_TO_SHOPPING_CART("INTERNAL_SERVER_ERROR.addProductToShoppingCart"),
    INTERNAL_SERVER_ERROR_REMOVE_PRODUCTS_FROM_SHOPPING_CART("INTERNAL_SERVER_ERROR.removeProductsFromShoppingCart"),
    INTERNAL_SERVER_ERROR_FAILED_TO_EXTRACT_PARTY_INFO("INTERNAL_SERVER_ERROR.failedToExtractPartyInfo"),
    INTERNAL_SERVER_ERROR_GET_EXPECTED_ORDERS("INTERNAL_SERVER_ERROR.getExpectedOrders"),
    INTERNAL_SERVER_ERROR_SEND_PAYMENT_LOG("INTERNAL_SERVER_ERROR.sendPaymentLog"),
    NOT_FOUND_NO_PROCESS_INSTANCE_GROUP("NOT_FOUND.noProcessInstanceGroup"),
    INTERNAL_SERVER_ERROR_GET_PROCESS_INSTANCE_GROUP_FILTERS("INTERNAL_SERVER_ERROR.getProcessInstanceGroupFilters"),
    NOT_FOUND_NO_METADATA_FOR_ORDER_RESPONSE("NOT_FOUND.noMetadataForOrderResponse"),
    NOT_FOUND_NO_PROCESS_ID("NOT_FOUND.noProcessId"),
    INTERNAL_SERVER_ERROR_GET_ORDER_DOCUMENT("INTERNAL_SERVER_ERROR.getOrderDocument"),
    BAD_REQUEST_ALREADY_FINISHED("BAD_REQUEST.alreadyFinished"),
    INTERNAL_SERVER_ERROR_FINISH_COLLABORATION("INTERNAL_SERVER_ERROR.finishCollaboration"),
    BAD_REQUEST_ALREADY_CANCELLED("BAD_REQUEST.alreadyCancelled"),
    BAD_REQUEST_ALREADY_COMPLETED("BAD_REQUEST.alreadyCompleted"),
    INTERNAL_SERVER_ERROR_CANCEL_COLLABORATION("INTERNAL_SERVER_ERROR.cancelCollaboration"),
    NOT_FOUND_NO_DOCUMENT("NOT_FOUND.noDocument"),
    INTERNAL_SERVER_ERROR_SERIALIZATION_ERROR("INTERNAL_SERVER_ERROR.serializationError"),
    INTERNAL_SERVER_ERROR_GET_DOCUMENT_JSON_CONTENT("INTERNAL_SERVER_ERROR.getDocumentJsonContent"),
    INTERNAL_SERVER_ERROR_FAILED_TO_GET_PARTY("INTERNAL_SERVER_ERROR.failedToGetParty"),
    BAD_REQUEST_INVALID_IDENTIFIERS("BAD_REQUEST.invalidIdentifiers"),
    INTERNAL_SERVER_ERROR_UPDATE_DOCUMENT("INTERNAL_SERVER_ERROR.updateDocument"),
    NOT_FOUND_NO_PROCESS_DOCUMENT_METADATA("NOT_FOUND.noProcessDocumentMetadata"),
    INTERNAL_SERVER_ERROR_GET_PROCESS_COUNT("INTERNAL_SERVER_ERROR.getProcessCount"),
    INTERNAL_SERVER_ERROR_GET_PROCESS_COUNT_BREAK_DOWN("INTERNAL_SERVER_ERROR.getProcessCountBreakDown"),
    INTERNAL_SERVER_ERROR_GET_NON_ORDERED_PRODUCTS("INTERNAL_SERVER_ERROR.getNonOrderedProducts"),
    INTERNAL_SERVER_ERROR_GET_TRADING_VOLUME("INTERNAL_SERVER_ERROR.getTradingVolume"),
    INTERNAL_SERVER_ERROR_GET_INACTIVE_COMPANIES("INTERNAL_SERVER_ERROR.getInactiveCompanies"),
    INTERNAL_SERVER_ERROR_GET_AVERAGE_RESPONSE_TIME("INTERNAL_SERVER_ERROR.getAverageResponseTime"),
    INTERNAL_SERVER_ERROR_GET_STATISTICS("INTERNAL_SERVER_ERROR.getStatistics"),
    INTERNAL_SERVER_ERROR_GET_FULFILMENT_STATISTICS("INTERNAL_SERVER_ERROR.getFulfilmentStatistics"),
    NOT_FOUND_NO_COLLABORATION_GROUP("NOT_FOUND.noCollaborationGroup"),
    NOT_ACCEPTABLE_NOT_ARCHIVABLE("NOT_ACCEPTABLE.notArchivable"),
    INTERNAL_SERVER_ERROR_ARCHIVE_COLLABORATION_GROUP("INTERNAL_SERVER_ERROR.archiveCollaborationGroup"),
    INTERNAL_SERVER_ERROR_GET_COLLABORATION_GROUP("INTERNAL_SERVER_ERROR.getCollaborationGroup"),
    BAD_REQUEST_MISSING_PARTY_PARAMETERS("BAD_REQUEST.missingPartyParameters"),
    INTERNAL_SERVER_ERROR_GET_COLLABORATION_GROUPS("INTERNAL_SERVER_ERROR.getCollaborationGroups"),
    INTERNAL_SERVER_ERROR_GET_FEDERATED_METADATA("INTERNAL_SERVER_ERROR.getFederatedMetadata"),
    INTERNAL_SERVER_ERROR_UNMERGE_GROUPS("INTERNAL_SERVER_ERROR.unmergeGroups"),
    INTERNAL_SERVER_ERROR_FAILED_TO_DESERIALIZE_FEDERATED_METADATA("INTERNAL_SERVER_ERROR.failedToDeserializeFederatedMetadata"),
    BAD_REQUEST_HJID_FIELDS_FOUND("BAD_REQUEST.hjidFieldsFound"),
    INTERNAL_SERVER_ERROR_CONTINUE_PROCESS("INTERNAL_SERVER_ERROR.continueProcess"),
    INTERNAL_SERVER_ERROR_CREATE_NEGOTIATIONS_FOR_BOM("INTERNAL_SERVER_ERROR.createNegotiationsForBOM"),
    INTERNAL_SERVER_ERROR_FAILED_TO_GET_PARTY_INFO("INTERNAL_SERVER_ERROR.failedToGetPartyInfo"),
    BAD_REQUEST_NOT_INCLUDED_IN_WORKFLOW("BAD_REQUEST.notIncludedInWorkflow"),
    BAD_REQUEST_SAME_PARTIES_TO_START_PROCESS("BAD_REQUEST.samePartiesToStartProcess"),
    INTERNAL_SERVER_ERROR_FAILED_TO_MERGE_TRANSPORT_GROUP_TO_ORDER_GROUP("INTERNAL_SERVER_ERROR.failedToMergeTransportGroupToOrderGroup"),
    INTERNAL_SERVER_ERROR_START_PROCESS("INTERNAL_SERVER_ERROR.startProcess"),
    NOT_FOUND_INVALID_PROCESS_INSTANCE("NOT_FOUND.invalidProcessInstance"),
    INTERNAL_SERVER_ERROR_CONSTRUCT_CONTRACT_FOR_PROCESS_INSTANCES("INTERNAL_SERVER_ERROR.constructContractForProcessInstances"),
    NOT_FOUND_NO_CONTRACT("NOT_FOUND.noContract"),
    INTERNAL_SERVER_ERROR_GET_CLAUSES_FOR_PROCESS_INSTANCES("INTERNAL_SERVER_ERROR.getClausesOfContract"),
    NOT_FOUND_INVALID_BOUNDED_DOCUMENT_TYPE("NOT_FOUND.invalidBoundedDocumentType"),
    NO_CONTENT_NO_CLAUSE("NO_CONTENT.noClause"),
    INTERNAL_SERVER_ERROR_GET_CLAUSE_DETAILS("INTERNAL_SERVER_ERROR.getClauseDetails"),
    INTERNAL_SERVER_ERROR_FAILED_TO_ADD_CLAUSE("INTERNAL_SERVER_ERROR.failedToAddClause"),
    INTERNAL_SERVER_ERROR_ADD_DOCUMENT_CLAUSE_TO_CONTRACT("INTERNAL_SERVER_ERROR.addDocumentClauseToContract"),
    INTERNAL_SERVER_ERROR_FAILED_TO_ADD_DATA_MONITORING_CLAUSE_TO_CONTRACT("INTERNAL_SERVER_ERROR.failedToAddDataMonitoringClauseToContract"),
    INTERNAL_SERVER_ERROR_ADD_DATA_MONITORING_CLAUSE_TO_CONTRACT("INTERNAL_SERVER_ERROR.addDataMonitoringClauseToContract"),
    NOT_FOUND_NO_DIGITAL_AGREEMENT("NOT_FOUND.noDigitalAgreement"),
    INTERNAL_SERVER_ERROR_GET_DIGITAL_AGREEMENT_FOR_PARTIES_AND_PRODUCT("INTERNAL_SERVER_ERROR.getDigitalAgreementForPartiesAndProduct"),
    INTERNAL_SERVER_ERROR_DELETE_DIGITAL_AGREEMENT("INTERNAL_SERVER_ERROR.deleteDigitalAgreement"),
    NOT_FOUND_NO_DIGITAL_AGREEMENT_FOR_PARAMETERS("NOT_FOUND.noDigitalAgreementForParameters"),
    INTERNAL_SERVER_ERROR_GET_DIGITAL_AGREEMENT_FOR_PARTIES_AND_PRODUCTS("INTERNAL_SERVER_ERROR.getDigitalAgreementForPartiesAndProducts"),
    INTERNAL_SERVER_ERROR_GET_ALL_DIGITAL_AGREEMENTS_FOR_PARTIES_AND_PRODUCTS("INTERNAL_SERVER_ERROR.getAllDigitalAgreementsForPartiesAndProduct"),
    INTERNAL_SERVER_ERROR_FAILED_TO_GET_PERSON("INTERNAL_SERVER_ERROR.failedToGetPerson"),
    INTERNAL_SERVER_ERROR_FAILED_TO_DESERIALIZE_DOCUMENT("INTERNAL_SERVER_ERROR.failedToDeserializeDocument"),
    INTERNAL_SERVER_ERROR_FAILED_TO_CREATE_PROCESS_INSTANCE_INPUT_MESSAGE("INTERNAL_SERVER_ERROR.failedToCreateProcessInstanceInputMessage"),
    INTERNAL_SERVER_ERROR_FAILED_TO_GET_GROUP_ID_TUPLE("INTERNAL_SERVER_ERROR.failedToGetGroupIdTuple"),
    INTERNAL_SERVER_ERROR_START_PROCESS_WITH_DOCUMENT("INTERNAL_SERVER_ERROR.startProcessWithDocument"),
    BAD_REQUEST_NO_VALID_REFERENCE("BAD_REQUEST.noValidReference"),
    INTERNAL_SERVER_ERROR_COMPLETE_PROCESS("INTERNAL_SERVER_ERROR.completeProcess"),
    INTERNAL_SERVER_ERROR_FAILED_TO_SEND_DOCUMENT_TO_INITIATOR_PARTY("INTERNAL_SERVER_ERROR.failedToSendDocumentToInitiatorParty"),
    INTERNAL_SERVER_ERROR_FAILED_TO_SERIALIZE_RFQ_SUMMARY("INTERNAL_SERVER_ERROR.failedToSerializeRFQSummary"),
    NOT_FOUND_NO_PRODUCT("NOT_FOUND.noProduct"),
    INTERNAL_SERVER_ERROR_GET_NEGOTIATION_SETTINGS("INTERNAL_SERVER_ERROR.getNegotiationSettings"),
    INTERNAL_SERVER_ERROR_CREATE_RFQ("INTERNAL_SERVER_ERROR.createRfq"),
    INTERNAL_SERVER_ERROR_SERIALIZE_RFQ("INTERNAL_SERVER_ERROR.serializeRFQ"),
    INTERNAL_SERVER_ERROR_CREATE_ORDER("INTERNAL_SERVER_ERROR.createOrder"),
    INTERNAL_SERVER_ERROR_SERIALIZE_ORDER("INTERNAL_SERVER_ERROR.serializeOrder"),
    INTERNAL_SERVER_ERROR_GENERATE_CONTRACT("INTERNAL_SERVER_ERROR.generateContract"),
    INTERNAL_SERVER_ERROR_FAILED_TO_GENERATE_ORDER_TERMS("INTERNAL_SERVER_ERROR.failedToGenerateOrderTerms"),
    NOT_FOUND_NO_PROCESS_INSTANCE("NOT_FOUND.noProcessInstance"),
    INTERNAL_SERVER_ERROR_UPDATE_INSTANCE("INTERNAL_SERVER_ERROR.updateInstance"),
    INTERNAL_SERVER_ERROR_IS_RATED("INTERNAL_SERVER_ERROR.isRated"),
    INTERNAL_SERVER_ERROR_GET_PROCESS_INSTANCE_ID_FOR_DOCUMENT("INTERNAL_SERVER_ERROR.getProcessInstanceIdForDocument"),
    INTERNAL_SERVER_ERROR_GET_DASHBOARD_PROCESS_INSTANCE_DETAILS("INTERNAL_SERVER_ERROR.getDashboardProcessInstanceDetails"),
    NOT_FOUND_NO_COLLABORATION_GROUP_FOR_PROCESS("NOT_FOUND.noCollaborationGroupForProcess"),
    INTERNAL_SERVER_ERROR_GET_ASSOCIATED_COLLABORATION_GROUP("INTERNAL_SERVER_ERROR.getAssociatedCollaborationGroup"),
    INTERNAL_SERVER_ERROR_FAILED_TO_WRITE_TRANSACTION_SUMMARY("INTERNAL_SERVER_ERROR.failedToWriteTransactionSummary"),
    INTERNAL_SERVER_ERROR_FAILED_TO_WRITE_DOCUMENT_TO_ZIP("INTERNAL_SERVER_ERROR.failedToWriteDocumentToZip"),
    INTERNAL_SERVER_ERROR_FAILED_TO_WRITE_AUXILIARY_FILE_TO_ZIP("INTERNAL_SERVER_ERROR.failedToWriteAuxiliaryFileToZip"),
    INTERNAL_SERVER_ERROR_EXPORT_TRANSACTION("INTERNAL_SERVER_ERROR.exportTransactions"),
    INTERNAL_SERVER_ERROR_ADD_CATALOGUE("INTERNAL_SERVER_ERROR.addCatalogue"),
    INTERNAL_SERVER_ERROR_UPLOAD_TEMPLATE("INTERNAL_SERVER_ERROR.uploadTemplate"),
    NOT_FOUND_NO_CONTRACT_FOR_DOCUMENT_TYPE("NOT_FOUND.noContractForDocumentType"),
    GATEWAY_TIMEOUT_WAITING_FOR_ANOTHER_SERVER("GATEWAY_TIMEOUT.waitingForAnotherServer"),
    INTERNAL_SERVER_ERROR_NO_AVAILABLE_RESOURCE("INTERNAL_SERVER_ERROR.noAvailableResource"),
    UNAUTHORIZED_INDEX_UBL_PROPERTIES("UNAUTHORIZED.indexUBLProperties"),
    UNAUTHORIZED_INDEX_CATALOGUES("UNAUTHORIZED.indexCatalogues"),
    UNAUTHORIZED_DELETE_INVALID_PRODUCTS("UNAUTHORIZED.deleteInvalidProducts"),
    UNAUTHORIZED_CREATE_VAT_FOR_PRODUCTS("UNAUTHORIZED.createVATForProducts"),
    UNAUTHORIZED_INDEX_ECLASS_RESOURCES("UNAUTHORIZED.indexEClassResources"),
    UNAUTHORIZED_INDEX_ECLASS_PROPERTIES("UNAUTHORIZED.indexEClassProperties"),
    UNAUTHORIZED_INDEX_ECLASS_CATEGORIES("UNAUTHORIZED.indexEClassCategories")
    ;

    private String value;

    NimbleExceptionMessageCode(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
