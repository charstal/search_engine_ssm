package cn.edu.zucc.caviar.searchengine.core.service;

import cn.edu.zucc.caviar.searchengine.common.pagination.Page;
import cn.edu.zucc.caviar.searchengine.core.pojo.Document;
import cn.edu.zucc.caviar.searchengine.core.pojo.UserDocumentRecord;
import org.springframework.stereotype.Service;


public interface NoteService {
    public Document getNodeByNoteId(String noteId);

    public Page<UserDocumentRecord> findNoteList(Integer userId, Integer page, Integer rows);

    public boolean insertCollectionNoteForUser(Integer userId, String noteId);

    public boolean insertLikeNoteForUser(Integer userId, String noteId);

    public boolean deleteCollectionNoteForUser(Integer userId, String noteId);

    public boolean deleteLikeNoteForUser(Integer userId, String noteId);
}
