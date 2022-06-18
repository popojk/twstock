package com.api.twstock.utils;

import com.api.twstock.config.LineMessageAPIConfiguration;
import com.api.twstock.model.lineEntity.Push;
import com.api.twstock.model.lineEntity.Reply;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name="messageAPIClient",
             url="https://api.line.me/",
             configuration = LineMessageAPIConfiguration.class)
public interface LineMessageAPI {

    @PostMapping("v2/bot/message/reply")
    Response reply(Reply reply);

    @PostMapping("v2/bot/message/push")
    Response push(Push push);


    @PostMapping("/company/applylistingForeign")
    Response test();




}
