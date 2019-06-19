package com.server.commIt.repository;

import com.server.commIt.model.QuoteLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface quoteLogRepository extends JpaRepository<QuoteLog, String> {}
