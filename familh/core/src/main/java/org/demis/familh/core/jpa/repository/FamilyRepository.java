package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.Family;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository ("familyRepository")
public interface FamilyRepository extends JpaRepository<Family, Long> {

    @Query("select f from Family f where f.familyTree = ?1")
    Page<Family> findFamilyTreeFamilies(FamilyTree familyTree, Pageable pageable);

}
