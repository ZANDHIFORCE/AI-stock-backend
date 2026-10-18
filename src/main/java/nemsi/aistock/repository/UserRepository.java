package nemsi.aistock.repository;

import nemsi.aistock.domain.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserid(String userid);
    void save(User user);
}
