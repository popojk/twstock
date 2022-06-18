package com.api.twstock.handlers;

import com.api.twstock.model.lineEntity.Event;
import com.api.twstock.resolver.Resolver;
import com.api.twstock.resolver.TextResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Optional;

@Component
public class MessageHandler implements EventHandler{
    private final HashMap<String, Resolver> resolvers;

    public MessageHandler(TextResolver textResolver){
        resolvers = new HashMap<>(){{
           put("text",textResolver);
        }};
    }

    @Override
    public void handle(Optional<Event> event) {
        resolvers.get(event.get().getMessage().getType()).solve(event);
    }
}
