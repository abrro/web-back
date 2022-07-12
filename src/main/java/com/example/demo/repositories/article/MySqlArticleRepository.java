package com.example.demo.repositories.article;

import com.example.demo.entities.Article;
import com.example.demo.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MySqlArticleRepository extends MySqlAbstractRepository implements ArticleRepository {
    @Override
    public Article addArticle(Article article) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO article (title, content, creator, category, tags,  visits, likes, dislikes) VALUES(?, ?, ?, ?, ?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setString(3, article.getCreator());
            preparedStatement.setString(4, article.getCategory());
            preparedStatement.setString(5, Article.listToString(article.getTags()));
            preparedStatement.setInt(6, 0);
            preparedStatement.setInt(7, 0);
            preparedStatement.setInt(8, 0);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                article.setId(resultSet.getInt(1));
                article.setDate(String.valueOf(Timestamp.valueOf(LocalDateTime.now())));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return article;
    }

    @Override
    public Article findArticle(Integer id) {
        Article article = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article where id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                int articleid = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                String date = resultSet.getString("date");
                String creator = resultSet.getString("creator");
                String category = resultSet.getString("category");
                List<String> tags = Article.stringToList(resultSet.getString("tags"));
                int visits = resultSet.getInt("visits");
                int likes = resultSet.getInt("likes");
                int dislikes = resultSet.getInt("dislikes");
                article = new Article(articleid, title, content, date , creator, category, tags, visits, likes, dislikes);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return article;
    }

    @Override
    public void deleteArticle(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM article where id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }

    @Override
    public Article modifyArticle(Article article, Integer articleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE article SET title = ?, content = ?, creator = ?, category = ?, tags = ? where id = ?");
            preparedStatement.setString(1, article.getTitle());
            preparedStatement.setString(2, article.getContent());
            preparedStatement.setString(3, article.getCreator());
            preparedStatement.setString(4, article.getCategory());
            preparedStatement.setString(5, Article.listToString(article.getTags()));
            preparedStatement.setInt(6, articleId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return article;
    }

    @Override
    public List<Article> allArticles() {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM article");
            while (resultSet.next()) {
                articles.add(new Article(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("date"),
                        resultSet.getString("creator"),
                        resultSet.getString("category"),
                        Article.stringToList(resultSet.getString("tags")),
                        resultSet.getInt("visits"),
                        resultSet.getInt("likes"),
                        resultSet.getInt("dislikes")
                        ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public List<Article> allArticlesOnPage(Integer pageNum) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article ORDER BY date DESC LIMIT ?,?");
            preparedStatement.setInt(1, (pageNum-1)*10);
            preparedStatement.setInt(2, (pageNum)*10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                articles.add(new Article(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("date"),
                        resultSet.getString("creator"),
                        resultSet.getString("category"),
                        Article.stringToList(resultSet.getString("tags")),
                        resultSet.getInt("visits"),
                        resultSet.getInt("likes"),
                        resultSet.getInt("dislikes")
                ));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public Integer articlesCount() {
        Integer num = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM article");
            if (resultSet.next()) {
                num = resultSet.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return num;
    }

    @Override
    public List<Article> searchArticles(String search, Integer pageNum) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article WHERE (content LIKE ?) OR (title LIKE ?) ORDER BY date DESC LIMIT ?,?");
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setInt(3, (pageNum-1)*10);
            preparedStatement.setInt(4, (pageNum)*10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                articles.add(new Article(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("date"),
                        resultSet.getString("creator"),
                        resultSet.getString("category"),
                        Article.stringToList(resultSet.getString("tags")),
                        resultSet.getInt("visits"),
                        resultSet.getInt("likes"),
                        resultSet.getInt("dislikes")
                ));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public Integer articlesCountSearch(String search) {
        Integer num = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM article where (content LIKE ? OR title LIKE ?)");
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setString(2, "%" + search + "%");
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                num = resultSet.getInt(1);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return num;
    }

    @Override
    public List<Article> newestArticles() {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article ORDER BY date DESC LIMIT 10");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                articles.add(new Article(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("date"),
                        resultSet.getString("creator"),
                        resultSet.getString("category"),
                        Article.stringToList(resultSet.getString("tags")),
                        resultSet.getInt("visits"),
                        resultSet.getInt("likes"),
                        resultSet.getInt("dislikes")
                ));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public List<Article> mostVisitedArticles() {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article WHERE date >= DATE_SUB(CURRENT_DATE, INTERVAL 1 MONTH) ORDER BY visits DESC, date DESC LIMIT 10");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                articles.add(new Article(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("date"),
                        resultSet.getString("creator"),
                        resultSet.getString("category"),
                        Article.stringToList(resultSet.getString("tags")),
                        resultSet.getInt("visits"),
                        resultSet.getInt("likes"),
                        resultSet.getInt("dislikes")
                ));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public List<Article> mostReactionsArticles() {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article ORDER BY (likes + dislikes) DESC LIMIT 3");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                articles.add(new Article(resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("content"),
                        resultSet.getString("date"),
                        resultSet.getString("creator"),
                        resultSet.getString("category"),
                        Article.stringToList(resultSet.getString("tags")),
                        resultSet.getInt("visits"),
                        resultSet.getInt("likes"),
                        resultSet.getInt("dislikes")
                ));
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }
}
