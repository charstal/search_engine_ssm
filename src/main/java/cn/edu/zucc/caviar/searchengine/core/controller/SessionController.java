package cn.edu.zucc.caviar.searchengine.core.controller;

import cn.edu.zucc.caviar.searchengine.common.utils.EncryptUtil;
import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class SessionController {

    @Autowired
    private EncryptUtil encryptUtil;

    @Autowired
    private UserService userService;


    // get session
//    @RequestMapping(value = "/session", method = RequestMethod.GET)
//    public String toLogin() {
//        return "login";
//    }

    @RequestMapping(value = "/session", method = RequestMethod.POST)
    public String login(String registerId, String password, Model model, HttpSession session) {
        String md5Password = encryptUtil.encrypt(password);
        User user = userService.findUserByRegisterId(registerId, md5Password);

        if(user == null) {
            model.addAttribute("msg", "Wrong account or Wrong password");
            return "login";
        }

        session.setAttribute("USER_SESSION", user);

        return "redirect:main";
    }

    @RequestMapping(value = "/session", method = RequestMethod.DELETE)
    public String logout(HttpSession session, HttpServletRequest request) {
        String[] uri = request.getRequestURI().split(".");
        System.out.println(uri[0]);

        session.invalidate();

        return "redirect:" + uri[0];
    }

    // update session
//    @RequestMapping(value = "/session", method = RequestMethod.PUT)
//    public void updateSession() {
//
//    }

}
