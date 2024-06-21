package com.vuongle.imaginepg.integrations;

import com.vuongle.imaginepg.ImaginePgApplication;
import com.vuongle.imaginepg.application.commands.CreateCategoryCommand;
import com.vuongle.imaginepg.application.commands.CreatePostCommand;
import com.vuongle.imaginepg.application.dto.CategoryDto;
import com.vuongle.imaginepg.application.dto.PostDto;
import com.vuongle.imaginepg.config.jwt.AuthTokenFilter;
import com.vuongle.imaginepg.domain.services.CategoryService;
import com.vuongle.imaginepg.domain.services.PostService;
import com.vuongle.imaginepg.interfaces.rest.v1.CategoryController;
import com.vuongle.imaginepg.shared.utils.JwtUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootTest(classes = ImaginePgApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PostIntegrationTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Autowired
  private AuthTokenFilter authTokenFilter;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private PostService postService;

  @Autowired
  private JwtUtils jwtUtils;

  private String token;

  @BeforeEach
  public void init() {
    token = jwtUtils.generateToken("admin");
  }


  @Test
  public void TestCreatePost() throws Exception {

    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + token);

    // create category first
    CreateCategoryCommand createCategoryCommand = new CreateCategoryCommand("test");
    HttpEntity<CreateCategoryCommand> createCategoryRequest = new HttpEntity<>(createCategoryCommand, headers);

    ResponseEntity<CategoryDto> categoryResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/api/category", createCategoryRequest, CategoryDto.class);

    Assertions.assertThat(categoryResponse.getStatusCode().is2xxSuccessful()).isTrue();
    CategoryDto categoryDto = categoryResponse.getBody();

    Assertions.assertThat(categoryDto).isNotNull();
    Assertions.assertThat(categoryDto.getName()).isEqualTo("test");

    CreatePostCommand createPostCommand = new CreatePostCommand();
    createPostCommand.setCategoryId(categoryDto.getId());
    createPostCommand.setTitle("test");
    createPostCommand.setDescription("description");
    createPostCommand.setContent("content");

    HttpEntity<CreatePostCommand> createPostRequest = new HttpEntity<>(createPostCommand, headers);
    ResponseEntity<PostDto> postResponse = testRestTemplate.postForEntity("http://localhost:" + port + "/api/post", createPostRequest, PostDto.class);

    Assertions.assertThat(postResponse.getStatusCode().is2xxSuccessful()).isTrue();
    PostDto postDto = postResponse.getBody();
    Assertions.assertThat(postDto).isNotNull();
    Assertions.assertThat(postDto.getTitle()).isEqualTo("test");

    // delete test data
    HttpEntity<CreatePostCommand> deleteRequest = new HttpEntity<>(headers);
    ResponseEntity<Void> deletePostResponse = testRestTemplate.exchange("http://localhost:" + port + "/api/admin/post/" + postDto.getId(), HttpMethod.DELETE, deleteRequest, Void.class);
    ResponseEntity<Void> deleteCategoryResponse = testRestTemplate.exchange("http://localhost:" + port + "/api/admin/category/" + categoryDto.getId(), HttpMethod.DELETE, deleteRequest, Void.class);

  }

}
