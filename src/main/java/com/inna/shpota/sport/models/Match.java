package com.inna.shpota.sport.models;

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
