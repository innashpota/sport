package com.inna.shpota.sport.data;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchResult {
    private String firstTeam;
    private String secondTeam;
    private String winner;
}
