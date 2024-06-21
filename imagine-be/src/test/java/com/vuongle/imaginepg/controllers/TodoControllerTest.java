package com.vuongle.imaginepg.controllers;

import com.vuongle.imaginepg.application.dto.TaskDto;
import com.vuongle.imaginepg.application.queries.TaskFilter;
import com.vuongle.imaginepg.config.SecurityConfig;
import com.vuongle.imaginepg.config.jwt.AuthTokenFilter;
import com.vuongle.imaginepg.domain.constants.TaskColor;
import com.vuongle.imaginepg.domain.repositories.TaskRepository;
import com.vuongle.imaginepg.domain.services.TodoService;
import com.vuongle.imaginepg.interfaces.rest.v1.CategoryController;
import com.vuongle.imaginepg.interfaces.rest.v1.TodoController;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = TodoController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
public class TodoControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthTokenFilter authTokenFilter;

  @MockBean
  private TodoService todoService;

  @Test
//  @WithMockUser(username = "test", password = "test")
  public void TestGetAllTodos() throws Exception {

    Page<TaskDto> tasks = new PageImpl<>(List.of(new TaskDto(), new TaskDto()));


    TaskFilter filter = new TaskFilter();
    filter.setColor(TaskColor.BLUE);

    Pageable pageable = Pageable.ofSize(5).withPage(1);
    Mockito.when(todoService.getAll(filter, pageable)).thenReturn(tasks);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/task")
        .contentType(MediaType.APPLICATION_JSON)
        .param("color", "BLUE")
        .param("page", "1")
        .param("size", "5"))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.content", Matchers.hasSize(2)));
  }

}
