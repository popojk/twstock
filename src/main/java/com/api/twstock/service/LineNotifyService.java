package com.api.twstock.service;

import com.api.twstock.handlers.EventHandler;
import com.api.twstock.handlers.MessageHandler;
import com.api.twstock.model.lineEntity.*;
import com.api.twstock.utils.FetchAPIUtil;
import com.api.twstock.utils.LineMessageAPI;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LineNotifyService {

    LineMessageAPI lineMessageAPI;

    public LineNotifyService(LineMessageAPI lineMessageAPI) {
        this.lineMessageAPI = lineMessageAPI;
    }

    public void pushNoticeMessageToLineUserId(String message, String userLineId){
        Push push = new Push();

        List<EntityMessage> messageList = new ArrayList<>();
        messageList.add(new TextMessage("text", message));

        push.setTo(userLineId);
        push.setMessages(messageList);

        pushMessage(push);
    }

    private void pushMessage(Push push) {
        Response response = lineMessageAPI.push(push);

        if (response.status() != 200){
            log.info("error while pushing");
            return;
        }
         log.info("push message success");
    }
}
