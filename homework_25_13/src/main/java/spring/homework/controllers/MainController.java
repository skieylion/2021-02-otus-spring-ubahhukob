package spring.homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/")
    public String login(){
        return "redirect:/login";
    }
}