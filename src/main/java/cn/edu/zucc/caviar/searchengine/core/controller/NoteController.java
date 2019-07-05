package cn.edu.zucc.caviar.searchengine.core.controller;


import cn.edu.zucc.caviar.searchengine.core.pojo.Comment;
import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.CommentService;
import cn.edu.zucc.caviar.searchengine.core.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;

    @Autowired
    private CommentService commentService;


    @RequestMapping(value = "/note/{noteId}", method = RequestMethod.GET)
    public String getNoteByNoteId(@PathVariable("noteId") String noteId, Model model) {

        Document document = noteService.getNodeByNoteId(noteId);

        if(document == null) {
            model.addAttribute("msg", "can't find note");
            return "error";
        }

        Set<Document> similarDocument = noteService.similarNoteRecommend(noteId);
        List<Comment> comments = commentService.getNoteComments(noteId);

        model.addAttribute("similarNote", similarDocument);
        model.addAttribute("note", document);
        model.addAttribute("comments", comments);
        return "main";
    }



    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String addComment(@RequestBody Comment comment, HttpSession session){
        User user = (User) session.getAttribute("USER_SESSION");
        comment.setUserId(user.getUserId());
        if(commentService.addComment(comment)){
            return "success";
        } else {
            return "fail";
        }
    }

    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.DELETE)
    public String deleteComment(@RequestBody Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("USER_SESSION");
        comment.setUserId(user.getUserId());
        if(commentService.deleteComment(comment)){
            return "success";
        } else {
            return "fail";
        }
    }
}
