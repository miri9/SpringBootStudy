
package org.zerock.dsl.repository;

import java.util.stream.IntStream;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.dsl.entity.Board;
import org.zerock.dsl.entity.QBoard;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository repository;



    @Test
    public void testSearchTitle(){

        String keyword = "5";

        // 동적쿼리 위한 Q도메인 클래스
        QBoard qBoard = QBoard.board;

        // "where"
        // BooleanBuilder : cascading builder fo Predicate expression. 즉 wherer = 일종의 필터 역할이다.
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        // "like" keyword
        BooleanExpression expression = qBoard.title.contains(keyword);
        
        // "where" board0_.title like ? escape '!'
        booleanBuilder.and(expression);

        Iterable<Board> result =  repository.findAll(booleanBuilder);
        result.forEach(board->System.out.println(board));

    }
    
    @Test
    public void makeDummies(){

        IntStream.rangeClosed(1,200).forEach(i -> {

            Board board = Board.builder()
              .title("title.."+i)
              .content("content..."+i)
              .writer("user"+ (i %10))
              .build();
            
            repository.save(board);

        });


    }

    @Test
    public void testSearch2(){
         //제목,내용,작성자
        String type = "tcw";

        // ["t","c","w"]
        String[] typeArr = type.split("");

        String keyword = "5";

        // 동적쿼리 위한 Q도메인 클래스
        QBoard qBoard = QBoard.board;

        // "where"
        BooleanBuilder outerBuilder = new BooleanBuilder();

        outerBuilder.and(qBoard.bno.gt(0L)); // bno > 0 AND
        
        
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        
        for(String t : typeArr){
            if(t.equals("t")){
                booleanBuilder.or(qBoard.title.contains(keyword));
            } else if (t.equals("c")){
                booleanBuilder.or(qBoard.content.contains(keyword));
            } else if (t.equals("w")){
                booleanBuilder.or(qBoard.writer.contains(keyword));
            }
        }

        outerBuilder.and(booleanBuilder);
        
        Iterable<Board> result =  repository.findAll(outerBuilder);
        result.forEach(board->System.out.println(board));

        /*
        where
        board0_.bno>?                           << outerBuilder
        and (                                     << booleanBuidler in outerBulider
            board0_.title like ? escape '!' 
            or board0_.content like ? escape '!' 
            or board0_.writer like ? escape '!'
        )
        */
    }

}