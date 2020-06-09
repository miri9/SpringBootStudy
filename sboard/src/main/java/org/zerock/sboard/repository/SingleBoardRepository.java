package org.zerock.sboard.repository;

import java.time.LocalDateTime;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.zerock.sboard.domain.SingleBoard;

public interface SingleBoardRepository extends JpaRepository<SingleBoard,Long>, QuerydslPredicateExecutor<SingleBoard> {

    @Transactional
    @Modifying
    @Query("update SingleBoard b set b.title =:title, b.moddate = :moddate where b.sno =:sno")
    int updateTitle(@Param("title")String title, @Param("sno") Long sno, @Param("moddate") LocalDateTime moddate);
}