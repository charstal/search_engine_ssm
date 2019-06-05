package cn.edu.zucc.caviar.searchengine.core.controller;

import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String toAdminPage() {
        return "admin";
    }


    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    @ResponseBody
    public List<User> adminLogin(HttpSession session, @RequestBody User admin, Model model) {
        admin = userService.findUserByRegisterId(admin);
        List<User> userList = userService.loadAllUser();

        if(admin != null) {
            session.setAttribute("ADMIN_SESSION", admin);
            return userList;
        }
        else {
            return null;
        }
    }

    @RequestMapping(value = "/admin/logout", method = RequestMethod.POST)
    @ResponseBody
    public String adminLogout(HttpSession session) {
        session.invalidate();

        return "success";
    }
}
