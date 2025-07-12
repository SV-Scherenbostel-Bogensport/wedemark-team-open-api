package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "arrows", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"set_id", "player_id", "arrow_index"})
})
public class Arrow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    @Column(name = "arrow_id")
    private Integer arrowId;

    @Column(name = "score_code", nullable = false)
    private String scoreCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_code", referencedColumnName = "score_code", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Score score;

    @Column(name = "player_id", nullable = false)
    private Integer playerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Player player;

    @Column(name = "set_id", nullable = false)
    private Integer setId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id", referencedColumnName = "set_id", insertable = false, updatable = false)
    @JsonIgnore
    private Set set;

    @Column(name = "arrow_index")
    private Short arrowIndex;

    public Arrow() {}
}