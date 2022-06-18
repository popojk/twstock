package com.api.twstock.model.lineEntity;

import lombok.Data;

@Data
public class Postback {

    private String data;
    private Param param;
}

class Param{

    private String datetime;

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }



}
