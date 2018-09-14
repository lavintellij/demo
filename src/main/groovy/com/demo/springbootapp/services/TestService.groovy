package com.demo.springbootapp.services

import org.springframework.stereotype.Service

@Service
interface TestService {
    String helloWorld(boolean showString)
}