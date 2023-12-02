package ru.itis.repository;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.itis.model.PostInfo;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArticleServiceJdbcImpl implements ArticleService{

    private final static String SQL_INSERT_ARTICLE = "INSERT INTO articles(username, title, text, tag, uuid_user) VALUES (?, ?, ?, ?, ?)";
    private final static String SQL_VIEW_ARTICLES = "SELECT * FROM articles ORDER BY article_id DESC";
    private final static String SQL_VIEW_USER_ARTICLES = "SELECT * FROM articles WHERE username = ?";
    private final static String SQL_SELECT_ARTICLE_TITLE = "SELECT article_id, title FROM articles ORDER BY article_id DESC LIMIT 8";
    private final static String SQL_DELETE_ARTICLE = "DELETE FROM articles WHERE article_id = ?";
    private final static String SQL_DELETE_ARTICLES_BY_USER = "DELETE FROM articles WHERE username = ?";

    DataSource dataSource;

    public ArticleServiceJdbcImpl(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(PostInfo postInfo, HttpSession session) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement2 = connection.prepareStatement(SQL_INSERT_ARTICLE);


        preparedStatement2.setString(1, session.getAttribute("currentUsername").toString());
        preparedStatement2.setString(2, postInfo.getTitle());
        preparedStatement2.setString(3, postInfo.getText());
        preparedStatement2.setString(4, postInfo.getTag());
        preparedStatement2.setObject(5, UUID.randomUUID());

        preparedStatement2.executeUpdate();
    }

    @Override
    public List getArticles() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_VIEW_ARTICLES);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<PostInfo> result = new ArrayList<>();

        while (resultSet.next()){
            PostInfo postInfo = PostInfo.builder()
                    .articleId(resultSet.getLong("article_id"))
                    .username(resultSet.getString("username"))
                    .title(resultSet.getString("title"))
                    .text(resultSet.getString("text"))
                    .tag(resultSet.getString("tag"))
                    .date(resultSet.getString("date_of_article"))
                    .uuid((UUID) resultSet.getObject("uuid_user"))
                    .build();

            result.add(postInfo);
        }
        return result;

    }

    @Override
    public List getUserArticles(HttpSession session) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement2 = connection.prepareStatement(SQL_VIEW_USER_ARTICLES);
        preparedStatement2.setString(1, session.getAttribute("currentUsername").toString());

        ResultSet resultSet = preparedStatement2.executeQuery();

        List<PostInfo> result = new ArrayList<>();

        while (resultSet.next()){
            PostInfo postInfo = PostInfo.builder()
                    .articleId(resultSet.getLong("article_id"))
                    .username(session.getAttribute("currentUsername").toString())
                    .title(resultSet.getString("title"))
                    .text(resultSet.getString("text"))
                    .tag(resultSet.getString("tag"))
                    .date(resultSet.getString("date_of_article"))
                    .uuid((UUID) resultSet.getObject("uuid_user"))
                    .build();
            result.add(postInfo);
        }
        return result;
    }

    @Override
    public List getTitleOfArticles() throws SQLException {
        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ARTICLE_TITLE);
        ResultSet resultSet = preparedStatement.executeQuery();

        List<PostInfo> result = new ArrayList<>();

        while (resultSet.next()){
            PostInfo postInfo = PostInfo.builder()
                    .articleId(resultSet.getLong("article_id"))
                    .title(resultSet.getString("title"))
                    .build();

            result.add(postInfo);
        }
        return result;
    }

    @Override
    public void deleteArticle(Long articleId) throws SQLException {
        Connection connection = dataSource.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ARTICLE);
        preparedStatement.setLong(1, articleId);

        preparedStatement.executeUpdate();

    }

    @Override
    public void deleteArticlesByUser(String username) throws SQLException {

        Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_ARTICLES_BY_USER);
        preparedStatement.setString(1, username);

        preparedStatement.executeUpdate();

    }
}
