package com.abuksa.server.service.implementation;

import com.abuksa.server.model.Entry;
import com.abuksa.server.repo.EntryRepo;
import com.abuksa.server.service.EntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EntryServiceImpl implements EntryService {
    private final EntryRepo entryRepo;

    @Override
    public Entry create(Entry entry) {
        log.info("Saving new entry: {}", entry.getText());
        return entryRepo.save(entry);
    }

    @Override
    public Collection<Entry> listAll(int limit) {
        log.info("Fetching list of all entries");
        return entryRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Entry getById(Long id) {
        log.info("Fetching entry by id: {}", id);
        return entryRepo.findById(id).get();
    }

    @Override
    public Entry update(Entry entry) {
        log.info("Updating entry: {}", entry.getText());
        return entryRepo.save(entry);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting entry by id: {}", id);
        entryRepo.deleteById(id);
        return Boolean.TRUE;
    }
}
