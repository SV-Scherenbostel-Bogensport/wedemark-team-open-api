package dev.laubfrosch.bogensport.wedemarkteamopenapi.api.repository;

import dev.laubfrosch.bogensport.wedemarkteamopenapi.api.model.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetRepository extends JpaRepository<Set, Integer> {
}
