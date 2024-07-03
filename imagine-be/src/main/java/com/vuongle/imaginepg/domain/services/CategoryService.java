package com.vuongle.imaginepg.domain.services;

import com.vuongle.imaginepg.application.commands.CreateCategoryCommand;
import com.vuongle.imaginepg.application.dto.CategoryDto;
import com.vuongle.imaginepg.application.queries.CategoryFilter;

public interface CategoryService extends BaseService<CategoryDto, CreateCategoryCommand, CategoryFilter> {

}
