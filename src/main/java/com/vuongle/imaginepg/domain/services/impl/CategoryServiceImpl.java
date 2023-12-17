package com.vuongle.imaginepg.domain.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuongle.imaginepg.application.commands.CreateCategoryCommand;
import com.vuongle.imaginepg.application.dto.CategoryDto;
import com.vuongle.imaginepg.application.exceptions.NoPermissionException;
import com.vuongle.imaginepg.application.queries.CategoryFilter;
import com.vuongle.imaginepg.application.queries.PostFilter;
import com.vuongle.imaginepg.domain.entities.Category;
import com.vuongle.imaginepg.domain.entities.Post;
import com.vuongle.imaginepg.domain.repositories.BaseRepository;
import com.vuongle.imaginepg.domain.repositories.PostRepository;
import com.vuongle.imaginepg.domain.services.CategoryService;
import com.vuongle.imaginepg.infrastructure.specification.CategorySpecifications;
import com.vuongle.imaginepg.infrastructure.specification.PostSpecifications;
import com.vuongle.imaginepg.shared.utils.Context;
import com.vuongle.imaginepg.shared.utils.ObjectData;
import com.vuongle.imaginepg.shared.utils.ValidateResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final BaseRepository<Category> categoryRepository;
    private final PostRepository postRepository;

    private final ObjectMapper objectMapper;

    public CategoryServiceImpl(
            BaseRepository<Category> categoryRepository,
            PostRepository postRepository,
            ObjectMapper objectMapper
    ) {
        this.categoryRepository = categoryRepository;
        this.postRepository = postRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public CategoryDto getById(UUID id) {
        return getById(id, CategoryDto.class);
    }

    @Override
    public <R> R getById(UUID id, Class<R> classType) {
        Category category = categoryRepository.getById(id);

        // check permission
        if (!Context.hasModifyPermission() && !ValidateResource.isOwnResource(category, Category.class)) {
            throw new NoPermissionException("No permission");
        }

        return ObjectData.mapTo(category, classType);
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
        Category existedCategory = getById(id, Category.class);

        existedCategory.setName(command.getName());
        existedCategory.setSlug();

        existedCategory = categoryRepository.save(existedCategory);

        return ObjectData.mapTo(existedCategory, CategoryDto.class);
    }

    @Override
    public void delete(UUID id, boolean force) {

        Category category = getById(id, Category.class);

        if (category.getDeletedAt() != null) return;

        if (force) {
            // delete all post
            postRepository.deleteByCategoryId(category.getId());

            categoryRepository.deleteById(id);
            return;
        }

        category.setDeletedAt(Instant.now());
        categoryRepository.save(category);

        // delete all post
        PostFilter postFilter = new PostFilter();
        postFilter.setCategoryId(category.getId());

        Specification<Post> specification = PostSpecifications.withFilter(postFilter);
        List<Post> posts = postRepository.findAll(specification);

        for (var post: posts) {
            post.setDeletedAt(Instant.now());
        }

        postRepository.saveAllPosts(posts);
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
