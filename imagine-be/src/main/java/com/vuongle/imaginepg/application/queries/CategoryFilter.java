package com.vuongle.imaginepg.application.queries;

import com.vuongle.imaginepg.domain.constants.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryFilter implements Serializable {

    private String likeName;

    private CategoryType type;
}
