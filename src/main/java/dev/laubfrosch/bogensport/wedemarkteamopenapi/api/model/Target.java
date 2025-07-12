package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "target_id")
    private Integer targetId;

    @Column(nullable = false, unique = true, length = 16)
    private String code;

    public Target() {}
}
