package com.vuongle.imaginepg.application.dto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStatistic implements Serializable {

    private long totalRows;

}
