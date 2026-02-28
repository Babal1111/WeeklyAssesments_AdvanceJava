package com.example.LearningManagmentSystem.Services;


import com.example.LearningManagmentSystem.Model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private List<User> userList = new ArrayList<>();

    public void addUser(User user){
    // assuming user with same id doest exist
        userList.add(user);
    }

    public User getUserById(int id){
        for(User user:userList){
            if(user.getId()==id){
                return user;
            }
        }
        return null;
    }
    public List<User> getAllUsers(){
        return this.userList;
    }
    public User login(String email, String passowrd){
        for(User user: userList){
            if (user.getEmail().equals(email) &&
                    user.getPassword().equals(passowrd)) {
                return user;
            }
        }
        return null;
    }
}
