package com.vuongle.imaginepg.shared.utils;

import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.exceptions.DataNotFoundException;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.exceptions.UserNotFoundException;

import java.lang.reflect.Field;
import java.util.Objects;

public class ValidateResource {

    public static <T> boolean isOwnResource(T dataSource, Class<T> classType) {
        if (Objects.isNull(dataSource)) {
            throw new DataNotFoundException(classType.getSimpleName() + " not found");
        }

        if (Objects.isNull(Context.getUser())) throw new UserNotFoundException("Logged in user not found");


        try {
            Field creatorField = dataSource.getClass().getDeclaredField("creator");
            creatorField.setAccessible(true);

            UserDto creator = (UserDto) creatorField.get(dataSource);
            if (!Context.getUser().getId().equals(creator.getId())) {
                throw new NoPermissionException("logged in user must be owner");
            }

        } catch (NoSuchFieldException e) {
            throw new DataNotFoundException(classType.getName() + " not found");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


        return true;
    }
}
