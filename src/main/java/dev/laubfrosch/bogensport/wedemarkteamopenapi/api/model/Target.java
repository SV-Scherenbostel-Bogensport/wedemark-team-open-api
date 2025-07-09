package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "targets")
public class Target {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "target_id")
    private Integer targetId;

    @Column(name = "code", length = 16, nullable = false, unique = true)
    private String code;

    public Target() {}
}
