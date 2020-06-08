package org.zerock.day1_1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.zerock.day1_1.entity.Todo;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.zerock.day1_1.dto.Criteria;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {
    
    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void testClass(){
        System.out.println(todoRepository.getClass().getName());
        log.info("---------------------");
    }
    
    @Test
    public void insertDummies(){
        
        for (int i = 1; i <=100; i++) {
            Todo todo = Todo.builder().title("title" + i).writer("user" + (i % 10)).build();
            System.out.println(todoRepository.save(todo));
        }
    }

    @Test
    public void readTest() {
        
        Optional<Todo> result = todoRepository.findById(55L);

        if(result.isPresent()){
            System.out.println(result.get());
        }
    }

    @Test
    public void testDelete(){
        
        Long tno =1000L;
        todoRepository.deleteById(tno);

        /* select => delete.
        바라보는 대상은 데이터베이스 자체가 아니라, 메모리.
        내가 가진 엔티티 객체 중 1000번인 녀석이 있어야 delete 가능. 
        => select 로 자기가 가진 메모리 찾아보고, 삭제할 땐 자기 메모리 상에서도 + DB 것도 삭제하는 방식임. 상태 동기화를 위함

        jpa 에 작업하면 메모리상에서 먼저 작업하고, db 에 작업을 하는 것임.
        */
    }
    
    @Test
    public void testUpdate(){
        Todo todo = Todo.builder().tno(55L).title("UPDATED TITLE").writer("GUEST").build();

        todoRepository.save(todo);
    }

    /* 페이징 : 파라미터는 Pageable 인터페이스 . => PageRequest 구현체 : new() 대신 오브 패턴 (static of()) 사용하여 객체 생성해야함.
       리턴타입은 Optional => List<Todo>(순수목록) , Page<Todo>(카운팅포함) , Slice<Todo> (앞의 두개를 자주 사용.)
        메서드 findAll()
    */
    @Test
    public void testPaging(){
        //정렬
        Sort sort1 = Sort.by("tno").descending();
        Sort sort2 = Sort.by("title").descending();
        Sort all = sort1.and(sort2);

        // Pageable 의 경우 페이지가 0부터 시작.
        Pageable pageable = PageRequest.of(1,10,sort2);

        //A page is a sublist of a list of objects. 객체들 담긴 부분list. 
        Page<Todo> result = todoRepository.findAll(pageable);

        System.out.println(result); // Page 2 of 10 containing org.zerock.day1_1.entity.Todo instances
        System.out.println("TOTAL PAGE: "+result.getTotalPages()); // TOTAL PAGE: 10
        System.out.println("TOTAL ELEMENT: "+result.getTotalElements()); // TOTAL ELEMENT: 100 = 레코드 개수 확인

        // get() 는 Stream<Todo> 를 반환한다.
        result.get().forEach(todo -> System.out.println(todo.toString()));

    }

    /* 검색 :  Query 메서드 이용 : Query creation from method names */
    //1.
    @Test
    public void testQuery1(){
        List<Todo> list = todoRepository.findByTnoBetween(500L, 510L);
        System.out.println(list);
    }

    //2.
    @Test
    public void testQuery2(){
        String keyword= "55";
        List<Todo> list = todoRepository.findByTitleContaining(keyword);
        list.forEach(todo->System.out.println(todo));
    }

    //query 메서드 단점 : 쿼리 = 즉 셀렉트만 가능함. 
    // 1+2 : 검색 + 정렬
    @Test
    public void testQuery3(){
        // 제목에 5 가 들어간 레코드를 tno desc 순으로 정렬해 1페이지 보여준다.
        String keyword= "5";

        Sort sort = Sort.by("tno").descending();
        Pageable pageable = PageRequest.of(0, 20,sort);

        List<Todo> list = todoRepository.findByTitleContaining(keyword,pageable);
        list.forEach(todo->System.out.println(todo));
    }

    // 0603
    
    @Test
    public void testQuery4(){

        Criteria cri = new Criteria(10L,30L);
        List<Todo> list = todoRepository.getTodos(cri);

        list.forEach(todo -> System.out.println(todo));
    }

    @Test
    public void testQuery5(){

        List<Object[]> list = todoRepository.getSimpleTodos();

        list.forEach(arr -> System.out.println(Arrays.toString(arr)));

    }

    /* ava.lang.IllegalStateException:
       org.hibernate.hql.internal.QueryExecutionRequestException:
     Not supported for DML operations update  .. 예외 해결 : @ modifying , @ transactional 추가

     => 쿼리의 동기화 위해 transactional 추가 필요.
     => 쿼리 한번에 작업 완료.
     */
    @Transactional
    @Modifying
    @Test
    public void testQuery6(){
        int count = todoRepository.updateTitle("updated title", 998L);
        System.out.println(count);
    }

    /*Pageable 테스트2 : jpql */
    @Test
    public void testPaging2(){
        // 정렬
        Sort sort1 = Sort.by("tno").descending();
        Sort sort2 = Sort.by("title").ascending();

        Sort all = sort1.and(sort2);

        // 이 때 페이지는 0부터 시작한다.
        Pageable pageable = PageRequest.of(1, 10,all);

        Page<Todo> result = todoRepository.getPage(pageable);

        result.forEach(page->System.out.println(page));


    }
}