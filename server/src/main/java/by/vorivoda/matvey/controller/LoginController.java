package by.vorivoda.matvey.controller;

import by.vorivoda.matvey.model.GlobalConstants;
import by.vorivoda.matvey.security.SecurityToken;
import by.vorivoda.matvey.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = GlobalConstants.LOGIN_PATH)
    public String loginCheck() {
        return "Login check";
    }

    @PostMapping(value = GlobalConstants.LOGIN_PATH)
    public ResponseEntity<String> getToken(@RequestParam("username") String username,
                                   @RequestParam("password") String password) {

        SecurityToken token = userService.signIn(username, password);
        if (token == null) throw new AuthenticationServiceException("Wrong username or password");

        return new ResponseEntity<>(token.getValue(), HttpStatus.OK);
    }
}