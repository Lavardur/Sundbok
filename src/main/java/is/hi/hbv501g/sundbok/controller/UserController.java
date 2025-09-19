package is.hi.hbv501g.sundbok.controller;

import is.hi.hbv501g.sundbok.model.User;
import is.hi.hbv501g.sundbok.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/user")
    public Iterable<User> get() {
        return userService.get();
    }

    @PostMapping("/api/user/register")
    public void recieveData(@RequestBody User user) {
        userService.processUser(user);
    }
}
