package com.sda.serwisaukcyjnybackend.application.auth.premium;

import com.sda.serwisaukcyjnybackend.domain.user.AccountType;
import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.PremiumAccountExpired;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Log4j2
public class PremiumAccountsCleaner {
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Scheduled(cron = "${app.premium.cleanerCron}")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void setToNormalAccountType() {
        LocalDate checkTime = LocalDate.now();
        var users = userRepository.findAllByAccountTypeAndPremiumAccountExpirationAfter(AccountType.PREMIUM, checkTime);
        log.info("SCHEDULED DELETE PREMIUM ACCOUNTS - found {} premium accounts to delete", users.size());
        for (User user : users) {
            updateUserStatus(user);
            eventPublisher.publishEvent(new PremiumAccountExpired(user.getDisplayName(), user.getEmail()));
        }
    }

    private void updateUserStatus(User user) {
        user.setNormalAccount();
        try {
            userRepository.save(user);
        } catch (OptimisticLockException e) {
            user = userRepository.getOne(user.getId());
            user.setNormalAccount();
            userRepository.save(user);
        }
    }
}
