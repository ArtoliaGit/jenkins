package com.bsoft.examination.web;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        log.debug("123");
        log.info("123");
        return "hello world";
    }
}
