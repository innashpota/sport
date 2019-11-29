package com.inna.shpota.sport.controller;

import com.inna.shpota.sport.data.MatchResult;
import com.inna.shpota.sport.entity.Competition;
import com.inna.shpota.sport.entity.Round;
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
}
