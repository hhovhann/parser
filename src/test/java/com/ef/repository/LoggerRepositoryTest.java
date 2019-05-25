package com.ef.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class LoggerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LoggerRepository loggerRepository;

    // add tests here
}