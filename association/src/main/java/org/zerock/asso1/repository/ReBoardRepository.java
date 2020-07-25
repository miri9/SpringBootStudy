package org.zerock.asso1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.asso1.domain.ReBoard;

public interface ReBoardRepository extends JpaRepository<ReBoard, Long>{

    // =========== ENTITY GRAPH ========================
    
    // 댓글 내용을 포함한 게시물 리스트를 페이지 타입으로 가져오기
    @EntityGraph(attributePaths = {"replySet","writer","replySet.replyer"})
    @Query(value ="select b from ReBoard b ", countQuery="select count(b) from ReBoard b")
    Page<ReBoard> ex3(Pageable pageable);
    

    // 게시물의 작성자, 댓글, 댓글작성자, 첨부파일을 페이지 타입으로 가져오기
    @EntityGraph(attributePaths = "files") // ReBord 의 참조관계 필드 files
    @Query(value = "select b from ReBoard b",
            countQuery = "select count(b) from ReBoard b")
    Page<ReBoard> getWithFiles(Pageable pageable);
    // =========== END  ENTITY GRAPH ====================



    // =========== 일반 SPRING DATA JPA ============================


    // 게시물 리스트 조회 : 게시물과 Member writer 조인 필요
    @Query("select b, b.writer from ReBoard b inner join b.writer order by b desc")
    List<ReBoard> getList();

     /*Page<ReBoard> getListPage(Pageable pageable); => 
      java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to org.zerock.asso1.domain.ReBoard
     : 페이지 타입을 받을 때 셀렉트 col 이 여러개면, 오브젝트 배열로 받아야한다.
    */
    @Query(
        value="select b, b.writer from ReBoard b inner join b.writer order by b desc", 
        countQuery = "select count(b) from ReBoard b")
    Page<Object[]> getListPage(Pageable pageable);


    /*
      - jspl 의 카운트는 int 아니라 long 타입.
      - 해당 객체가 이미 참조하는 필드를 조인해 가져올 때는, on 조건 없이 가능하다.
        다만 이 경우 Reboard 는 댓글 필드를 가지고 있지 아니하므로 on 조건 필요.
    */
    @Query(
        value="select b, count(r) from ReBoard b left outer join Reply r on r.board = b group by b", 
        countQuery = "select count(b) from ReBoard b")
    Page<Object[]> getListWithReply(Pageable pageble);



    /* - 보드의 writer : board + member = 1:1 관계.
       @Query("select b from ReBoard b inner join b.writer w") : 셀렉트에서 member 로딩 안되므로 아래처럼 변경
       @Query("select b,w from ReBoard b inner join b.writer w") : 댓글(board 에 포함 x) 조인 미포함. 아래처럼 변경

       - 글 하나당 카운트를 원하므로, 그룹 바이가 필요 ( 기준 :  ReBoard )
    */
    // 게시물과 리플 개수, 그리고 글쓴이(mname) 를 포함한 리스트를 페이지 타입으로 가져오기
    @Query("select b,count(r),w from ReBoard b inner join b.writer w left outer join Reply r on r.board = b group by b")
    Page<Object[]> ex1(Pageable pageable);
    
}