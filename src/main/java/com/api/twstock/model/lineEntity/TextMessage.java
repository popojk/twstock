package com.api.twstock.model.lineEntity;

import lombok.Data;

@Data
public class TextMessage extends EntityMessage{

    private String type;
    private String text;

    public TextMessage(String type, String text) {
        this.type = type;
        this.text = text;
    }
}
