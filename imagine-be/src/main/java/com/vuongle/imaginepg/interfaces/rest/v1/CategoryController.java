package com.vuongle.imaginepg.interfaces.rest.v1;

import com.vuongle.imaginepg.application.commands.CreateCategoryCommand;
import com.vuongle.imaginepg.application.dto.CategoryDto;
import com.vuongle.imaginepg.application.queries.CategoryFilter;
import com.vuongle.imaginepg.domain.services.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService
    ) {
        this.categoryService = categoryService;
    }

    @GetMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<Page<CategoryDto>> searchCategory(
            CategoryFilter filter,
            Pageable pageable
    ) {
        Page<CategoryDto> categoryPageable = categoryService.getAll(filter, pageable);

        return ResponseEntity.ok(categoryPageable);
    }

    @PostMapping
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CategoryDto> create(
            @RequestBody CreateCategoryCommand command
    ) {
        CategoryDto category = categoryService.create(command);

        return ResponseEntity.ok(category);
    }

    @PutMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CategoryDto> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody CreateCategoryCommand command
    ) {
        CategoryDto updatedCategory = categoryService.update(id, command);

        return ResponseEntity.ok(updatedCategory);
    }

    @GetMapping("/{id}")
    @SecurityRequirement(
            name = "Bearer authentication"
    )
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'MODERATOR')")
    public ResponseEntity<CategoryDto> getById(
            @PathVariable(value = "id") UUID id
    ) {
        CategoryDto category = categoryService.getById(id);

        return ResponseEntity.ok(category);
    }
}
