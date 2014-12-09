package org.demis.familh.core.jpa.repository;

import org.demis.familh.core.jpa.entity.FamilyTree;
import org.demis.familh.core.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("familyTreeRepository")
public interface FamilyTreeRepository  extends JpaRepository<FamilyTree, Long> {

    @Query("select f from FamilyTree f where f.user = :user")
    List<FamilyTree> findUserFamilyTrees(@Param("user") User user);
}
