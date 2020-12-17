package com.sda.serwisaukcyjnybackend.view.edituser;

import com.sda.serwisaukcyjnybackend.domain.user.User;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCode;
import com.sda.serwisaukcyjnybackend.domain.user.VerificationCodeRepository;
import com.sda.serwisaukcyjnybackend.domain.user.event.ChangePasswordRequested;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EditUserService {
    private final UserRepository userRepository;
    private final VerificationCodeRepository verificationCodeRepository;
    private final ApplicationEventPublisher eventPublisher;

    public EditUserDTO getEditUserData(Long userId) {
        return EditUserMapper.mapToEditUserDTO(
                this.userRepository.findById(userId).orElseThrow());
    }

    public void sendEmailToChangePasswordForm(Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow();
        VerificationCode verificationCode = new VerificationCode(user);
        this.verificationCodeRepository.save(verificationCode);
        eventPublisher.publishEvent(new ChangePasswordRequested(
                user.getDisplayName(), verificationCode.getCode(), user.getEmail()));
    }
}
