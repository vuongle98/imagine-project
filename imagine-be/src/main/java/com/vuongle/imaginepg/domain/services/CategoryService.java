package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateCategoryCommand;
import com.vuongle.imaginepg.application.dto.CategoryDto;
import com.vuongle.imaginepg.application.queries.CategoryFilter;
import java.util.UUID;

public interface CategoryService extends BaseService<CategoryDto, CreateCategoryCommand, CategoryFilter> {

}
