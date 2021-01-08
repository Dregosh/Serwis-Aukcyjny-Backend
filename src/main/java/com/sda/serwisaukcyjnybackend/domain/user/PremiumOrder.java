package com.sda.serwisaukcyjnybackend.domain.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class PremiumOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private String orderId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "order_date")
    private LocalDateTime orderDate;

    public PremiumOrder(String orderId, User user) {
        this.orderId = orderId;
        this.user = user;
        this.orderDate = LocalDateTime.now();
    }
}
