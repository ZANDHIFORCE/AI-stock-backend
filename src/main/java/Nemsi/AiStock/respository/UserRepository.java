package Nemsi.AiStock.respository;

import Nemsi.AiStock.domain.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByUserid(String userid);
    void save(User user);
}
