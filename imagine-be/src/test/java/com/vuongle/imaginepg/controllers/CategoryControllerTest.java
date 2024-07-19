package com.vuongle.imaginepg.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuongle.imaginepg.application.commands.CreateCategoryCommand;
import com.vuongle.imaginepg.application.dto.CategoryDto;
import com.vuongle.imaginepg.application.queries.CategoryFilter;
import com.vuongle.imaginepg.config.jwt.AuthTokenFilter;
import com.vuongle.imaginepg.domain.constants.CategoryType;
import com.vuongle.imaginepg.domain.services.CategoryService;
import com.vuongle.imaginepg.interfaces.rest.v1.CategoryController;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(CategoryController.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthTokenFilter authTokenFilter;

  @MockBean
  private CategoryService categoryService;

  @Autowired
  private ObjectMapper objectMapper;

  @Test
//  @WithMockUser(username = "test", password = "test", roles = {"USER"})
  public void TestGetAllCategories() throws Exception {
    Page<CategoryDto> categories = new PageImpl<>(List.of(
      new CategoryDto(),
      new CategoryDto(),
      new CategoryDto()
    ));

    CategoryFilter filter = new CategoryFilter();
    filter.setType(CategoryType.QUESTION);

    Pageable pageable = PageRequest.of(0, 5);

    Mockito.when(categoryService.getPageable(filter, pageable)).thenReturn(categories);

    mockMvc.perform(MockMvcRequestBuilders
        .get("/api/category")
        .contentType(MediaType.APPLICATION_JSON)
        .param("page", "0")
        .param("size", "5")
        .param("type", "QUESTION")
    ).andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(3)));
  }

  @Test
  public void TestCreateCategory() throws Exception {
    CategoryDto category = new CategoryDto();
    category.setName("test");

    CreateCategoryCommand createCommand = new CreateCategoryCommand("test");

    Mockito.when(categoryService.create(ArgumentMatchers.any(CreateCategoryCommand.class)))
        .thenReturn(category);

    mockMvc.perform(MockMvcRequestBuilders.post("/api/category")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(createCommand)))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("test")));
  }
}
