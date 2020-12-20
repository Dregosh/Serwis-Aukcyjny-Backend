package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserExistData checkIfUserExist(String email, String displayName) {
        return new UserExistData(
                userRepository.existsByEmail(email),
                userRepository.existsByDisplayName(displayName));
    }

    public EditUserDTO getEditUserData(Long userId) {
        return EditUserMapper.mapToEditUserDTO(
                this.userRepository.findById(userId).orElseThrow());
    }
}
