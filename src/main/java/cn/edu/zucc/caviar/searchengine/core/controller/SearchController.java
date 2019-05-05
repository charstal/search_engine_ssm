package cn.edu.zucc.caviar.searchengine.core.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SearchController {

    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    public String keywordSearch(@PathVariable("keyword") String keyword) {

        return "main";
    }

}
