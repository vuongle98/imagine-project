package com.vuongle.imaginepg.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vuongle.imaginepg.application.commands.LoginCommand;
import com.vuongle.imaginepg.application.commands.RegisterCommand;
import com.vuongle.imaginepg.application.dto.JwtResponse;
import com.vuongle.imaginepg.application.dto.UserDto;
import com.vuongle.imaginepg.application.dto.UserProfile;
import com.vuongle.imaginepg.config.SecurityConfig;
import com.vuongle.imaginepg.config.jwt.AuthTokenFilter;
import com.vuongle.imaginepg.domain.services.AuthService;
import com.vuongle.imaginepg.domain.services.impl.AuthServiceImpl;
import com.vuongle.imaginepg.interfaces.rest.v1.AuthController;
import jakarta.servlet.ServletContext;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(AuthController.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@WebAppConfiguration
public class AuthControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AuthTokenFilter authTokenFilter;

  @MockBean
  private AuthService authService;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  public void setup() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
  }


  @Test
  public void testLogin() throws Exception {

    LoginCommand command = new LoginCommand("test", "test");
    JwtResponse response = new JwtResponse("a", "", new UserDto());

    when(authService.login(any(LoginCommand.class))).thenReturn(response);

    String value = objectMapper.writeValueAsString(command);

    ResultActions result = mockMvc.perform(post("/api/auth/token")
      .contentType(MediaType.APPLICATION_JSON)
      .content(value));

    result.andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.token", CoreMatchers.is("a")));
  }

  @Test
  public void testRegister() throws Exception {
    RegisterCommand command = new RegisterCommand("test", "test", "test", "test");

    UserDto user = new UserDto();
    user.setUsername("test");
    user.setEmail("test");

    when(authService.register(any(RegisterCommand.class))).thenReturn(user);

    ResultActions result = mockMvc.perform(post("/api/auth/register")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(command)));

    result.andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is("test")));
  }

  @Test
  public void testVerifyUser() throws Exception {
    UserProfile profile = new UserProfile();
    profile.setUsername("test");
    profile.setEmail("test@gmail.com");

    when(authService.verify()).thenReturn(profile);

    mockMvc.perform(get("/api/auth/verify")
      .contentType(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is("test")));
  }

  @Test
  public void givenWac_whenServletContext_thenItProvidesGreetController() {
    ServletContext servletContext = webApplicationContext.getServletContext();

    Assertions.assertNotNull(servletContext);
    Assertions.assertTrue(servletContext instanceof MockServletContext);
    Assertions.assertNotNull(webApplicationContext.getBean("authController"));
  }

}
