package com.vuongle.imaginepg.domain.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuongle.imaginepg.application.commands.CreateCategoryCommand;
import com.vuongle.imaginepg.application.dto.CategoryDto;
import com.vuongle.imaginepg.application.queries.CategoryFilter;
import com.vuongle.imaginepg.domain.entities.Category;
import com.vuongle.imaginepg.domain.repositories.CategoryRepository;
import com.vuongle.imaginepg.domain.services.CategoryService;
import com.vuongle.imaginepg.infrastructure.specification.CategorySpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final ObjectMapper objectMapper;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository,
            ObjectMapper objectMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public CategoryDto getById(UUID id) {
        Category category = categoryRepository.getById(id);
        return ObjectData.mapTo(category, CategoryDto.class);
    }

    @Override
    public CategoryDto create(CreateCategoryCommand command) {
        Category category = objectMapper.convertValue(command, Category.class);
        category.setSlug();
        category.setUser(Context.getUser());
        category = categoryRepository.save(category);

        // map to dto
        return ObjectData.mapTo(category, CategoryDto.class);
    }

    @Override
    public CategoryDto update(UUID id, CreateCategoryCommand command) {
        Category existedCategory = categoryRepository.getById(id);

        existedCategory.setName(command.getName());
        existedCategory.setSlug();

        existedCategory = categoryRepository.save(existedCategory);

        return ObjectData.mapTo(existedCategory, CategoryDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        if (force) {
            categoryRepository.deleteById(id);
            return;
        }

        Category category = categoryRepository.getById(id);

        category.setDeletedAt(Instant.now());
        categoryRepository.save(category);

        // delete all post
    }

    @Override
    public Page<CategoryDto> getAll(CategoryFilter filter, Pageable pageable) {
        Specification<Category> specification = CategorySpecifications.withFilter(filter);
        Page<Category> categoryPage = categoryRepository.findAll(specification, pageable);

        return categoryPage.map(category -> ObjectData.mapTo(category, CategoryDto.class));
    }

    @Override
    public List<CategoryDto> getAll(CategoryFilter filter) {
        Specification<Category> specification = CategorySpecifications.withFilter(filter);
        List<Category> categoryList = categoryRepository.findAll(specification);

        return ObjectData.mapListTo(categoryList, CategoryDto.class);
    }


}
