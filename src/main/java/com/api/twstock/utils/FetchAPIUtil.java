package com.api.twstock.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class FetchAPIUtil {

    public static <T> T send(String url, HttpMethod method, Class<T> t) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
            HttpEntity<T> responseEntity = restTemplate.exchange(url, method, httpEntity, t);
            T result = responseEntity.getBody();

            return result;

        } catch (HttpClientErrorException httpClientErrorException) {
            httpClientErrorException.getResponseBodyAsString();
            return null;
        }
    }

    public static String fetchLineNotify(String url, String message){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth("XuIetJH1sAmXkUOQi1MJUOsLjBv3D73OHW92rXxA3ok");

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("message",message);
        HttpEntity<String> request =  new HttpEntity<String>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                .queryParams(map);
        UriComponents uriComponents = builder.build().encode();


        ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uriComponents.toUri(), request, String.class );
        return responseEntityStr.getBody();

    }

    public static <T> T fetchFinmindAPI(String dataset, String dataId, String startDate, HttpMethod request, Class<T> t){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkYXRlIjoiMjAyMi0wMy0yNiAxMzo1NDoxMCIsInVzZXJfaWQiOiJwb3BvamsiLCJpcCI6IjEuMTcxLjE3OS40MyJ9.UtLxvbsWhW7reNHzgmg9Ja_JNH7nqudO7G3QtU6y9oQ";


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("dataset",dataset);
        map.add("data_id", dataId);
        map.add("start_date", startDate);
        map.add("token", token);

        HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://api.finmindtrade.com/api/v4/data")
                .queryParams(map);
        UriComponents uriComponents = builder.build().encode();

        HttpEntity<T> responseEntity = restTemplate.exchange(uriComponents.toUri(), request, httpEntity, t);
        T result = responseEntity.getBody();

        long i = 1;

        if(result == null){
            fetchFinmindAPI(dataset, dataId, LocalDate.now().minusDays(i).toString(), request, t);
            i++;
        }

        return result;

    }

}
