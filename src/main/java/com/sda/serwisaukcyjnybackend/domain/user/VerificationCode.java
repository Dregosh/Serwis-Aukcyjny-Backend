package com.sda.serwisaukcyjnybackend.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.EntityGraph;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Data
@Entity
@NamedEntityGraph(
        name = "verification-with-user",
        attributeNodes = {
                @NamedAttributeNode("user")
        }
)
public class VerificationCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public VerificationCode(User user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.code = UUID.randomUUID().toString();
    }

    public String getUserEmail() {
        return user.getEmail();
    }

    public String getUserDisplayName() {
        return user.getDisplayName();
    }
}
