package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "status")
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer statusId;

    @Column(nullable = false, unique = true, length = 128)
    private String label;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "primary_color", length = 7)
    private String primaryColor;

    @Column(name = "secondary_color", length = 7)
    private String secondaryColor;

    @Column(nullable = false)
    private Boolean pulsing = false;

    public Status() {}
}