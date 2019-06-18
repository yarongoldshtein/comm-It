package com.server.commIt.repository;

import com.server.commIt.model.Quote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface quoteRepository extends JpaRepository<Quote, String> {}
