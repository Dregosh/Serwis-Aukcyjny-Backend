package com.sda.serwisaukcyjnybackend.view.auth;

import com.sda.serwisaukcyjnybackend.domain.category.CategoryRepository;
import com.sda.serwisaukcyjnybackend.domain.user.UserRepository;
import com.sda.serwisaukcyjnybackend.view.category.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Value("${app.auction.maxPromoted}")
    protected int maxPromoted;

    @Transactional(readOnly = true)
    public UserExistData checkIfUserExist(String email, String displayName) {
        return new UserExistData(
                userRepository.existsByEmail(email),
                userRepository.existsByDisplayName(displayName));
    }

    @Transactional(readOnly = true)
    public EditUserDTO getEditUserData(Long userId) {
        return UserMapper.mapToEditUserDTO(
                this.userRepository.findById(userId).orElseThrow());
    }

    @Transactional(readOnly = true)
    public CreateAuctionUserDTO getCreateAuctionUserDTO(Long userId) {
        var categories = categoryRepository.findAll().stream()
                .map(CategoryMapper::mapToCategoryDTO)
                .collect(Collectors.toUnmodifiableList());
        return new CreateAuctionUserDTO(userRepository.getOne(userId)
                .canCreatePromotedAuction(maxPromoted), categories);
    }
}
