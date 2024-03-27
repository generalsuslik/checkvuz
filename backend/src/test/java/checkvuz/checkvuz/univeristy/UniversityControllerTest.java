//package checkvuz.checkvuz.univeristy;
//
//import checkvuz.checkvuz.university.controller.UniversityController;
//import checkvuz.checkvuz.university.entity.University;
//import checkvuz.checkvuz.university.service.UniversityService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@ExtendWith(MockitoExtension.class)
//public class UniversityControllerTest {
//
//    @Mock
//    private UniversityService universityService;
//
//    @InjectMocks
//    private UniversityController universityController;
//
//    private MockMvc mockMvc;
//
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    void setup() {
//        MockitoAnnotations.initMocks(this);
//        mockMvc = MockMvcBuilders.standaloneSetup(universityController)
//                .build();
//        objectMapper = new ObjectMapper();
//    }
//
//    @Test
//    void createUniversity() throws Exception {
//        University university = new University();
//        university.setId(10L);
//        university.setTitle("university_title1");
//        university.setExpandedTitle("expanded_title_1");
//        university.setDescription("description1");
//        university.setFoundingYear(1900);
//
//        String universityJson = objectMapper.writeValueAsString(university);
//
//        MockHttpServletResponse response = mockMvc.perform(
//                post("http://lcalhost:8080/api/universities")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(universityJson)
//        ).andReturn().getResponse();
//
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
//
//        verify(universityService, times(1)).createUniversity(university);
//    }
//}
