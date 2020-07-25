package org.zerock.asso1.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.asso1.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply,Long> {

    @Query(value = "select r, replyer from Reply r inner join r.replyer replyer where r.board.bno =:bno "
    , countQuery = "select count(r) from Reply r where r.board.bno =:bno")
    Page<Object[]> getReplyWithReBoard(@Param("bno")Long bno, org.springframework.data.domain.Pageable pageable);
    
    @EntityGraph(attributePaths = {"replyer","board"})
    @Query(value = "select r from Reply r where r.board.bno =:bno "
    , countQuery = "select count(r) from Reply r where r.board.bno =:bno")
    Page<Reply> getReplyWithReBoard2(@Param("bno")Long bno, org.springframework.data.domain.Pageable pageable);
    
}