package com.projectmanagement.helper;

import com.projectmanagement.dto.internal.UserDto;
import com.projectmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class User {

    @Autowired
    private UserRepository userRepository;

    public String getUserId(){
        String subject = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthToken) {
            Jwt jwt = jwtAuthToken.getToken();
            String[] userId = jwt.getSubject().split("\\|");
            subject = userId[1];
        }
        return subject;
    }

    public String getUserName(){
        User user = new User();
        UserDto userDto = userRepository.findFirstByUserId(user.getUserId());
        return userDto.getUsername();
    }

}
