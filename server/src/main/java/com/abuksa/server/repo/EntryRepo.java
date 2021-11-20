package com.abuksa.server.repo;

import com.abuksa.server.model.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepo extends JpaRepository<Entry, Long> {
}
