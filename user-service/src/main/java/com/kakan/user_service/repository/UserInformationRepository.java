package com.kakan.user_service.repository;

import com.kakan.user_service.pojo.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {
    UserInformation findByAccountId(Integer userId);
    boolean existsByPhone(String phone);
    UserInformation findUserInformationByUserId(int userId);
}
