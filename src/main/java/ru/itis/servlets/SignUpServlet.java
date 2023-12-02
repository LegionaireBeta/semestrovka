package ru.itis.servlets;

import ru.itis.dto.SignUpForm;
import ru.itis.repository.SignUpService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    SignUpService signUpService;

    public void init(ServletConfig config){
        signUpService = (SignUpService) config.getServletContext().getAttribute("signService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/signUp.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SignUpForm form = new SignUpForm();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        form.setFirstName(request.getParameter("name"));
        form.setSurname(request.getParameter("surname"));
        try {
            Date date = sdf.parse(request.getParameter("age"));
            form.setAge(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        form.setGender(request.getParameter("gender"));
        form.setCountry(request.getParameter("country"));
        form.setCity(request.getParameter("city"));
        form.setUsername(request.getParameter("username"));
        form.setPassword(request.getParameter("password"));

        try {
            signUpService.signUp(form);
            response.sendRedirect("/signIn");
        } catch (SQLException e){
            response.sendRedirect("/signUp");
            throw new RuntimeException(e);
        }
    }
}
