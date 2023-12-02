package ru.itis.servlets;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.itis.model.User;
import ru.itis.repository.AccountRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    AccountRepository accountRepository;
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void init(ServletConfig config){
        accountRepository = (AccountRepository) config.getServletContext().getAttribute("accountRep");
        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/html/signIn.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountUsername = request.getParameter("username");
        String accountPassword = request.getParameter("password");


        User user = User.builder()
                .usernameOfUser(accountUsername)
                .passwordOfUser(accountPassword)
                .build();

        try {


            if(accountRepository.login(accountUsername, accountPassword, user)){
                HttpSession session = request.getSession(true);
                session.setAttribute("autenticated", true);
                session.setAttribute("currentUsername", accountUsername);
                response.sendRedirect("/home");
            }else {
                response.sendRedirect("/signIn");
            }
        }catch (SQLException e){

            throw new RuntimeException(e);
        }
    }
}
