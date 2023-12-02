package ru.itis.repository;

import ru.itis.model.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface AccountRepository {
    void save(User user) throws SQLException;

    Boolean login(String username, String password, User user) throws SQLException;
    List selectAll() throws SQLException;
    void deleteUser(Long userId) throws SQLException;
    Boolean userExists(String username) throws SQLException;

}
