package com.example.demo.repositories.session;

import com.example.demo.repositories.MySqlAbstractRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlSessionRepository extends MySqlAbstractRepository implements SessionRepository {

    @Override
    public void visitArticle(Integer articleId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE article SET visits = visits + 1 WHERE id = ?");
            preparedStatement.setInt(1, articleId);
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
    public void likeArticle(Integer articleId, Integer num) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE article SET likes = likes + ? WHERE id = ?");
            preparedStatement.setInt(1, num);
            preparedStatement.setInt(2, articleId);

            preparedStatement.executeUpdate();
            preparedStatement.clearBatch();

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
    public void dislikeArticle(Integer articleId, Integer num) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE article SET dislikes = dislikes + ? WHERE id = ?");
            preparedStatement.setInt(1, num);
            preparedStatement.setInt(2, articleId);

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
    public void likeComment(Integer commentId, Integer num) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE comment SET likes = likes + ? WHERE id = ?");
            preparedStatement.setInt(1, num);
            preparedStatement.setInt(2, commentId);

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
    public void dislikeComment(Integer commentId, Integer num) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE comment SET dislikes = dislikes + ? WHERE id = ?");
            preparedStatement.setInt(1, num);
            preparedStatement.setInt(2, commentId);

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
}
