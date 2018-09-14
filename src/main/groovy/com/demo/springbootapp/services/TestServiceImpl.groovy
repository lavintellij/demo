package com.demo.springbootapp.services

import org.springframework.stereotype.Service

@Service
class TestServiceImpl implements TestService {
    @Override
    String helloWorld(boolean showString) {
        return showString ? "hello world" : "dude go away"
    }
}
