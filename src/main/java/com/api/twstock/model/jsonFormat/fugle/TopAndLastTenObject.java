package com.api.twstock.model.jsonFormat.fugle;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TopAndLastTenObject {

    List<Map.Entry<String, Float>> topTenList;

    List<Map.Entry<String, Float>>  lastTenList;
}
