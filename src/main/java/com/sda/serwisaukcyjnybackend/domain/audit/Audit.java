package com.sda.serwisaukcyjnybackend.domain.audit;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "command_name")
    private String commandName;
    @Column(name = "command_content")
    private String commandContent;
    @Enumerated(EnumType.STRING)
    private AuditResult result;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private Long duration;

    public Audit(String commandName, String commandContent,
                 AuditResult result, Long duration) {
        this.commandContent = commandContent;
        this.commandName = commandName;
        this.result = result;
        this.duration = duration;
        this.createdAt = LocalDateTime.now();
    }
}
