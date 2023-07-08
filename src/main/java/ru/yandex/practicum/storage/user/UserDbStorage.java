package ru.yandex.practicum.storage.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.model.user.User;

import java.util.List;

@Repository
@Qualifier
public class UserDbStorage implements UserStorage {
    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User saveUser(User user) {
        String sqlQuery = "insert into users(user_id, email, login, name, birthday, " +
                " friends_id)" + " values (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sqlQuery, user.getId(), user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday(),
                user.getFriends().size());
        return user;
    }

    @Override
    public void deleteUser(User user) {
        String sqlQuery = "delete from users where id = ?";
        jdbcTemplate.update(sqlQuery, user.getId());
    }

    @Override
    public User updateUser(User user) {
        String sqlQuery = "update users set user_id = ?,  email = ? login = ?," +
                " name = ?, birthday = ?, " +
                " friends_id = ?";
        jdbcTemplate.update(sqlQuery, user.getId(), user.getEmail(), user.getLogin(), user.getName(),
                user.getBirthday(),
                user.getFriends().size());
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
