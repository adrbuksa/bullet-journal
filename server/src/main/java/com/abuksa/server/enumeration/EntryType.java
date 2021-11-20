package com.abuksa.server.enumeration;

public enum EntryType {
    NOTE("NOTE"), TASK("TASK"), EVEMT("EVENT");

    private final String type;

    EntryType(String type) { this.type = type; }

    public String getType() { return this.type; }
}
