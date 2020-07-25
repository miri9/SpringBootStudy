package org.zerock.asso1.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.asso1.domain.Member;
import org.zerock.asso1.domain.ReBoard;

@SpringBootTest
public class MemberRepositoryTests {
    
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReBoardRepository boardRepository;

    @Test
    public void insertDummiesBoard(){
        /*
            원래는 memberRepository 에서 멤버 얻어와야 하지만 - 테스트용으로 id 만 설정한 멤버객체를 생성한다.
                
            코드 실행하면
            1. jpa 가 db 안정성 보장을 위해 member 테이블을 select
            +
            2. insert
            forEach 한번 당...총 두번의 쿼리를 날린다.
        */

        IntStream.rangeClosed(1, 500).forEach(i -> {

            Member writer = Member.builder().mid("u" + (i % 100)).build();

            ReBoard board = ReBoard.builder()
            .title("Title.........." + i)
            .content("Content..............." + i)
            .writer(writer)
            .build();

            // 출력 : ReBoard(bno=501, title=Title.......1, content=Content......1, writer=Member(mid=u1, mpw=null, mname=null))
            System.out.println(boardRepository.save(board));

        });

    }

    @Test
    public void insertDummies(){

        IntStream.rangeClosed(0,100).forEach(i -> {

            Member member = Member.builder()
            .mid("u"+i)
            .mpw("u"+i)
            .mname("USER-"+i)
            .build();

            System.out.println(memberRepository.save(member));

        });

    }

    @Transactional
    @Test
    public void testRead(){

        Long bno = 500L;

        Optional<ReBoard> result = boardRepository.findById(bno);

        if(result.isPresent()){
            ReBoard board = result.get();
            System.out.println(board.getBno());
            System.out.println(board.getTitle());
            System.out.println(board.getWriter().getMname());
        }

        /* 1. fetch 설정 X + 트랜잭션 X + board.getWriter().getMid() 까지 테스트 
            : outer join 로 writer = member 를 로딩한다.
                => @ManyToOne(fetch = FetchType.LAZY) : 해당 필드가 필요할 때만 그 필드가 속한 테이블을 조인해서 가져오도록 변경.

               2. board.getWriter().getMName() 테스트
                : writer.mid = member.mid 는 board 에게 있어 FK 관계이므로 조인 없이 참조 가능.
                : 그러나 mname 을 가져와야 할 경우 select "쿼리를 두 번" 날리게 된다.
                : 이 때, 예외 발생 
                  org.hibernate.LazyInitializationException: 
                  could not initialize proxy [org.zerock.asso_myself.domian.Member#u0] - no Session
                  => 테스트 코드에 "트랜잭션" 걸어 해결한다.
        */
        /* 트랜잭션 추가 후 두번째 테스트
        Hibernate: 보드 셀렉트
                select
                    reboard0_.bno as bno1_1_0_,
                    reboard0_.content as content2_1_0_,
                    reboard0_.title as title3_1_0_,
                    reboard0_.writer as writer4_1_0_ 
                from
                    re_board reboard0_ 
                where
                    reboard0_.bno=?
        출력
        200
        Title.......200
        u0
        Hibernate: 멤버 셀렉트 for 출력:board.getWriter().getMname()
                    ==> FK (참조관계) 가 아니므로 따로 셀렉트하여 로딩할 필요 O
                select
                    member0_.mid as mid1_0_0_,
                    member0_.mname as mname2_0_0_,
                    member0_.mpw as mpw3_0_0_ 
                from
                    member member0_ 
                where
                    member0_.mid=?
        출력
        USER-0 
        */

        /*
        한계 : 쿼리를 하나로 줄일 수 없을까?
        */
    }


    // 게시물 리스트 가져오기 (1) : 페이지 타입 + findAll
    @Transactional
    @Test
    public void testPage(){

        Pageable pageable
         = PageRequest.of(0,10,Sort.Direction.DESC,"bno");

        Page<ReBoard> result 
         = boardRepository.findAll(pageable);
        
        result.get().forEach(b -> {
            System.out.println(b.getBno());
            System.out.println(b.getTitle());
            System.out.println(b.getWriter().getMid());
            System.out.println(b.getWriter().getMname());


            System.out.println("----------------");

              /*
                1. eager 로딩 + b.getWriter().getMid() 까지 테스트
                : 보트 1번 + 멤버 11 번 총 12번의 셀렉트 쿼리를 날림.
                => lazy 로딩 : 셀렉트 쿼리 하나로 해결.
                2. b.getWriter().getMname() 까지 테스트
                : 셀렉트 쿼리 두 개
                    Hibernate: 
                    select
                        reboard0_.bno as bno1_1_,
                        reboard0_.content as content2_1_,
                        reboard0_.title as title3_1_,
                        reboard0_.writer as writer4_1_ 
                    from
                        re_board reboard0_ 
                    order by
                        reboard0_.bno desc limit ?
                    ==> testSimpleList 로 이어짐.
                */
        }); 

    }


    // 게시물 리스트 가져오기 (2) : ReBoard 타입 + @query
    @Test
    public void testSimpleList(){
        //getList : @Query("select b,b.writer from ReBoard b inner join b.writer order by b desc ")
        List<ReBoard> list = boardRepository.getList();
        
        for(int i = 0; i <10; i++){

            ReBoard board = list.get(i);

            System.out.println(board.getBno());
            System.out.println(board.getTitle());
            System.out.println(board.getWriter().getMid());
            System.out.println(board.getWriter().getMname());
            System.out.println("-------------------------");
            
        }

        /*  Hibernate: 
        select
        reboard0_.bno as bno1_1_0_,
        member1_.mid as mid1_0_1_,
        reboard0_.content as content2_1_0_,
        reboard0_.title as title3_1_0_,
        reboard0_.writer as writer4_1_0_,
        member1_.mname as mname2_0_1_,
        member1_.mpw as mpw3_0_1_ 
        from
        re_board reboard0_ 
        inner join
        member member1_ 
        on reboard0_.writer=member1_.mid 
        order by
        reboard0_.bno desc
        700
        Title.......200
        u0
        USER-0
        ----------------------------
        */
    }

    // 게시물 리스트 가져오기 (3) : 페이지 타입 + @query
    @Test
    public void testPageList(){

        Sort sort = Sort.by("bno").descending();
        Pageable pageable = PageRequest.of(0,10,sort);

        Page<Object[]> result = boardRepository.getListPage(pageable);

        // [0] board , [1] board.writer
        result.get().forEach(arr -> {

            ReBoard board = (ReBoard)arr[0];
            //Member member = (Member)arr[1]; ==> 참조관계 + 조인된 상태이므로,
            // board 객체 만으로 member 필드 참조 가능.
        

            System.out.println(board.getBno());
            System.out.println(board.getTitle());
            System.out.println(board.getWriter().getMid());
            System.out.println(board.getWriter().getMname());
            System.out.println("-------------------------");
        });

        /*
        Hibernate: 
        select
            reboard0_.bno as bno1_1_0_,
            member1_.mid as mid1_0_1_,
            reboard0_.content as content2_1_0_,
            reboard0_.title as title3_1_0_,
            reboard0_.writer as writer4_1_0_,
            member1_.mname as mname2_0_1_,
            member1_.mpw as mpw3_0_1_ 
        from
            re_board reboard0_ 
        inner join
            member member1_ 
                on reboard0_.writer=member1_.mid 
        */

    }

}