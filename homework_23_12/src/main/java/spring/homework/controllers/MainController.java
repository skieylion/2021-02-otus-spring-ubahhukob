package spring.homework.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    @GetMapping("/")
    public String login(){
        return "redirect:/login";
    }
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @GetMapping("/table.js")
    public String table(){
        return "table.js";
    }
    @GetMapping("/actions.js")
    public String actions(){
        return "actions.js";
    }
    @GetMapping("/axios.js")
    public String axios(){
        return "axios.js";
    }
}