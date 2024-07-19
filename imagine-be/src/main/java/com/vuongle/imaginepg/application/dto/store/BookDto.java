package com.vuongle.imaginepg.application.dto.store;

import com.vuongle.imaginepg.application.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto implements Serializable {

    private UUID id;

    private String title;

    private String description;

    private Instant publishedAt;

    private int availability;

    private long price;

    private int edition;

    private UserDto publisher;

    private BookAuthorDto author;

}
