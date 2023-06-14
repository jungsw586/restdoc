package com.example.restdoc.presentation.api.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.relaxedQueryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.example.restdoc.domain.entity.User;
import com.example.restdoc.domain.interactor.repository.UserRepository;
import com.example.restdoc.domain.service.model.command.CreateUserCommand;
import com.example.restdoc.domain.service.model.command.UpdateUserCommand;
import com.example.restdoc.infra.data.mybatis.vo.UserVo;
import com.example.restdoc.presentation.api.controller.request.CreateUserRequest;
import com.example.restdoc.presentation.api.controller.request.UpdateUserRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.RequestFieldsSnippet;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.restdocs.request.PathParametersSnippet;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
@DisplayName("User API")
class UserControllerTest {
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UserRepository userRepository;

  private final PathParametersSnippet PathVariableUserId = pathParameters(
      parameterWithName("id").description("사용자 ID")
  );

  private final RequestFieldsSnippet CreateUserRequest = requestFields(
      fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름")
  );

  private final RequestFieldsSnippet UpdateUserRequest = requestFields(
      fieldWithPath("id").type(JsonFieldType.STRING).description("사용자 아이디"),
      fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름")
  );

  private final ResponseFieldsSnippet UserResponse = responseFields(
      fieldWithPath("id").type(JsonFieldType.STRING).description("사용자 아이디"),
      fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름")
  );

  private String requestBody(Object object) throws Exception {
    return objectMapper.writeValueAsString(object);
  }

  private String responseBody(Object object) throws Exception {
    return objectMapper.writeValueAsString(object);
  }

  @BeforeEach
  void init(RestDocumentationContextProvider restDocumentationContextProvider) {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .addFilters(new CharacterEncodingFilter("UTF-8", true))
        .apply(
            documentationConfiguration(restDocumentationContextProvider)
              .operationPreprocessors()
              .withRequestDefaults(
                  prettyPrint()
              )
              .withResponseDefaults(
                  prettyPrint()
              )
        )
        .alwaysDo(print())
        .build();
  }

