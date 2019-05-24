package cn.edu.zucc.caviar.searchengine.core.service;

import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import org.springframework.stereotype.Service;


public interface NoteService {
    public Document getNodeByNoteId(String noteId);
}
