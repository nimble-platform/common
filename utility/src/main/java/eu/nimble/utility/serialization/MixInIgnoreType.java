package eu.nimble.utility.serialization;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class MixInIgnoreType {
    @JsonIgnore byte[] value;
}