package cn.edu.zucc.caviar.searchengine.core.controller;


import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;


    @RequestMapping(value = "/note/{noteId}", method = RequestMethod.GET)
    public String getNoteByNoteId(@PathVariable("noteId") String noteId, Model model) {

        Document document = noteService.getNodeByNoteId(noteId);

        if(document == null) {
            model.addAttribute("msg", "can't find note");
            return "error";
        }

        model.addAttribute("note", document);
        return "main";
    }


}
