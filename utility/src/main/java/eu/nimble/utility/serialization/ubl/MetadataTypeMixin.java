package eu.nimble.utility.serialization.ubl;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public abstract class MetadataTypeMixin {
    @JsonProperty("creationDate")
    abstract Date getCreationDateItem();

    @JsonProperty("modificationDate")
    abstract Date getModificationDateItem();

    @JsonIgnore
    public abstract void setModificationDateItem(Date value);

    @JsonIgnore
    public abstract void setCreationDateItem(Date value);
}