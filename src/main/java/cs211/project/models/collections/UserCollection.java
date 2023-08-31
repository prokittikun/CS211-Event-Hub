package cs211.project.models.collections;

import cs211.project.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserCollection {
    private List<User> users;

    public UserCollection() {
        this.users = new ArrayList<>();
    }

    public boolean addUser(User user) {
        if (user != null) {
            users.add(user);
            return true;
        }
        return false;
    }

    public User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
    
}

