package com.api.twstock.model.jsonFormat.fugle;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Data<T> {

    @JsonProperty(value="apiVersion")
    String apiVersion;

    @JsonProperty(value="data")
    Quote quote;
}
