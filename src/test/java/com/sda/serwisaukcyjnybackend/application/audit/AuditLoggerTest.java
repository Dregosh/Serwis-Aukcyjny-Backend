package com.sda.serwisaukcyjnybackend.application.audit;

import com.sda.serwisaukcyjnybackend.domain.audit.Audit;
import com.sda.serwisaukcyjnybackend.domain.audit.AuditEntry;
import com.sda.serwisaukcyjnybackend.domain.audit.AuditRepository;
import com.sda.serwisaukcyjnybackend.domain.audit.AuditResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditLoggerTest {
    @Mock
    AuditRepository auditRepository;
    @InjectMocks
    AuditLogger auditLogger;

    @Test
    void shouldLog() {
        //given
        var auditEntry = new AuditEntry("test",
                "tested", AuditResult.SUCCESS, 134L);
        when(auditRepository.save(any())).thenReturn(new Audit());

        //when && then
        auditLogger.log(auditEntry);
    }

}