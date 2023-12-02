package ru.itis.servlets;

import ru.itis.model.PostInfo;
import ru.itis.repository.ArticleService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@WebServlet("/insertArticle")
public class InsertArticleServlet extends HttpServlet {

    ArticleService articleService;

    public void init(ServletConfig config){
        articleService = (ArticleService) config.getServletContext().getAttribute("artService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String user = session.getAttribute("currentUsername").toString();

        request.setAttribute("usernameForJsp", user);
        request.getRequestDispatcher("/jsp/insertArticle.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titleOfPost = request.getParameter("title");
        String textOfPost = request.getParameter("text");
        String tagOfPost = request.getParameter("tag");

        HttpSession session = request.getSession();
        String username = session.getAttribute("currentUsername").toString();

        PostInfo postInfo = PostInfo.builder()
                .username(username)
                .title(titleOfPost)
                .text(textOfPost)
                .tag(tagOfPost)
                .build();





        try {
            articleService.save(postInfo, session);
            response.sendRedirect("/home");
        } catch (SQLException e) {
            response.sendRedirect("/insertArticle");
            throw new RuntimeException(e);
        }


    }
}

