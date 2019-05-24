package cn.edu.zucc.caviar.searchengine.core.dao;


import cn.edu.zucc.caviar.searchengine.core.pojo.UserDocumentRecord;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface NoteDao {

    public List<UserDocumentRecord> selectNoteList(UserDocumentRecord userDocumentRecord);

    public Integer selectNoteListCount(UserDocumentRecord userDocumentRecord);

    public Integer insertToCollection(@Param("userId") Integer userId, @Param("noteId") String noteId,@Param("date") Timestamp date);
    public Integer insertToLike(@Param("userId") Integer userId, @Param("noteId") String noteId,@Param("date") Timestamp date);

    public Integer deleteToCollection(@Param("userId") Integer userId, @Param("noteId") String noteId);
    public Integer deleteToLike(@Param("userId") Integer userId, @Param("noteId") String noteId);
}
