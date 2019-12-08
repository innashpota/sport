package com.inna.shpota.sport.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Round {
    private String name;
    private List<Match> matches;
}
