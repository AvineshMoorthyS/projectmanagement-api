package com.projectmanagement.repository;

import com.projectmanagement.dto.internal.UserDto;
import com.projectmanagement.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<UserModel, String> {

    @Query("SELECT new com.projectmanagement.dto.internal.UserDto(u.userId, u.username, u.active) FROM UserModel u WHERE u.userId = :id AND u.active = 'Y'")
    UserDto findFirstByUserId(String id);

    @Query("SELECT new com.projectmanagement.dto.internal.UserDto(u.userId, u.username, u.active) FROM UserModel u WHERE u.username = :username AND u.active = 'Y'")
    UserDto findFirstByUsername(String username);

}
