package ru.itis.servlets;

import ru.itis.repository.AccountRepository;
import ru.itis.repository.ArticleService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    AccountRepository accountRepository;
    ArticleService articleService;

    public void init(ServletConfig config){
        accountRepository = (AccountRepository) config.getServletContext().getAttribute("accountRep");
        articleService = (ArticleService) config.getServletContext().getAttribute("artService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long idOfUser = Long.parseLong(request.getParameter("idOfUser"));
        String usernameOfUser = request.getParameter("usernameOfUser");

        try {
            articleService.deleteArticlesByUser(usernameOfUser);
            accountRepository.deleteUser(idOfUser);
            response.sendRedirect("/users");
        }catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/users");
        }

    }
}
