package com.ef.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class BlockedIpRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BlockedIpRepository blockedIpRepository;

    // add tests here
}