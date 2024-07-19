package com.vuongle.imaginepg.application.dto.store;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCheckerResult implements Serializable {

    List<BookDto> outOfStocks;
}
