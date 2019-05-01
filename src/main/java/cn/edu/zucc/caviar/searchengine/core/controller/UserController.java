package cn.edu.zucc.caviar.searchengine.core.controller;

import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    @ResponseBody
    public User toRegister(@PathVariable("id") String id, HttpSession session) {
        User user = (User) session.getAttribute("USER_SESSION");

        if(user == null) {
            user = userService.findUserByRegisterId(id, null);
        }

        return user;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String register(User user) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setRegisterTime(timestamp);
        user.setLastLoginTime(timestamp);

        user = userService.registerUser(user);

        if(user != null){
            return "success";
        }else{
            return "error";
        }
    }

    // delete user
//    @RequestMapping(value = "/user", method = RequestMethod.DELETE)

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public User updateUser(@PathVariable("id") Integer id, User user, HttpSession session) {
        user.setUserId(id);
        user = userService.updateUser(user);
        session.setAttribute("USER_SESSION", user);

        return user;
    }



}
