package com.demo.springbootapp.controllers

import com.demo.springbootapp.services.TestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HelloController {
    @Autowired
    TestService testService

    @RequestMapping("/")
    @ResponseBody
    String hello() {
        return testService.helloWorld(true)
    }
}
