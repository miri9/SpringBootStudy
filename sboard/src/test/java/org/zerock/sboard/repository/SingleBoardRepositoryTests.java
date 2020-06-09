package org.zerock.sboard.repository;

import java.util.stream.IntStream;
import java.time.LocalDateTime;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sboard.domain.SingleBoard;

@SpringBootTest
public class SingleBoardRepositoryTests {
    @Autowired
    private SingleBoardRepository repository;
    
    @Test
    public void testUpdateByQuery(){
        // 쿼리 애너테이션 이용한 업데이트 : 쿼리 1개
        int count = repository.updateTitle("Updated by Query", 300L, LocalDateTime.now());
        System.out.println("COUNT: " + count);

        /*
        Hibernate: 
        update
            single_board 
        set
            title=?,
            moddate=? 
        where
            sno=?
        COUNT: 1
        */
    }

    @Test
    public void testUpdate(){
        // JPA 이용한 업데이트 : 쿼리 총 3개
        Optional<SingleBoard> result 
          = repository.findById(300L);
        
        SingleBoard singleBoard = result.get();
        
        System.out.println(singleBoard);

        singleBoard.changeTitle("Updated............300");

        repository.save(singleBoard);
        /*
        Hibernate: 
        select
            singleboar0_.sno as sno1_0_0_,
            singleboar0_.moddate as moddate2_0_0_,
            singleboar0_.regdate as regdate3_0_0_,
            singleboar0_.content as content4_0_0_,
            singleboar0_.title as title5_0_0_,
            singleboar0_.writer as writer6_0_0_ 
        from
            single_board singleboar0_ 
        where
            singleboar0_.sno=?

        Hibernate: 
        select
            singleboar0_.sno as sno1_0_0_,
            singleboar0_.moddate as moddate2_0_0_,
            singleboar0_.regdate as regdate3_0_0_,
            singleboar0_.content as content4_0_0_,
            singleboar0_.title as title5_0_0_,
            singleboar0_.writer as writer6_0_0_ 
        from
            single_board singleboar0_ 
        where
            singleboar0_.sno=?

        Hibernate: 
        update
            single_board 
        set
            moddate=?,
            regdate=?,
            content=?,
            title=?,
            writer=? 
        where
            sno=?
        */
    }

    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,300).forEach(i->{

            SingleBoard board =
             SingleBoard.builder()
             .title("title.."+i)
             .content("content.."+i)
             .writer("user"+(i%10))
             .build();

             repository.save(board);

        });
    }
}