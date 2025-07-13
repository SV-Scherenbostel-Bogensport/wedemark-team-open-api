package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SetRepository extends JpaRepository<Set, Integer> {
    Optional<Set> findByMatchIdAndSetIndex(Integer matchId, Short setIndex);
}
