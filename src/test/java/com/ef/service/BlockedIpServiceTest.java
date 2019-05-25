package com.ef.service;

import com.ef.repository.BlockedIpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

 public class BlockedIpServiceTest {
    @Autowired
    private BlockedIpService blockedIpService;

    @MockBean
    private BlockedIpRepository blockedIpRepository;

    // add tests here
}