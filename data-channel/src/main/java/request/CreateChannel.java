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

        @ApiModelProperty(value = "ID of creating company", required = true)
        private String producerCompanyID;

        @ElementCollection(targetClass = String.class)
        @ApiModelProperty(value = "IDs of consuming companies", required = true)
        private Set<String> consumerCompanyIDs;

        @ApiModelProperty(value = "Description and purpose of data channel", required = true)
        private String description;

        @Temporal(TemporalType.TIMESTAMP)
        @ApiModelProperty(value = "Opening date/time of data channel", required = true)
        private Date startDateTime;

        @Temporal(TemporalType.TIMESTAMP)
        @ApiModelProperty(value = "Closing date/time of data channel", required = true)
        private Date endDateTime;

        @ApiModelProperty(value = "ID of originating business process (optional)")
        private String businessProcessID;

        private Request() {
        }

        public Request(String producerCompanyID, Set<String> consumerCompanyIDs, String description, Date startDateTime, Date endDateTime, String businessProcessID) {
            this.producerCompanyID = producerCompanyID;
            this.consumerCompanyIDs = consumerCompanyIDs;
            this.description = description;
            this.startDateTime = startDateTime;
            this.endDateTime = endDateTime;
            this.businessProcessID = businessProcessID;
        }

        public String getProducerCompanyID() {
            return producerCompanyID;
        }

        public void setProducerCompanyID(String producerCompanyID) {
            this.producerCompanyID = producerCompanyID;
        }

        public Set<String> getConsumerCompanyIDs() {
            return consumerCompanyIDs;
        }

        public void setConsumerCompanyIDs(Set<String> consumerCompanyIDs) {
            this.consumerCompanyIDs = consumerCompanyIDs;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Date getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(Date startDateTime) {
            this.startDateTime = startDateTime;
        }

        public Date getEndDateTime() {
            return endDateTime;
        }

        public void setEndDateTime(Date endDateTime) {
            this.endDateTime = endDateTime;
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