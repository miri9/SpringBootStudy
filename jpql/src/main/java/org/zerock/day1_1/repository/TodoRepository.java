package org.zerock.day1_1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.day1_1.dto.Criteria;
import org.zerock.day1_1.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{
    List<Todo> findByTnoBetween(Long from, Long to); // 쿼리 메서드

    
    List<Todo> findByTitleContaining(String keyword);
    
    List<Todo> findByTitleContaining(String keyword,Pageable pageable);

    /* 쿼리메서드(JPQL) : Todo 클래스의 객체
     오더바이, 조건절 등은 필드 안정해주면 자동으로 id 기준.
    */
    @Query("select t from Todo t where t.tno between :from and :to order by t.tno desc")
    List<Todo> getTodos(@Param("from") Long from, @Param("to") Long to);

    // 패러미터를 객체로 묶어서 전달하는 방법.
    @Query("select t from Todo t where t.tno between :#{#cri.from} and :#{#cri.to} order by t desc")
    List<Todo> getTodos(@Param("cri")Criteria cri);

    // 아스테리크 지양하기 : tno, title 만 가져오기
    @Query("select t.tno, t.title from Todo t ")
    List<Object[]> getSimpleTodos();

    @Query("update Todo t set t.title = :title where t.tno = :tno")
    int updateTitle( @Param("title")String title, @Param("tno")Long tno);

    // Page 반환타입 : value + countQuery 제작.
    @Query(value="select t from Todo t order by t desc",countQuery = "select count(t) from Todo t" )
    Page<Todo> getPage(Pageable pageable);

    // 네이티브SQL : true 속성 주면 순수 sql 처리 가능. 반환타입은 오브젝트 배열로만 사용해야함.

}