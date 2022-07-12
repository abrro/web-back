package com.example.demo.repositories.category;

import com.example.demo.entities.Article;
import com.example.demo.entities.Category;
import com.example.demo.repositories.MySqlAbstractRepository;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlCategoryRepository extends MySqlAbstractRepository implements CategoryRepository {
    @Override
    public Category addCategory(Category category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO category (label, description) VALUES(?, ?)");
            preparedStatement.setString(1, category.getLabel());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public List<Category> allCategories() {
        List<Category> categories = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from category");
            while (resultSet.next()) {
                categories.add(new Category(resultSet.getString("label"),
                        resultSet.getString("description")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return categories;
    }

    @Override
    public Category findCategory(String category) {
        Category cat = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM category where label = ?");
            preparedStatement.setString(1, category);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String title = resultSet.getString("label");
                String description = resultSet.getString("description");
                cat = new Category(title, description);
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

        return cat;
    }

    @Override
    public void deleteCategory(String category) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM category where label = ?");
            preparedStatement.setString(1, category);
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
    public Category modifyCategory(Category category, String categoryName) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE category SET label = ?, description = ? where label = ?");
            preparedStatement.setString(1, category.getLabel());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setString(3, categoryName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return category;
    }

    @Override
    public List<Article> articlesByCategory(String category) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article where category = ?");
            preparedStatement.setString(1, category);
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
    public List<Category> allCategoriesOnPage(Integer pageNum) {
        List<Category> categories = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM category ORDER BY label LIMIT ?,?");
            preparedStatement.setInt(1, (pageNum-1)*10);
            preparedStatement.setInt(2, (pageNum)*10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.add(new Category(resultSet.getString("label"), resultSet.getString("description")));
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

        return categories;
    }

    public Integer categoryCount() {
        Integer num = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM category");
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

    public Integer articlesCountByCategory(String category) {
        Integer num = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM article where category = ?");
            preparedStatement.setString(1, category);
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
    public List<Article> articlesByCategoryOnPage(String category, Integer pageNum) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article where category = ? ORDER BY date  DESC LIMIT ?,? ");
            preparedStatement.setString(1, category);
            preparedStatement.setInt(2, (pageNum-1)*10);
            preparedStatement.setInt(3, (pageNum)*10);
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
    public List<Article> searchArticlesByCategory(String category, String search, Integer pageNum) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article WHERE category = ? AND ((content LIKE ?) OR (title LIKE ?)) ORDER BY date DESC LIMIT ?,?");
            preparedStatement.setString(1, category);
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
            preparedStatement.setInt(4, (pageNum-1)*10);
            preparedStatement.setInt(5, (pageNum)*10);
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
    public Integer articlesCountByCategorySearch(String category, String search) {
        Integer num = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM article where category = ? AND ((content LIKE ?) OR (title LIKE ?))");
            preparedStatement.setString(1, category);
            preparedStatement.setString(2, "%" + search + "%");
            preparedStatement.setString(3, "%" + search + "%");
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


}

