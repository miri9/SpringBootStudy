package org.zerock.dsl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.dsl.entity.Board;
// 인터페이스에 내용 추가 : QuerydslPredicateExecutor<도메인>
public interface BoardRepository extends JpaRepository<Board,Long>, QuerydslPredicateExecutor<Board>{
    
}