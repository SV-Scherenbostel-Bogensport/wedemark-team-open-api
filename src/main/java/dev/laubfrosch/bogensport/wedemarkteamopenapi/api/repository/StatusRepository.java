package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Round;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Status;
import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Target;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {

    @Query("SELECT m.status FROM Match m WHERE m.round = :round AND (m.target1 = :target OR m.target2 = :target)")
    Optional<Status> findStatusByRoundAndTarget(Round round, Target target);
}
