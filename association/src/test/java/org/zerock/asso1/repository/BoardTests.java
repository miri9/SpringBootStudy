package org.zerock.asso1.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.asso1.domain.AttachFile;
import org.zerock.asso1.domain.Member;
import org.zerock.asso1.domain.ReBoard;
import org.zerock.asso1.domain.Reply;

@SpringBootTest
public class BoardTests {
    
    @Autowired
    private ReBoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private AttachFileRepository fileRepository;

    @Test
    public void insertDummyReply(){

        IntStream.rangeClosed(1,1000).forEach(i -> {

            Long bno = (long)(Math.random()* 500) +1;
            ReBoard board = ReBoard.builder().bno(bno).build();

            String mid = "u" + (int)(Math.random()* 100);
            Member member = Member.builder().mid(mid).build();

            Reply reply = Reply.builder().replyText("Reply...."+i)
                .replyer(member)
                .board(board)
                .build();

            System.out.println(replyRepository.save(reply));    


        });
    }

    @Test
    public void testGetListWithReply(){

        Pageable pageble = PageRequest.of(0,10,Sort.Direction.DESC,"bno");

        Page<Object[]> result = boardRepository.getListWithReply(pageble);

        result.stream().forEach(arr -> {

            ReBoard board = (ReBoard)arr[0];
            long count = (long)arr[1];

            System.out.println(board);
            System.out.println(count);
            System.out.println("-------------------------");

        });
    }

    @Test
    public void testGetReplyWithReBoard(){

        Pageable pageable 
        =PageRequest.of(0,10,Sort.Direction.ASC,"rno");
        Long bno = 500L;

        Page<Object[]> result = replyRepository.getReplyWithReBoard(bno, pageable);

        result.stream().forEach(arr -> {

            Reply reply = (Reply)arr[0];

            System.out.println(reply);
            System.out.println(reply.getReplyer());
            System.out.println("------------------");
        });
    }


    @Transactional
    @Test
    public void testGetReplyWithReBoard2(){

        Pageable pageable 
        =PageRequest.of(0,10,Sort.Direction.ASC,"rno");
        Long bno = 500L;

        Page<Reply> result = replyRepository.getReplyWithReBoard2(bno, pageable);

        result.stream().forEach(reply -> {

            System.out.println(reply);
            System.out.println(reply.getBoard());
            System.out.println(reply.getReplyer());
            System.out.println("------------------");
        });
    }

    //----------------------------------------------
    @Test
    public void testEx1(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.ex1(pageable);

        result.stream().forEach(arr -> {

            ReBoard board = (ReBoard)arr[0];
            long count = (long)arr[1];

            System.out.println(board.getBno() +": " + board.getTitle()+"[ "+ count +"]" +": " + board.getWriter());
        });

    }

    @Test
    public void testEx2(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("rno").descending());

        Long bno = 497L;

        Page<Reply> result = replyRepository.ex2(bno, pageable);

        result.stream().forEach(r -> {
            System.out.println(r.getRno());
            System.out.println(r.getReplyText());
            System.out.println(r.getReplyer());
            System.out.println("-----------------------");
        });
    }

    @Test
    public void testEx3(){

        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<ReBoard> result = boardRepository.ex3(pageable);

        result.stream().forEach(board -> {


            System.out.println(board.getBno() +": " + board.getTitle());
            System.out.println(board.getWriter());

            System.out.println("--------------------------------");
        });
    }

    @Test
    public void insertDummyFiles(){

        IntStream.rangeClosed(1, 1000).forEach(i -> {

            Long bno = (long)(Math.random()* 500) + 1;
            ReBoard board = ReBoard.builder().bno(bno).build();

            AttachFile file = AttachFile.builder().board(board)
            .fileName("file"+i).build();

            System.out.println(fileRepository.save(file));
        });
    }

    @Test
    public void testWithFiles(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<ReBoard> result = boardRepository.getWithFiles(pageable);

        result.stream().forEach(board ->{

            System.out.println(board);
            System.out.println(board.getWriter());
            System.out.println("1---------------------");
            Set<AttachFile> files = board.getFiles();

            files.forEach(file -> System.out.println(file));

            Set<Reply> replies = board.getReplies();

            replies.forEach(reply ->{ 
                System.out.println(reply);
                System.out.println(reply.getReplyer());
            });

            System.out.println("------------------------------------");

        });

    }

}