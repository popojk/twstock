package com.api.twstock.model.jsonFormat;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class FinmindQuoteData<T> {

    @JsonProperty(value="data")
    List<T> data= new ArrayList<T>();


}
