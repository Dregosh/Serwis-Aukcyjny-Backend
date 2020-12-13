package com.sda.serwisaukcyjnybackend.domain.audit;

import lombok.Value;

@Value
public class AuditEntry {
    String commandName;
    String commandContent;
    AuditResult result;
    Long duration;
}
