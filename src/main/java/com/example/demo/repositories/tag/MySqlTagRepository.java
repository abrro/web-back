package com.example.demo.repositories.tag;

import com.example.demo.entities.Article;
import com.example.demo.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class MySqlTagRepository extends MySqlAbstractRepository implements TagRepository {


    @Override
    public Integer articlesByTagCount(String tag) {
        Integer num = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT COUNT(*) FROM article where tags LIKE ?");
            preparedStatement.setString(1, "%" + tag + "%");
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
    public List<Article> articlesByTag(String tag, Integer pageNum) {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article WHERE tags LIKE ? ORDER BY date DESC LIMIT ?,?");
            preparedStatement.setString(1, "%" + tag + "%");
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
}
