package jm.spring.rest.controller;


import jm.spring.rest.controller.exception.NoDataFoundException;
import jm.spring.rest.entity.User;
import jm.spring.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//POST
//        localhost:8080/login?j_username=admin&j_password=admin

@RestController
@RequestMapping("/api")
public class MyRestController {

    private final UserService userService;

    @Autowired
    public MyRestController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/test/{id}")
    public String  getTest(@PathVariable int id) {
        System.out.println(">>> getTest - вернул Answer"+id);
        return "Answer"+id;
    }


    @GetMapping("/users")
    public List<User> getUsers() {
        System.out.println(">>> MyRestController.getUsers обработал запрос ВСЕ ЮЗЕРЫ");
        return userService.getUsers();
    }

    @GetMapping("/users/{id}")
    public User getOneUser(@PathVariable int id) {
        System.out.println(">>> MyRestController.getOneUser обработал запрос ЮЗЕР с id="+id);
        User user = userService.getUser(id);
        if(user == null) {
            throw new NoDataFoundException("ERROR: User with id=" + id + " not found into DB");
        }
        return user;
    }

    @PostMapping("/users") //создание нового юзера
    public User saveUser(@RequestBody User user) {
        System.out.println("POST: Сохраню юзера "+user);
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/users") //изменение существующего юзера
    public User updateUser(@RequestBody User user) {
        System.out.println("PUT: Изменю юзера с id="+user.getId());
        userService.saveUser(user);
        return user;
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id) {
        System.out.println("Буду удалять юзера с номером "+id);
        User user = userService.getUser(id);
        if(user == null) {
            throw new NoDataFoundException("ERROR: User with id=" + id + " not found into DB");
        }
        userService.deleteUser(id);
        return "OK. User with id=" + id + " was deleted from DB";
    }


}
