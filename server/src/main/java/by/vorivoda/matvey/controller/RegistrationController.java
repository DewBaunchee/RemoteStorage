package by.vorivoda.matvey.controller;

import by.vorivoda.matvey.controller.exception.InvalidRegistrationArgumentsException;
import by.vorivoda.matvey.controller.exception.SuchUserAlreadyExistsException;
import by.vorivoda.matvey.model.dao.entity.User;
import by.vorivoda.matvey.model.GlobalConstants;
import by.vorivoda.matvey.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = GlobalConstants.REGISTRATION_PATH)
    public String registrationCheck() {
        return "Reg check";
    }

    @PostMapping(GlobalConstants.REGISTRATION_PATH)
    @ResponseBody
    public void addUser(@RequestParam("username") String username,
                        @RequestParam("password") String password) throws Exception {

        if (userService.loadUserByUsername(username) != null) throw new SuchUserAlreadyExistsException();
        String errorsReport = getRegistrationErrorsReport(username, password); // TODO return exceptions
        if (errorsReport.length() > 0) throw new InvalidRegistrationArgumentsException(errorsReport);

        userService.create(new User(username, password));
    }

    private String getRegistrationErrorsReport(String username, String password) {
        StringBuilder report = new StringBuilder();

        if (!username.matches(GlobalConstants.USERNAME_SYMBOLS_REGEX))
            report.append("Invalid symbols in username, permitted symbols: ")
                    .append(GlobalConstants.USERNAME_SYMBOLS_REGEX)
                    .append("\n");

        if (!password.matches(GlobalConstants.PASSWORD_SYMBOLS_REGEX))
            report.append("Invalid symbols in password, permitted symbols: ")
                    .append(GlobalConstants.PASSWORD_SYMBOLS_REGEX)
                    .append("\n");

        if (username.length() < GlobalConstants.USERNAME_MIN_LENGTH)
            report.append("Username is too short, min length: ")
                    .append(GlobalConstants.USERNAME_MIN_LENGTH)
                    .append("\n");

        if (username.length() > GlobalConstants.USERNAME_MAX_LENGTH)
            report.append("Username is too long, max length: ")
                    .append(GlobalConstants.USERNAME_MAX_LENGTH)
                    .append("\n");

        if (password.length() < GlobalConstants.PASSWORD_MIN_LENGTH)
            report.append("Password is too short, min length: ")
                    .append(GlobalConstants.PASSWORD_MIN_LENGTH)
                    .append("\n");

        if (password.length() > GlobalConstants.PASSWORD_MAX_LENGTH)
            report.append("Password is too long, max length: ")
                    .append(GlobalConstants.PASSWORD_MAX_LENGTH)
                    .append("\n");

        return report.toString();
    }
}
