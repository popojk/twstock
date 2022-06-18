package com.api.twstock.model.lineEntity;

import lombok.Data;

import java.util.List;

@Data
public class Push {

    String to;

    List<EntityMessage> messages;

}
