package com.example.demo.services;

import com.example.demo.entities.User;
import com.example.demo.repositories.user.UserRepository;


import javax.inject.Inject;
import java.util.List;

public class UserService {
    public UserService() {
        System.out.println(this);
    }

    @Inject
    UserRepository userRepository;

    public User addUser(User user) {
        return this.userRepository.addUser(user);
    }

    public User findUser(String username){
        return this.userRepository.findUser(username);
    }

    public List<User> allUsers(){
        return this.userRepository.allUsers();
    }

    public User modifyUser(User user, String username){
        return this.userRepository.modifyUser(user, username);
    }

    public void toggleStatus(String username, int newStatus){
        this.userRepository.toggleStatus(username, newStatus);
    }

    public List<User> allUsersOnPage(Integer pageNum) {
        return this.userRepository.allUsersOnPage(pageNum);
    }

    public Integer userCount() {
        return this.userRepository.userCount();
    }


}
