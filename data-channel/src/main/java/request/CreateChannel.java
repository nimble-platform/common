package request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.ElementCollection;

/**
 * Request and response entities for creating a channel.
 *
 * @author Johannes Innerbichler
 */
@SuppressWarnings("unused")
public class CreateChannel {
    @ApiModel(value = "CreateChannelRequest", discriminator = "CCREQ")
    public static class Request {

        @ApiModelProperty(value = "ID of seller company", required = true)
        private String sellerCompanyID;

        @ElementCollection(targetClass = String.class)
        @ApiModelProperty(value = "ID of buyer company", required = true)
        private String buyerCompanyID;

        @ApiModelProperty(value = "ID of originating business process (optional)")
        private String businessProcessID;

        private Request() {
        }

        public Request(String sellerCompanyID, String buyerCompanyID, String businessProcessID) {
            this.sellerCompanyID = sellerCompanyID;
            this.buyerCompanyID = buyerCompanyID;
            this.businessProcessID = businessProcessID;
        }

        public String getSellerCompanyID() {
            return sellerCompanyID;
        }

        public void setSellerCompanyID(String sellerCompanyID) {
            this.sellerCompanyID = sellerCompanyID;
        }

        public String getBuyerCompanyID() {
            return buyerCompanyID;
        }

        public void setBuyerCompanyID(String buyerCompanyID) {
            this.buyerCompanyID = buyerCompanyID;
        }

        public String getBusinessProcessID() {
            return businessProcessID;
        }

        public void setBusinessProcessID(String businessProcessID) {
            this.businessProcessID = businessProcessID;
        }

        
    }


    @ApiModel(value = "CreateChannelResponse", discriminator = "CCRES")
    public static class Response {

        @ApiModelProperty(value = "ID of created channel", required = true)
        private String channelID;

        private Response() {
        }

        public Response(String channelID) {
            this.channelID = channelID;
        }

        public String getChannelID() {
            return channelID;
        }

        public void setChannelID(String channelID) {
            this.channelID = channelID;
        }
    }
}