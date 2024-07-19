package com.vuongle.imaginepg.application.commands;

import com.vuongle.imaginepg.application.dto.store.BookOrderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanBookCommand implements Serializable {

    private List<BookOrderDto> orderInfo;

}
