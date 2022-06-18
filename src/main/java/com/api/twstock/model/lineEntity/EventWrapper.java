package com.api.twstock.model.lineEntity;

import lombok.Data;

import java.util.List;

@Data
public class EventWrapper {

    private String destination;
    private List<Event> events;
}
