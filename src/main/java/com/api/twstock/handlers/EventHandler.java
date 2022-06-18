package com.api.twstock.handlers;

import com.api.twstock.model.lineEntity.Event;

import java.util.Optional;

public interface EventHandler {
    void handle(Optional<Event> event);
}
