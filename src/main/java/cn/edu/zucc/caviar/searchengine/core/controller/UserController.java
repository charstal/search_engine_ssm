package cn.edu.zucc.caviar.searchengine.core.controller;

import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable("id") Integer id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("USER_SESSION");

        if(user == null || !id.equals(user.getUserId())) {
            user = new User();
            user.setUserId(id);
            user = userService.findUserByRegisterId(user);
        }

        if(user == null) {
            model.addAttribute("msg", "can't find user");
            return "error";
        }

        model.addAttribute("user", user);

        return "user";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    @ResponseBody
    public User register(@RequestBody User user, HttpSession session) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        user.setRegisterTime(timestamp);
        user.setLastLoginTime(timestamp);

        user = userService.registerUser(user);

        if(user != null) {
            session.setAttribute("USER_SESSION", user);
        }


        return user;
    }

    // delete user
//    @RequestMapping(value = "/user", method = RequestMethod.DELETE)

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public User updateUser(@PathVariable("id") Integer id,@RequestBody User user, HttpSession session) {

        user.setUserId(id);

        if("".equals(user.getPassword())) {
            user.setPassword(null);
        }
        if("".equals(user.getDescribe())) {
            user.setDescribe(null);
        }
        if("".equals(user.getAvatar())) {
            user.setAvatar(null);
        }
        if("".equals(user.getUserName())) {
            user.setUserName(null);
        }
        if("".equals(user.getGender())) {
            user.setGender(null);
        }

        System.out.println(user);

        user = userService.updateUser(user);
        session.setAttribute("USER_SESSION", user);

        return user;
    }

    @RequestMapping(value = "/collect/{noteId}", method = RequestMethod.GET)
    @ResponseBody
    public String collectNote(HttpSession session, @PathVariable("noteId") String noteId) {
        User user = (User) session.getAttribute("USER_SESSION");

        System.out.println("UserId:" + user.getUserId());
        System.out.println("noteId:"  + noteId);

        return "success";
    }

    @RequestMapping(value = "/like/{noteId}", method = RequestMethod.GET)
    @ResponseBody
    public String likeNote(HttpSession session, @PathVariable("noteId") String noteId) {
        User user = (User) session.getAttribute("USER_SESSION");

        System.out.println("UserId:" + user.getUserId());
        System.out.println("noteId:"  + noteId);

        return "success";
    }

}
