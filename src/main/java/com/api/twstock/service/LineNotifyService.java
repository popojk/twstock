package com.api.twstock.service;

import com.api.twstock.model.lineEntity.EntityMessage;
import com.api.twstock.model.lineEntity.Push;
import com.api.twstock.model.lineEntity.TextMessage;
import com.api.twstock.utils.FetchAPIUtil;
import com.api.twstock.utils.LineMessageAPI;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class LineNotifyService {

    @Autowired
    LineMessageAPI lineMessageAPI;

    public String sendNoticeToAlex(String message){
        return FetchAPIUtil.fetchLineNotify("https://notify-api.line.me/api/notify",
                 message);
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
