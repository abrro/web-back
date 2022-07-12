package com.example.demo.repositories.user;

import com.example.demo.entities.User;
import com.example.demo.entities.UserType;
import com.example.demo.repositories.MySqlAbstractRepository;
import org.apache.commons.codec.digest.DigestUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlUserRepository extends MySqlAbstractRepository implements UserRepository{
    @Override
    public User addUser(User user) {
        String hashedPassword = DigestUtils.sha256Hex(user.getPassHash());

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("INSERT INTO user (email, name, lastname, type, status, passHash) VALUES(?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getType().toString());
            preparedStatement.setInt(5, user.getStatus());
            preparedStatement.setString(6, hashedPassword);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return user;
    }

    @Override
    public User findUser(String email) {
        User user = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM user where email = ?");
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {
                String mail = resultSet.getString("email");
                String name = resultSet.getString("name");
                String lastname = resultSet.getString("lastname");
                String type = resultSet.getString("type");
                Integer status = resultSet.getInt("status");
                String passHash = resultSet.getString("passHash");
                user = new User(mail, name, lastname, UserType.valueOf(type), status, passHash);
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

        return user;
    }

    @Override
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from user");
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        UserType.valueOf(resultSet.getString("type")),
                        resultSet.getInt("status"),
                        resultSet.getString("passHash")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return users;
    }

    @Override
    public User modifyUser(User user, String usermail) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE user SET email = ?, name = ?, lastname = ?, type = ? where email = ?");
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getLastname());
            preparedStatement.setString(4, user.getType().toString());
            preparedStatement.setString(5, usermail);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return user;
    }

    @Override
    public void toggleStatus(String usermail, int newStatus) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE user SET status = ? where email = ?");
            preparedStatement.setInt(1,newStatus);
            preparedStatement.setString(2, usermail);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

    }

    @Override
    public List<User> allUsersOnPage(Integer pageNum) {
        List<User> users = new ArrayList<>();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM user ORDER BY email DESC LIMIT ?,?");
            preparedStatement.setInt(1, (pageNum-1)*10);
            preparedStatement.setInt(2, (pageNum)*10);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                users.add(new User(resultSet.getString("email"),
                        resultSet.getString("name"),
                        resultSet.getString("lastname"),
                        UserType.valueOf(resultSet.getString("type")),
                        resultSet.getInt("status"),
                        resultSet.getString("passHash")));
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

        return users;
    }

    public Integer userCount(){
        Integer num = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM user");
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
}
