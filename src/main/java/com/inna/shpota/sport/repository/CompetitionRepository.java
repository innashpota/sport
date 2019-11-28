package com.inna.shpota.sport.repository;

import com.inna.shpota.sport.entity.Competition;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompetitionRepository extends MongoRepository<Competition, String> {
    Competition findByName(String name);
}
