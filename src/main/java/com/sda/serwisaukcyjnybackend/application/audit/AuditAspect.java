package com.sda.serwisaukcyjnybackend.application.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sda.serwisaukcyjnybackend.application.command.Command;
import com.sda.serwisaukcyjnybackend.application.command.CommandResult;
import com.sda.serwisaukcyjnybackend.application.command.CommandResultType;
import com.sda.serwisaukcyjnybackend.domain.audit.AuditEntry;
import com.sda.serwisaukcyjnybackend.domain.audit.AuditResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Log4j2
public class AuditAspect {
    private final AuditLogger auditLogger;
    private final ObjectMapper objectMapper;

    @Around("execution(* com.sda.serwisaukcyjnybackend.application.command.CommandHandler.handle(..)) && args(command)")
    public Object log(ProceedingJoinPoint joinPoint, Command command) throws Throwable {
        long startTime = System.currentTimeMillis();
        AuditResult auditResult = AuditResult.SUCCESS;
        try {
            Object result = joinPoint.proceed();
            CommandResult commandResult = (CommandResult) result;
            if (commandResult.getResultType() == CommandResultType.FAILED) {
                auditResult = AuditResult.FAILED;
            }
            return result;
        } catch (Throwable throwable) {
            log.error(throwable.getStackTrace());
            auditResult = AuditResult.FAILED;
            throw throwable;
        } finally {
            long endTime = System.currentTimeMillis();
            AuditEntry auditEntry = new AuditEntry(command.getClass().getSimpleName(), getCommandContentAsString(command),
                    auditResult, endTime - startTime);
            auditLogger.log(auditEntry);
        }
    }

    private String getCommandContentAsString(Command command) {
        try {
            return objectMapper.writeValueAsString(command);
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }
}
