package com.inna.shpota.sport.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Round {
    private String name;
    private List<Match> matches;
}
