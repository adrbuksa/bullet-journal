package com.abuksa.server.enumeration;

public enum EntryStatus {
    COMPLETED("COMPLETED"), NOT_COMPLETED("NOT_COMPLETED");

    private final String status;

    EntryStatus(String status) { this.status = status; }

    public String getType() { return this.status; }
}
