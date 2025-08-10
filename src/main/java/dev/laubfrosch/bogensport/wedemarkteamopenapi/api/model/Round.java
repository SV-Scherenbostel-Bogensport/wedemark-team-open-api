package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "rounds")
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "round_id")
    private Integer roundId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_knock_out", nullable = false)
    private Boolean isKnockOut = false;

    @Formula("(SELECT ro.updated_at FROM round_overview ro WHERE ro.round_id = round_id)")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;

    public Round() {}
}