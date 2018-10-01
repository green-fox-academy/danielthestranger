package com.greenfoxacademy.restbackend.model.dto;

import com.greenfoxacademy.restbackend.model.Log;
import java.util.List;

public class LogsWithCount {
    private List<Log> entries;
    private Integer entryCount;

    public LogsWithCount() {
        if (entries == null)
            entryCount = 0;
        else
            entryCount = entries.size();
    }

    public List<Log> getEntries() {
        return entries;
    }

    public void setEntries(List<Log> entries) {
        this.entries = entries;
        entryCount = entries.size();
    }

    public Integer getEntryCount() {
        return entryCount;
    }
}
