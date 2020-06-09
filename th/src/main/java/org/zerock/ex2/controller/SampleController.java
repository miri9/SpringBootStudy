
package org.zerock.ex2.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.ex2.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {

    @GetMapping({"/ex1"})
    public void ex1(Model model){
        model.addAttribute("msg", "Hello World");
        model.addAttribute("msg2", "SUCCESS");

        model.addAttribute("sum", 100);
    }

    @GetMapping("/ex2")
    public void ex2(Model model){

        log.info("GET /ex2");

        SampleDTO dto = SampleDTO.builder().sno(10L).title("sample").regdate(LocalDateTime.of(2020,12,25,14,29,00)).build();

        model.addAttribute("dto", dto);
    }

    @GetMapping({"/ex3"})
    public void ex3(Model model){
        
        SampleDTO dto = SampleDTO.builder().sno(10L).title("sample").regdate(LocalDateTime.of(2020,12,25,14,29,00)).build();

        model.addAttribute("dto", dto);

        model.addAttribute("arr", new String[]{"AAA","BBB","CCC"});

        model.addAttribute("result", "result");

    }

    @GetMapping({"/exList", "/exLink", "/exTime"})
    public void exList(Model model){

        List<SampleDTO> dtoList = IntStream.rangeClosed(1,10).mapToObj(i ->
            SampleDTO.builder().sno((long)i).title("Title.."+i).writer("writer"+i).regdate(LocalDateTime.now()).build()
        ).collect(Collectors.toList());

        model.addAttribute("dtoList", dtoList);
    }

    @GetMapping("/exLayout")
    public void exLayout(Model model){

        model.addAttribute("msg","Hello World");

    }





}