  @Test
  void getAll() throws Exception {
    //given
    User Jeff = new User(UUID.randomUUID().toString(), "jeff");
    User Justin = new User(UUID.randomUUID().toString(), "justin");
    User Kate = new User(UUID.randomUUID().toString(), "kate");

    UserVo jeff = new UserVo(Jeff.getId(), Jeff.getName());
    UserVo justin = new UserVo(Justin.getId(), Justin.getName());
    UserVo kate = new UserVo(Kate.getId(), Kate.getName());

    when(userRepository.findAll())
        .thenReturn(Stream.of(
            jeff,
            justin,
            kate
        ));

    //when
    ResultActions performed = mockMvc.perform(
        get("/users")
            .contentType(MediaType.APPLICATION_JSON)
    );

    //then
    performed
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name").value(equalTo("jeff")))
        .andExpect(jsonPath("$[1].name").value(equalTo("justin")))
        .andDo(
            //rest doc
            MockMvcRestDocumentation.document(
                "user.getAll",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("사용자 이름")
                )
            )
        )
        .andDo(
            //open api 3.0
            MockMvcRestDocumentationWrapper.document(
                "user.getAll",
                responseFields(
                    fieldWithPath("[].id").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("[].name").type(JsonFieldType.STRING).description("사용자 이름")
                )
            )
        );
  }

  @Test
  void getOneById() throws Exception {
    //given
    User Jeff = new User(UUID.randomUUID().toString(), "jeff");

    when(userRepository.findOneBy(Jeff.getId()))
        .thenReturn(Optional.of(new UserVo(Jeff.getId(), Jeff.getName())));

    //when
    ResultActions performed = mockMvc.perform(
        get("/users/{id}", Jeff.getId())
            .contentType(MediaType.APPLICATION_JSON)
    );

    //then
    performed
        .andExpect(status().isOk())
        .andExpect(content().string(this.responseBody(Jeff)))
        .andExpect(jsonPath("$.id").value(equalTo(Jeff.getId())))
        .andExpect(jsonPath("$.name").value(equalTo(Jeff.getName())))
        .andDo(
            //rest doc
            MockMvcRestDocumentation.document(
                "user.getOneById",
                PathVariableUserId,
                UserResponse
            )
        )
        .andDo(
            //open api 3.0
            MockMvcRestDocumentationWrapper.document(
                "user.getOneById",
                PathVariableUserId,
                UserResponse
            )
        );
  }

  @Test
  void create() throws Exception {
    //given
    CreateUserRequest request = new CreateUserRequest("chloe");
    CreateUserCommand command = new CreateUserCommand(request.getName());
    final User user = new User(UUID.randomUUID().toString(), command.getName());
    UserVo vo = new UserVo(user.getId(), user.getName());
    when(userRepository.save(any()))
        .thenReturn(vo)
        .thenThrow();

    //when
    ResultActions performed = mockMvc.perform(
        post("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.requestBody(request))
    );

    //then
    performed
        .andExpect(status().isOk())
        .andExpect(content().string(this.responseBody(user)))
        .andExpect(jsonPath("$.id").isString())
        .andExpect(jsonPath("$.name").value(equalTo(request.getName())))
        .andDo(
            //rest doc
            MockMvcRestDocumentation.document(
                "user.create",
                CreateUserRequest,
                UserResponse
            )
        )
        .andDo(
            //open api 3.0
            MockMvcRestDocumentationWrapper.document(
                "user.create",
                CreateUserRequest,
                UserResponse
            )
        );
  }

  @Test
  void update() throws Exception {
    //given
    User Jeff = new User(UUID.randomUUID().toString(), "jeff");

    UpdateUserRequest request = new UpdateUserRequest(Jeff.getId(), "SW");
    UpdateUserCommand command = new UpdateUserCommand(request.getId(), request.getName());
    User user = new User(command.getId(), command.getName());
    UserVo vo = new UserVo(user.getId(), user.getName());
    when(userRepository.findOneBy(Jeff.getId()))
        .thenReturn(Optional.of(new UserVo(Jeff.getId(), Jeff.getName())));
    when(userRepository.save(vo))
        .thenReturn(vo);

    //when
    ResultActions performed = mockMvc.perform(
        put("/users")
            .contentType(MediaType.APPLICATION_JSON)
            .content(this.requestBody(request))
    );

    //then
    performed
        .andExpect(status().isOk())
        .andExpect(content().string(this.responseBody(user)))
        .andExpect(jsonPath("$.id").value(equalTo(user.getId())))
        .andExpect(jsonPath("$.name").value(equalTo(user.getName())))
        .andDo(
            //rest doc
            MockMvcRestDocumentation.document(
                "user.update",
                UpdateUserRequest,
                UserResponse
            )
        )
        .andDo(
            //open api 3.0
            MockMvcRestDocumentationWrapper.document(
                "user.update",
                UpdateUserRequest,
                UserResponse
            )
        );
  }

  @Test
  void deleteOneById() throws Exception {
    //given
    User Jeff = new User(UUID.randomUUID().toString(), "jeff");
    when(userRepository.findOneBy(Jeff.getId()))
        .thenReturn(Optional.of(new UserVo(Jeff.getId(), Jeff.getName())));

    //when
    ResultActions delete = mockMvc.perform(
        delete("/users/{id}", Jeff.getId())
            .contentType(MediaType.APPLICATION_JSON)
    );

    //then
    delete
        .andExpect(status().isOk())
        .andDo(
            //rest doc
            MockMvcRestDocumentation.document(
                "user.deleteOneById",
                PathVariableUserId
            )
        )
        .andDo(
            //open api 3.0
            MockMvcRestDocumentationWrapper.document(
                "user.deleteOneById",
                PathVariableUserId
            )
        );
  }
}