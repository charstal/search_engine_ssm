package cn.edu.zucc.caviar.searchengine.core.controller;


import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.pojo.Response;
import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.SearchService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
public class SearchController {

    @Autowired
    private SearchService service;

    @RequestMapping(value = "/search/{keyword}", method = RequestMethod.GET)
    @ResponseBody
    public Response keywordSearch(@PathVariable("keyword") String keyword, Integer page, HttpSession session) {

        User user = (User) session.getAttribute("USER_SESSION");

        String recommendNumber = "";

        if(user != null) {
            recommendNumber = user.getUserId().toString();
        } else {
            recommendNumber = session.getId();
        }


        if(page == null) {
            page = 1;
        }

        System.out.println(keyword);

        Response response = service.keywordSearch(keyword, page, recommendNumber);


//        for(String a: response.getHotpotList()) {
//            System.out.println("----------------------");
//            System.out.println(a);
//        }
        return response;
    }

}
