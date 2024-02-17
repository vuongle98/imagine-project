package com.vuongle.imaginepg.config;

import com.vuongle.imaginepg.domain.entities.User;
import com.vuongle.imaginepg.shared.utils.Context;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class UserAuditing implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {

        User user = Context.getUser();

        if (Objects.nonNull(user)) {
            return Optional.of(user.getUsername());
        }

        return Optional.empty();
    }
}
