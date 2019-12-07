package com.inna.shpota.sport.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "competitions")
public class Competition {
    @JsonProperty("_id")
    @BsonIgnore
    private String id;

    @NotNull
    @BsonProperty(value = "number_of_teams")
    private int numberOfTeams;

    @NotNull
    private String name;

    private List<Round> rounds;
}
