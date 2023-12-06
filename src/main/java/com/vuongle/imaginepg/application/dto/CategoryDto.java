package com.vuongle.imaginepg.application.dto;

import com.vuongle.imaginepg.domain.entities.Post;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto implements Serializable {

    private UUID id;

    private String name;

    private String slug;

    private UserDto user;

}
