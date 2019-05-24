package com.ef.repository;

import com.ef.domain.AccessLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggerRepository extends JpaRepository<AccessLog, Long> {

}
