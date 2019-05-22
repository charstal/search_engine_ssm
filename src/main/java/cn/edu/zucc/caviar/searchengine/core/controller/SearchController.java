package cn.edu.zucc.caviar.searchengine.core.controller;


import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Set;

@Controller
public class SearchController {

    @Autowired
    private SearchService service;

    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    @ResponseBody
    public Set<Document> keywordSearch(@PathVariable("keyword") String keyword, Integer page) {


        if(page == null) {
            System.out.println(keyword);
            service.keywordSearch(keyword);
            page = 1;
        }

        return service.documentsInPage(page,10);
    }

}
