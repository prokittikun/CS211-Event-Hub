package cs211.project.models.collections;

import cs211.project.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public User findUserById(String id) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                return user;
            }
        }
        return null;
    }

    public List<User> findUsersByRole(String role) {
        List<User> usersWithRole = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().equals(role)) {
                usersWithRole.add(user);
            }
        }
        return usersWithRole;
    }

    public boolean deleteUserById(String id) {
        User user = findUserById(id);
        if (user != null) {
            users.remove(user);
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        return users;
    }

}
