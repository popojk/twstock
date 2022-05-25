package com.api.twstock.service;

import com.api.twstock.utils.FetchAPIUtil;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class LineNotifyService {

    public String sendNoticeToAlex(String message){
        return FetchAPIUtil.fetchLineNotify("https://notify-api.line.me/api/notify",
                 message);
    }
}
