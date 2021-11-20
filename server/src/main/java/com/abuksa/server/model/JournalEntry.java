package com.abuksa.server.model;

import com.abuksa.server.enumeration.EntryStatus;
import com.abuksa.server.enumeration.EntryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JournalEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "Text cannot be empty!")
    private String text;
    @NotEmpty(message = "Type cannot be empty!")
    private EntryType type;
    @NotEmpty(message = "Date cannot be empty!")
    private LocalDate date;
    private EntryStatus status;
    private Boolean isImportant;
}
