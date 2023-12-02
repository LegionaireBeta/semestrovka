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

public class SettingsRepositoryJdbcImpl implements SettingsRepository{

    DataSource dataSource;
    PasswordEncoder passwordEncoder;

    private final static String SQL_USER_PASS = "SELECT username, password_hash FROM users WHERE username = ?";
    private final static String SQL_USER_PASS_UPDATE = "UPDATE users SET username = ?, password_hash = ? WHERE username = ?";
    private final static String SQL_USER_INFORMATION = "SELECT first_name, last_name, age, gender, country, city FROM users WHERE username = ?";
    private final static String SQL_USER_INFORMATION_UPDATE = "UPDATE users SET first_name = ?, " +
                                                                            "last_name = ?, age = ?, gender = ?, " +
                                                                            "country = ?, city = ? WHERE username = ?";

    public SettingsRepositoryJdbcImpl(DriverManagerDataSource dataSource){
        this.dataSource = dataSource;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List getUserPass(String username) throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_PASS);
        List<User> result = new ArrayList<>();

        preparedStatement.setString(1, username);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            User user = User.builder()
                    .usernameOfUser(resultSet.getString("username"))
                    .build();

            result.add(user);
        }
        return result;
    }

    @Override
    public void changeUsernamePassword(User user, HttpSession session) throws SQLException {

        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_PASS_UPDATE);

        preparedStatement.setString(1, user.getUsernameOfUser());
        preparedStatement.setString(2, passwordEncoder.encode(user.getPasswordOfUser()));
        preparedStatement.setString(3, session.getAttribute("currentUsername").toString());

        preparedStatement.executeUpdate();

        preparedStatement.close();
        connection.close();

    }

    @Override
    public List getUserInformation(HttpSession session) throws SQLException {
        List<User> result = new ArrayList<>();

        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_INFORMATION);
        preparedStatement.setString(1, session.getAttribute("currentUsername").toString());

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){
            User user = User.builder()
                    .firstNameOfUser(resultSet.getString("first_name"))
                    .surnameOfUser(resultSet.getString("last_name"))
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
    public void changeUserInformations(User user, HttpSession session) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_USER_INFORMATION_UPDATE);

        preparedStatement.setString(1, user.getFirstNameOfUser());
        preparedStatement.setString(2, user.getSurnameOfUser());
        preparedStatement.setDate(3, new java.sql.Date(user.getAgeOfUser().getTime()));
        preparedStatement.setString(4, user.getGenderOfUser());
        preparedStatement.setString(5, user.getCountryOfUser());
        preparedStatement.setString(6, user.getCityOfUser());
        preparedStatement.setString(7, session.getAttribute("currentUsername").toString());

        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();
    }



}
