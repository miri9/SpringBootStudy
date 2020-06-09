package org.zerock.sboard.service;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.sboard.dto.SingleBoardDTO;

@SpringBootTest
public class SingleBoardServiceTests {
    @Autowired
    private SingleBoardService service;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,300).forEach(i->{
            SingleBoardDTO dto =  SingleBoardDTO.builder()
            .title("title..."+i)
            .content("content..."+i)
            .writer("user"+(i%10))
            .build();

            System.out.println("==============");
            System.out.println(service.register(dto));
        
        });
    }
}