package cn.edu.zucc.caviar.searchengine.core.controller;

import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@Controller
public class FileUploadController {

    @RequestMapping("/fileUpload")
    @ResponseBody
    public String handleFormUpload(MultipartFile uploadfile,
                                   HttpServletRequest request,
                                   Model model, HttpSession session) {

        if (!uploadfile.isEmpty()) {
            String dir = "";

            User user = (User) session.getAttribute("USER_SESSION");

            String originalFileName = uploadfile.getOriginalFilename();

            String dirPath = request.getServletContext().getRealPath("/upload/");

            File filePath = new File(dirPath);

            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            String newFileName = user.getUserId() + "_" + UUID.randomUUID() + "_avatar" + originalFileName.substring(originalFileName.lastIndexOf("."));

            dir = dirPath + newFileName;

            try {
                uploadfile.transferTo(new File(dir));
            } catch (IOException e) {
                e.printStackTrace();
                model.addAttribute("msg", "file upload Error");
                return "error";
            }


            System.out.println(dir);

            return dir;
        } else {
            return "FAIL";
        }

    }
}
