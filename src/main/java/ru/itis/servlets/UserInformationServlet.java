package ru.itis.servlets;

import ru.itis.model.User;
import ru.itis.repository.SettingsRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet("/userInformationSettings")
public class UserInformationServlet extends HttpServlet {

    SettingsRepository settingsRepository;

    public void init(ServletConfig config){
        settingsRepository = (SettingsRepository) config.getServletContext().getAttribute("settingService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();


        List result;

        try {
            result = settingsRepository.getUserInformation(session);
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

        request.setAttribute("settingsForJsp", result);
        request.setAttribute("usernameForJsp", session.getAttribute("currentUsername"));

        request.getRequestDispatcher("/jsp/userInformationSettings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");

        Date date = null;
        try {
            date = sdf.parse(request.getParameter("age"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String gender = request.getParameter("gender");
        String country = request.getParameter("country");
        String city = request.getParameter("city");

        User user = User.builder()
                .firstNameOfUser(name)
                .surnameOfUser(surname)
                .ageOfUser(date)
                .genderOfUser(gender)
                .countryOfUser(country)
                .cityOfUser(city)
                .build();

        try {
            settingsRepository.changeUserInformations(user, session);
            response.sendRedirect("/home");
        }catch (SQLException e){
            response.sendRedirect("/userInformationSettings");
            throw new RuntimeException(e);
        }

    }
}
