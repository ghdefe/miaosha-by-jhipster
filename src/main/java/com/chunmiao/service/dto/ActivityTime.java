package com.chunmiao.service.dto;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


public class ActivityTime {
    private ZonedDateTime start;

    private ZonedDateTime end;

    @Override
    public String toString() {
        return "ActivityTime{" +
            "start=" + start +
            ", end=" + end +
            '}';
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }
}
