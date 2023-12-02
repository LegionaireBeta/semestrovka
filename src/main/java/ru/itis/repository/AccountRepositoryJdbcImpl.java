package ru.itis.repository;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.model.User;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountRepositoryJdbcImpl implements AccountRepository {

    private DataSource dataSource;

    private static final String SQL_INSERT = "INSERT INTO users(first_name, last_name, age, gender, country, city, username, password_hash) VALUES ";
    private static final String SQL_SELECT_ALL = "SELECT * FROM users OFFSET 1";
    private static final String SQL_VIEW_USER = "SELECT * FROM users WHERE username = ?";
    private static final String SQL_DELETE_USER = "DELETE FROM users WHERE user_id = ?";
    private static final String SQL_SELECT_ID_BY_USERNAME = "SELECT user_id FROM users WHERE username = ?";
    private static final String SQL_INSERT_UUID = "INSERT INTO logins(id, uuid) VALUES(?, ?)";

    private PasswordEncoder passwordEncoder;

    public AccountRepositoryJdbcImpl(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void save(User user) throws SQLException {
        String sql = SQL_INSERT + "(?, ?, ?, ?, ?, ?, ?, ?)";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, user.getFirstNameOfUser());
        preparedStatement.setString(2, user.getSurnameOfUser());
        preparedStatement.setDate(3, new java.sql.Date(user.getAgeOfUser().getTime()));
        preparedStatement.setString(4, user.getGenderOfUser());
        preparedStatement.setString(5, user.getCountryOfUser());
        preparedStatement.setString(6, user.getCityOfUser());
        preparedStatement.setString(7, user.getUsernameOfUser());
        preparedStatement.setString(8, user.getPasswordOfUser());

        preparedStatement.executeUpdate();
    }

    public Boolean login(String username, String password, User user) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_VIEW_USER);

        preparedStatement.setString(1, user.getUsernameOfUser());
        ResultSet resultSet = preparedStatement.executeQuery();

        String userAcc = "";
        String passAcc = "";

        while (resultSet.next()) {
            userAcc = resultSet.getString("username");
            passAcc = resultSet.getString("password_hash");
        }

        if (userAcc.equals(username) && passwordEncoder.matches(password, passAcc)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List selectAll() throws SQLException {
        List<User> result = new ArrayList<>();

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            User user = User.builder()
                    .idOfUser(resultSet.getLong("user_id"))
                    .firstNameOfUser(resultSet.getString("first_name"))
                    .surnameOfUser(resultSet.getString("last_name"))
                    .usernameOfUser(resultSet.getString("username"))
                    .ageOfUser(resultSet.getDate("age"))
                    .genderOfUser(resultSet.getString("gender"))
                    .countryOfUser(resultSet.getString("country"))
                    .cityOfUser(resultSet.getString("city"))
                    .build();

            result.add(user);
        }
        return result;
    }

    @Override
    public void deleteUser(Long userId) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_USER);

        preparedStatement.setLong(1, userId);

        preparedStatement.executeUpdate();
    }

    @Override
    public UUID insertUUID(HttpSession session) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement1 = connection.prepareStatement(SQL_SELECT_ID_BY_USERNAME);
        PreparedStatement preparedStatement2 = connection.prepareStatement(SQL_INSERT_UUID);

        preparedStatement1.setString(1, session.getAttribute("currentUsername").toString());

        ResultSet resultSet = preparedStatement1.executeQuery();

        Long id = null;

        while (resultSet.next()) {
            id = resultSet.getLong("user_id");
        }

        UUID uuid = UUID.randomUUID();

        preparedStatement2.setLong(1, id);
        preparedStatement2.setObject(2, uuid);

        preparedStatement2.executeUpdate();

        return uuid;
    }

    @Override
    public Boolean findUUID(UUID uuid) throws SQLException {
        String sql = "SELECT COUNT(*) FROM logins WHERE uuid = ?";
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setObject(1, uuid);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

}
