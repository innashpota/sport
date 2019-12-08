package com.inna.shpota.sport.models;

import lombok.Data;

import java.util.List;

@Data
public class Round {
    private String name;
    private List<Match> matches;
}
