package ru.itis.repository;

import ru.itis.model.User;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public interface SettingsRepository {
    List getUserPass(String username) throws SQLException;
    void changeUsernamePassword(User user, HttpSession session) throws SQLException;
    List getUserInformation(HttpSession session) throws SQLException;
    void changeUserInformations(User user, HttpSession session) throws SQLException;

}
