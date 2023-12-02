package ru.itis.repository;

import ru.itis.model.User;

import java.sql.SQLException;
import java.util.List;

public interface AccountRepository {
    void save(User user) throws SQLException;

    Boolean login(String username, String password, User user) throws SQLException;
    List selectAll() throws SQLException;
    void deleteUser(Long userId) throws SQLException;
}
