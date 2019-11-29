package com.inna.shpota.sport.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.List;

@Data
@NoArgsConstructor
public class Competition {
    @Id
    private String id;
    private int numberOfTeams;
    private String name;
    private List<Round> rounds;
}
