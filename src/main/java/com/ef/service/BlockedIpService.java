package com.ef.service;

import com.ef.domain.BlockedIp;
import com.ef.repository.BlockedIpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockedIpService {

    private static Logger LOGGER = LoggerFactory.getLogger(BlockedIpService.class);
    private BlockedIpRepository blockedIpRepository;

    @Autowired
    public BlockedIpService(BlockedIpRepository blockedIpRepository) {
        this.blockedIpRepository = blockedIpRepository;
    }

    /***
     * Load all ip addresses into database with comments
     */
   public void loadAllIpAddressesToDatabaseWithComment(List<String> allIps) {
        // Save in blocked ip table
       allIps.forEach(ip -> blockedIpRepository.save(new BlockedIp(ip, "This ip address made to many requests")));
       LOGGER.info("Added all ips into blocked ip table with comments");
    }
}