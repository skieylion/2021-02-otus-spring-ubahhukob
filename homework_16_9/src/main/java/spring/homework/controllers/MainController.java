package spring.homework.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.homework.domain.IndexPage;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

    @GetMapping("/")
    public String index(Model model){
        List<IndexPage> indexPageList=new ArrayList<>() {{
            add(new IndexPage("find a book","view/find"));
            add(new IndexPage("find all books","view/find-all"));
            add(new IndexPage("update a book","view/update"));
            add(new IndexPage("create a book","view/create"));
            add(new IndexPage("delete a book","view/delete"));
        }};

        model.addAttribute("indexPageList",indexPageList);
        return "index";
    }
    @GetMapping("/view/find")
    public String find(){
        return "find";
    }
    @GetMapping("/view/find-all")
    public String findAll(){
        return "find-all";
    }
    @GetMapping("/view/update")
    public String update(){
        return "update";
    }
    @GetMapping("/view/create")
    public String create(){
        return "create";
    }
    @GetMapping("/view/delete")
    public String delete(){
        return "delete";
    }
}
