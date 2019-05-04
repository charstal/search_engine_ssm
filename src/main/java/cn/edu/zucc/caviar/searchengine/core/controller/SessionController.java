package cn.edu.zucc.caviar.searchengine.core.controller;

import cn.edu.zucc.caviar.searchengine.common.utils.EncryptUtil;
import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SessionController {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "/session", method = RequestMethod.GET)
    public String toLogin() {
        return "login";
    }

    @RequestMapping(value = "/session", method = RequestMethod.POST)
    @ResponseBody
    public User login(@RequestBody User user, HttpSession session, Model model) {
        user = userService.findUserByRegisterId(user);
        if(user != null) {
            session.setAttribute("USER_SESSION", user);
        }
        else {
            model.addAttribute("msg", "Wrong account or Wrong password");
        }

//        System.out.println(user);
        return user;
    }

    @RequestMapping(value = "/session", method = RequestMethod.DELETE)
    public ModelAndView logout(HttpSession session, HttpServletRequest request) {

        session.invalidate();
        ModelAndView mav = new ModelAndView("redirect:session");
        return mav;


    }

    // update session
//    @RequestMapping(value = "/session", method = RequestMethod.PUT)
//    public void updateSession() {
//
//    }

}
