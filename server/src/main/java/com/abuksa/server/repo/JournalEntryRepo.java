package com.abuksa.server.repo;

import com.abuksa.server.model.JournalEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalEntryRepo extends JpaRepository<JournalEntry, Long> {
}
