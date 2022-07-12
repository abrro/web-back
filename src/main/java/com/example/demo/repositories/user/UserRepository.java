package com.example.demo.repositories.user;

import com.example.demo.entities.User;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public interface UserRepository {
    public User addUser(User user);
    public User findUser(String usermail);
    public List<User> allUsers();
    public User modifyUser(User user, String usermail);
    public void toggleStatus(String usermail, int newStatus);
    public List<User> allUsersOnPage(Integer pageNum);
    public Integer userCount();
}
