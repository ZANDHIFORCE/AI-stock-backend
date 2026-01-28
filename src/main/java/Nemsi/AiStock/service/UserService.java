package Nemsi.AiStock.service;

import Nemsi.AiStock.domain.User;
import Nemsi.AiStock.respository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(String userid, String password, String email, String nickname, String name, String birthdd, String gender) {
        User user = new User();
        user.setUserid(userid);
        // 비밀번호 암호화 필수!
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(email);
        user.setNickname(nickname);
        user.setName(name);

        // birthdd 형식 처리 (YYYYMMDD 입력 시 YYYY-MM-DD로 변환)
        if (birthdd != null && birthdd.length() == 8 && !birthdd.contains("-")) {
            birthdd = birthdd.substring(0, 4) + "-" + birthdd.substring(4, 6) + "-" + birthdd.substring(6, 8);
        }
        user.setBirthdd(birthdd);
        
        user.setGender(gender);
        user.setRole("USER");
        
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        user.setCreatedd(now);
        user.setUpdatedd(now);
        
        userRepository.save(user);
    }
}
