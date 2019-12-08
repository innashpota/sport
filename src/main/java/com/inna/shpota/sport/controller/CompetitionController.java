package com.inna.shpota.sport.controller;

import com.inna.shpota.sport.data.MatchResult;
import com.inna.shpota.sport.data.Result;
import com.inna.shpota.sport.models.Competition;
import com.inna.shpota.sport.models.Match;
import com.inna.shpota.sport.models.Round;
import com.inna.shpota.sport.service.CompetitionService;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/competitions")
@RestController
public class CompetitionController {
    private final CompetitionService service;

    public CompetitionController(CompetitionService service) {
        Assert.notNull(service, "Service must not be null");
        this.service = service;
    }

    @PostMapping
    public List<Round> add(@RequestBody Competition competition) {
        return service.add(competition);
    }

    @GetMapping("/{name}")
    public List<Round> findByName(@PathVariable("name") String name) {
        return service.findByName(name);
    }

    @PutMapping("/{name}")
    public void setWinner(
            @PathVariable("name") String name,
            @RequestBody MatchResult matchResult
    ) {
        service.setWinner(name, matchResult);
    }

    @GetMapping("/{name}/playedMatches")
    public List<Match> playedMatches(@PathVariable("name") String name) {
        return service.playedMatches(name);
    }

    @GetMapping("/{name}/notPlayedMatches")
    public List<Match> notPlayedMatches(@PathVariable("name") String name) {
        return service.notPlayedMatches(name);
    }

    @GetMapping("/{name}/results")
    public List<Result> results(@PathVariable("name") String name) {
        return service.results(name);
    }
}
