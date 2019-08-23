package eu.nimble.utility.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.nimble.service.model.ubl.despatchadvice.DespatchAdviceType;
import eu.nimble.service.model.ubl.document.IDocument;
import eu.nimble.service.model.ubl.iteminformationrequest.ItemInformationRequestType;
import eu.nimble.service.model.ubl.iteminformationresponse.ItemInformationResponseType;
import eu.nimble.service.model.ubl.order.OrderType;
import eu.nimble.service.model.ubl.orderresponsesimple.OrderResponseSimpleType;
import eu.nimble.service.model.ubl.ppaprequest.PpapRequestType;
import eu.nimble.service.model.ubl.ppapresponse.PpapResponseType;
import eu.nimble.service.model.ubl.quotation.QuotationType;
import eu.nimble.service.model.ubl.receiptadvice.ReceiptAdviceType;
import eu.nimble.service.model.ubl.requestforquotation.RequestForQuotationType;
import eu.nimble.service.model.ubl.transportexecutionplan.TransportExecutionPlanType;
import eu.nimble.service.model.ubl.transportexecutionplanrequest.TransportExecutionPlanRequestType;

import java.io.IOException;

/**
 * A custom deserializer to properly deserialize the objects that are instances of classes which implement {@link IDocument}
 */
public class IDocumentDeserializer extends JsonDeserializer<IDocument> {

    @Override
    public IDocument deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ObjectMapper mapper = (ObjectMapper) jp.getCodec();
        JsonNode node = mapper.readTree(jp);

        if(node == null) {
            return null;
        }

        if(node.has("itemInformationRequestLine")){
            return mapper.readValue(node.toString(), ItemInformationRequestType.class);
        }
        else if(node.has("itemInformationRequestDocumentReference")){
            return mapper.readValue(node.toString(), ItemInformationResponseType.class);
        }
        else if(node.has("requestForQuotationLine")){
            return mapper.readValue(node.toString(), RequestForQuotationType.class);
        }
        else if(node.has("requestForQuotationDocumentReference")){
            return mapper.readValue(node.toString(), QuotationType.class);
        }
        else if(node.has("despatchLine")){
            return mapper.readValue(node.toString(), DespatchAdviceType.class);
        }
        else if(node.has("despatchDocumentReference")){
            return mapper.readValue(node.toString(), ReceiptAdviceType.class);
        }
        else if(node.has("orderLine")){
            return mapper.readValue(node.toString(), OrderType.class);
        }
        else if(node.has("orderReference")){
            return mapper.readValue(node.toString(), OrderResponseSimpleType.class);
        }
        else if(node.has("mainTransportationService")){
            return mapper.readValue(node.toString(), TransportExecutionPlanRequestType.class);
        }
        else if(node.has("transportExecutionPlanRequestDocumentReference")){
            return mapper.readValue(node.toString(), TransportExecutionPlanType.class);
        }
        else if(node.has("ppapDocumentReference")){
            return mapper.readValue(node.toString(), PpapResponseType.class);
        }
        return mapper.readValue(node.toString(), PpapRequestType.class);
    }
}
