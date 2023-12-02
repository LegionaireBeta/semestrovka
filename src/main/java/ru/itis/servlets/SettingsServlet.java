package ru.itis.servlets;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.util.List;

@WebServlet("/mySettings")
public class SettingsServlet extends HttpServlet {

    SettingsRepository settingsRepository;
    PasswordEncoder passwordEncoder;

    public void init(ServletConfig config){
        settingsRepository = (SettingsRepository) config.getServletContext().getAttribute("settingService") ;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String user = session.getAttribute("currentUsername").toString();

        List result;

        try {
            result = settingsRepository.getUserPass(user);
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

        request.setAttribute("usernameForJsp", user);
        request.setAttribute("settingsForJsp", result);
        request.getRequestDispatcher("/jsp/mySettings.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userAcc = request.getParameter("username");
        String userPass = request.getParameter("password");

        User user = User.builder()
                .usernameOfUser(userAcc)
                .passwordOfUser(userPass)
                .build();
        try {
            settingsRepository.changeUsernamePassword(user, session);
            response.sendRedirect("/signIn");
        }catch (SQLException e){
            response.sendRedirect("/mySettings");
            throw new RuntimeException(e);
        }
    }
}
