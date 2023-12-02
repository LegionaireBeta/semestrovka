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

@WebServlet("/accountInformations")
public class AccountInformationServlet extends HttpServlet {


    AccountRepository accountRepository;

    public void init(ServletConfig config){
        accountRepository = (AccountRepository) config.getServletContext().getAttribute("accountRep");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List user;

        try {
           user = accountRepository.selectUserInformation(session);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        request.setAttribute("userForJsp", user);
        request.setAttribute("usernameForJsp", session.getAttribute("currentUsername"));
        request.getRequestDispatcher("/jsp/accountInformations.jsp").forward(request, response);
    }
}
