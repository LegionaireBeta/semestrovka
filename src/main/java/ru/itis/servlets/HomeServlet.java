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

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    ArticleService articleService;

    public void init(ServletConfig config){
        articleService = (ArticleService) config.getServletContext().getAttribute("artService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String user = session.getAttribute("currentUsername").toString();
        List article;
        List title;

        try {
            article = articleService.getArticles();
            title = articleService.getTitleOfArticles();
        }catch (Exception e){
            throw new IllegalStateException(e);
        }

        request.setAttribute("usernameForJsp", user);
        request.setAttribute("articlesForJsp", article);
        request.setAttribute("titlesForJsp", title);
        request.getRequestDispatcher("/jsp/home.jsp").forward(request, response);
    }
}
