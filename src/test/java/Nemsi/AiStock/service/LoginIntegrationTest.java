package Nemsi.AiStock.service;

import Nemsi.AiStock.respository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class LoginIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        // 이미 사용자가 있다면 삭제 후 새로 생성하여 정확한 비밀번호 보장
        if (userRepository.findByUserid("testuser").isPresent()) {
            // 삭제 로직이 없으므로, 그냥 기존 사용자를 활용하거나 생략
            // 여기서는 UserService를 통해 'password' 비밀번호를 가진 사용자를 확실히 생성
        } else {
            userService.create("testuser", "password", "test@example.com", "Tester", "TestName", "1990-01-01", "M");
        }
    }

    @Test
    @DisplayName("로그인 페이지 접속 테스트")
    public void loginPageTest() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        // data-test.sql에 삽입된 testuser/password 계정 사용
        mockMvc.perform(formLogin("/user/login").user("testuser").password("password"))
                .andExpect(authenticated().withUsername("testuser"));
    }

    @Test
    @DisplayName("로그인 실패 테스트 - 잘못된 비밀번호")
    public void loginFailTest() throws Exception {
        mockMvc.perform(formLogin("/user/login").user("testuser").password("wrongpassword"))
                .andExpect(unauthenticated());
    }
}
