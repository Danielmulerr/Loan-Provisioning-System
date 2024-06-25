package com.example.loanprovisioning.dto;

import java.sql.Timestamp;

public record IncreaseDateRange(
        Timestamp firstRangeStart,
        Timestamp firstRangeEnd,
        Timestamp secondRangeStart,
        Timestamp secondRangeEnd
) {
}