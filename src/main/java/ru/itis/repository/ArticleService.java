package ru.itis.repository;

import ru.itis.model.PostInfo;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public interface ArticleService {


    void save(PostInfo postInfo, HttpSession session) throws SQLException;
    List getArticles() throws SQLException;
    List getUserArticles(HttpSession session) throws SQLException;
    List getTitleOfArticles() throws SQLException;
    void deleteArticle(Long articleId) throws SQLException;
    void deleteArticlesByUser(String username) throws SQLException;

}
