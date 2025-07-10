package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "scores")
public class Score {

    @Id
    @Column(name = "score_code", length = 128)
    private String scoreCode;

    @Column(nullable = false)
    private Integer value;

    @Column(length = 7)
    private String color;

    public Score() {}
}