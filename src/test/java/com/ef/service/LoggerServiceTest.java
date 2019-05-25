package com.ef.service;

import com.ef.repository.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

 public class LoggerServiceTest {

    @Autowired
    private LoggerService loggerService;

    @MockBean
    private LoggerRepository loggerRepository;

    // add tests here
}