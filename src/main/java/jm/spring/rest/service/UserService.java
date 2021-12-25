package jm.spring.rest.service;

import jm.spring.rest.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    public List<User> getUsers();

    public void saveUser(User user);

    public void deleteUser(int id);

    public User getUser(int id);

    public User getUserByName(String username);

}
