package com.vuongle.imaginepg.domain.entities.store;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
@Table(name = "book_author")
public class BookAuthor implements Serializable {

    @Id
    private UUID id;

    private String fullName;

    private String email;

    private String phone;

    private String address;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Book> books;
}
