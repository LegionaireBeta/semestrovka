package ru.itis.servlets;

import ru.itis.repository.ArticleService;
import ru.itis.repository.ArticleServiceJdbcImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteArticle")
public class DeleteArticle extends HttpServlet {


    ArticleService articleService;

    public void init(ServletConfig config){
        articleService = (ArticleService) config.getServletContext().getAttribute("artService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long articleId = Long.parseLong(request.getParameter("articleId"));

        try {
            articleService.deleteArticle(articleId);

            response.sendRedirect("/home");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/home");
        }
    }
}

