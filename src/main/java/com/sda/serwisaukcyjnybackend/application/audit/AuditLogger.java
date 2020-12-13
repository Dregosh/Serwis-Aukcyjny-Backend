package com.sda.serwisaukcyjnybackend.application.audit;

import com.sda.serwisaukcyjnybackend.domain.audit.Audit;
import com.sda.serwisaukcyjnybackend.domain.audit.AuditEntry;
import com.sda.serwisaukcyjnybackend.domain.audit.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Log4j2
public class AuditLogger {
    private final AuditRepository auditRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void log(AuditEntry auditEntry) {
        log.info("Executed command {}, result, {}, payload: {}, duration: {}", auditEntry.getCommandName(),
                auditEntry.getCommandContent(), auditEntry.getResult(), auditEntry.getResult());
        var audit = new Audit(auditEntry.getCommandName(),
                auditEntry.getCommandContent(), auditEntry.getResult(), auditEntry.getDuration());
        auditRepository.save(audit);
    }

}
