package ru.itis.servlets;

import ru.itis.repository.AccountRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {


    AccountRepository accountRepository;

    public void init(ServletConfig config){
        accountRepository = (AccountRepository) config.getServletContext().getAttribute("accountRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List user;

        HttpSession session = request.getSession();

        try {
           user = accountRepository.selectAll();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

        request.setAttribute("usersForJsp", user);
        request.setAttribute("usernameForJsp", session.getAttribute("currentUsername"));

        request.getRequestDispatcher("/jsp/users.jsp").forward(request, response);
    }
}
