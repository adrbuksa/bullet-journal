package com.abuksa.server.service;

import com.abuksa.server.model.Entry;

import java.util.Collection;

public interface EntryService {
    Entry create(Entry entry);
    Collection<Entry> listAll(int limit);
    Entry getById(Long id);
    Entry update(Entry entry);
    Boolean delete(Long id);
}
