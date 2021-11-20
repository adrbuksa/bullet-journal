package com.abuksa.server.resource;

import com.abuksa.server.model.CustomResponse;
import com.abuksa.server.model.Entry;
import com.abuksa.server.service.EntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("entry")
@RequiredArgsConstructor
public class EntryResource {
    private final EntryService entryService;

    @GetMapping("/list")
    public ResponseEntity<CustomResponse> getEntries() {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(now())
                        .data(of("entries", entryService.listAll(100)))
                        .message("All entries retrieved.")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CustomResponse> getEntries(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(now())
                        .data(of("entry", entryService.getById(id)))
                        .message("Entry retrieved.")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<CustomResponse> saveEntry(@RequestBody @Valid Entry entry) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(now())
                        .data(of("entry", entryService.create(entry)))
                        .message("Entry created.")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()
        );
    }

    @PutMapping("/update")
    public ResponseEntity<CustomResponse> updateEntry(@RequestBody @Valid Entry entry) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(now())
                        .data(of("entry", entryService.update(entry)))
                        .message("Entry updated.")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CustomResponse> deleteEntry(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(now())
                        .data(of("entry", entryService.delete(id)))
                        .message("Entry deleted.")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }
}
