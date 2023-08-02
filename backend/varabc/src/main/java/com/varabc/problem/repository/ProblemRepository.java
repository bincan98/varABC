package com.varabc.problem.repository;

import com.varabc.problem.domain.entity.ProblemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity,Long> {

    @Modifying
    @Query("UPDATE ProblemEntity p SET p.problemResign = true WHERE p.problemNo = ?1")
    void updateProblemResign(Long problemNo);
}
