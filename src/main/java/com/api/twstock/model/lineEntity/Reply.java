package com.api.twstock.model.lineEntity;

import lombok.Data;

import java.util.List;

@Data
public class Reply {
    private String replyToken;

    private List<EntityMessage> messages;

    public String getReplyToken() {
        return replyToken;
    }

    public List<EntityMessage> getMessages() {
        return messages;
    }

    public void setReplyToken(String replyToken) {
        this.replyToken = replyToken;
    }

    public void setMessages(List<EntityMessage> messages) {
        this.messages = messages;
    }

    public Reply(String replyToken, List<EntityMessage> messages) {
        this.replyToken = replyToken;
        this.messages = messages;
    }

    public Reply() {
        super();
    }
}
