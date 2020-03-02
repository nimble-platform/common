package eu.nimble.utility.serialization.hjid_removing;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * Created by suat on 16-Oct-19.
 */
@JsonFilter(JsonFieldFilter.JSON_FILTER)
public class JsonFieldFilter {
    public static final String JSON_FILTER = "Json filter";
}