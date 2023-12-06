package com.vuongle.imaginepg.shared.utils;

import com.vuongle.imaginepg.application.exceptions.UserNotFoundException;
import com.vuongle.imaginepg.domain.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Objects;

public class Context {

    public static User getUser() {
        if (Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
//            throw new UserNotFoundException("User not found");
            return null;
        }

        Authentication context = SecurityContextHolder.getContext().getAuthentication();

        if (!(context.getPrincipal() instanceof User)) {
//            throw new UserNotFoundException("User not found");
            return null;
        }

        return (User) context.getPrincipal();
    }
}
