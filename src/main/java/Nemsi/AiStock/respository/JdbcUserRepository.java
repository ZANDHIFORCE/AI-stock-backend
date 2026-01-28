package Nemsi.AiStock.respository;

import Nemsi.AiStock.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<User> findByUserid(String userid) {
        // users 테이블에서 userid로 조회
        String sql = "SELECT * FROM users WHERE userid = ?";
        return jdbcTemplate.query(sql, userRowMapper(), userid).stream().findAny();
    }

    @Override
    public void save(User user) {
        String sql = "INSERT INTO users (userid, \"password\", email, nickname, \"role\", createdd, updatedd, birthdd, gender, \"name\") " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, 
            user.getUserid(), 
            user.getPassword(), 
            user.getEmail(), 
            user.getNickname(), 
            user.getRole(),
            user.getCreatedd(),
            user.getUpdatedd(),
            user.getBirthdd(), 
            user.getGender(), 
            user.getName()
        );
    }

    private RowMapper<User> userRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserid(rs.getString("userid"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setNickname(rs.getString("nickname"));
            user.setRole(rs.getString("role"));
            user.setCreatedd(rs.getString("createdd"));
            user.setUpdatedd(rs.getString("updatedd"));
            user.setBirthdd(rs.getString("birthdd"));
            user.setGender(rs.getString("gender"));
            user.setName(rs.getString("name"));
            return user;
        };
    }
}
