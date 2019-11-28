package request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.ElementCollection;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.Set;

/**
 * Request and response entities for creating a channel.
 *
 * @author Johannes Innerbichler
 */
@SuppressWarnings("unused")
public class CreateChannel {
    @ApiModel(value = "CreateChannelRequest", discriminator = "CCREQ")
    public static class Request {

        @ApiModelProperty(value = "ID of buyer company", required = true)
        private String buyerCompanyID;

        @ApiModelProperty(value = "ID of seller company", required = true)
        private String sellerCompanyID;

        @ApiModelProperty(value = "Description and purpose of data channel", required = false)
        private String description;

        @ApiModelProperty(value = "ID of originating business process (optional)")
        private String businessProcessID;

        @ApiModelProperty(value = "ID of originating business process (optional)" ,required = false)
        private String productID;

        private Request() {
        }

        public Request(String buyerCompanyID, String sellerCompanyID, String description, String businessProcessID,String productID) {
            this.buyerCompanyID = buyerCompanyID;
            this.sellerCompanyID = sellerCompanyID;
            this.description = description;
            this.businessProcessID = businessProcessID;
            this.productID = productID;
        }

        public String getBuyerCompanyID() {
            return buyerCompanyID;
        }

        public void setBuyerCompanyID(String buyerCompanyID) {
            this.buyerCompanyID = buyerCompanyID;
        }

        public String getSellerCompanyID() {
            return sellerCompanyID;
        }

        public void setSellerCompanyID(String sellerCompanyID) {
            this.sellerCompanyID = sellerCompanyID;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getBusinessProcessID() {
            return businessProcessID;
        }

        public void setBusinessProcessID(String businessProcessID) {
            this.businessProcessID = businessProcessID;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
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
