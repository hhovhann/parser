package com.ef.service;

import com.ef.domain.BlockedIp;
import com.ef.repository.BlockedIpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        allIps.forEach(ip -> {
            BlockedIp blockedIp = blockedIpRepository.findByIpAddress(ip);
            // To avoid ip duplication
            if (Objects.isNull(blockedIp)) {
                blockedIpRepository.save(new BlockedIp(ip, "This ip address made to many requests"));
                LOGGER.info("Ip address {} stored into blocked ip table.: ", ip);
            } else {
                LOGGER.info("Ip address {} already stored into blocked ip table.: ", ip);
            }
        });
    }
}