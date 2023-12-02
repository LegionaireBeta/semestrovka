package ru.itis.servlets;

import ru.itis.repository.ArticleService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/myArticles")
public class MyArticlesServlet extends HttpServlet {


    ArticleService articleService;

    public void init(ServletConfig config){
        articleService = (ArticleService) config.getServletContext().getAttribute("artService");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(true);
        String username = session.getAttribute("currentUsername").toString();

        List article;


        try {
            article = articleService.getUserArticles(session);
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

        request.setAttribute("titlesForJsp", article);
        request.setAttribute("usernameForJsp", username);
        request.setAttribute("myArticlesForJsp", article);
        request.getRequestDispatcher("/jsp/myArticles.jsp").forward(request, response);
    }
}
