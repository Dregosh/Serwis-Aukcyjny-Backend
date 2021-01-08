package com.sda.serwisaukcyjnybackend.application.auth.premium;

import com.sda.serwisaukcyjnybackend.domain.user.PremiumOrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
@RequiredArgsConstructor
@Log4j2
public class PremiumOrderCleaner {
    private final PremiumOrderRepository premiumOrderRepository;

    @Scheduled(cron = "${app.premium.cleanerCron}")
    public void deleteOldOrders() {
        LocalDateTime checkTime = LocalDateTime.now().minusMinutes(30);
        var orders = premiumOrderRepository.findAllByOrderDateBefore(checkTime);
        log.info("SCHEDULED DELETE OLD ORDERS - found {} orders to delete", orders.size());
        premiumOrderRepository.deleteAll(orders);
    }
}
