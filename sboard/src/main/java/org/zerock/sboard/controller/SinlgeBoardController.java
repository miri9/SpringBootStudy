package org.zerock.sboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.sboard.service.SingleBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/sboard")
public class SinlgeBoardController {

    private final SingleBoardService service;

    @GetMapping("/register")
    public void register(){
        log.info("/register........get");

        
    }
}