package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

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
    @Column(name = "arrow_id")
    private Integer arrowId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "score_code", referencedColumnName = "score_code", nullable = false)
    private Score score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id", nullable = false)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "set_id", referencedColumnName = "set_id")
    private Set set;

    @Column(name = "arrow_index")
    private Short arrowIndex;

    public Arrow() {}
}