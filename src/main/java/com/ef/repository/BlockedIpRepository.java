package com.ef.repository;

import com.ef.domain.BlockedIp;
import org.springframework.data.repository.CrudRepository;

public interface BlockedIpRepository extends CrudRepository<BlockedIp, Long> {
}
