package com.ef.repository;

import com.ef.domain.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface LoggerRepository extends JpaRepository<AccessLog, Long> {
    @Query("SELECT log.ipAddress FROM AccessLog log " +
            "WHERE log.startDate > :startDate AND log.startDate > :endDate " +
            "GROUP BY log.ipAddress HAVING COUNT(log.ipAddress) > :threshold")
    List<String> findIpAddresses(@Param("startDate") LocalDateTime startDate,
                                 @Param("endDate") LocalDateTime endDate,
                                 @Param("threshold") Long threshold);

    @Query("SELECT log.request FROM AccessLog log WHERE log.ipAddress = :ipAddress")
    List<String> findRequestsByIpAddress(String ipAddress);
}