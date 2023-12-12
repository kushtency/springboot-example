package dev.learn.simpleuserlogin.controller.secured;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {
    @GetMapping("")
    public String testController(){
        return "hello from secured controller";
    }
}
