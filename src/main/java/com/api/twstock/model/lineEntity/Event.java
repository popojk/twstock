package com.api.twstock.model.lineEntity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Event {
    private String replyToken;
    private String mode;
    private String type;
    private Source source;
    private String timestamp;
    private Message message;

    @JsonProperty("postback")
    private Postback postback;

    public Event() {

    }

    public Event(String type, Source source, Message message, String replyToken) {
        this.type = type;
        this.source = source;
        this.message = message;
        this.replyToken = replyToken;
    }

}
