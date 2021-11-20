package com.abuksa.server.service;

import com.abuksa.server.model.JournalEntry;

import java.util.Collection;

public interface JournalEntryService {
    JournalEntry create(JournalEntry entry);
    Collection<JournalEntry> listAll(int limit);
    JournalEntry getById(Long id);
    JournalEntry update(JournalEntry entry);
    Boolean delete(Long id);
}
