package com.api.twstock.resolver;

import com.api.twstock.model.lineEntity.Event;

import java.util.Optional;

public interface Resolver {
    void solve(Optional<Event> event);
}
