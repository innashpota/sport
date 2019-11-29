package com.inna.shpota.sport.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Match {
    private String firstTeam;
    private String secondTeam;
    private boolean isPlayed;
    private String winner;
}
