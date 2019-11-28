package com.inna.shpota.sport.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result {
    private String team;
    private int playedMatchesCount;
    private int wins;
    private int draws;
    private int lose;
    private int points;
}
