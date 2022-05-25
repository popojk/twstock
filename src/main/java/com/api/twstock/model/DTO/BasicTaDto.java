package com.api.twstock.model.DTO;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BasicTaDto {

    private String stockNo;

    private String startDate;

    private String endDate;

    private List<Integer> movingAverageList = new ArrayList<Integer>();
}
