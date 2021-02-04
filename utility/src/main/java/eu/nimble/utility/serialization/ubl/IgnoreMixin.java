package eu.nimble.utility.serialization.ubl;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class IgnoreMixin {
    @JsonIgnore byte[] value;
}