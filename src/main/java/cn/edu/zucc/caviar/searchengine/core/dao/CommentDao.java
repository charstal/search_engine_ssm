package cn.edu.zucc.caviar.searchengine.core.dao;

import cn.edu.zucc.caviar.searchengine.core.pojo.Comment;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface CommentDao {

    public Integer addComment(Comment comment);
    public Integer deleteComment(Comment comment);

    public List<Comment> getNoteComments(@Param("noteId") String noteId);

    public Integer likeCommentRecord(@Param("userId")Integer userId, @Param("commentId") Integer commentId, @Param("date")Timestamp timestamp);
    public Integer dislikeCommentRecord(@Param("userId")Integer userId, @Param("commentId") Integer commentId);

    public Integer likeComment(@Param("commentId") Integer commentId);
    public Integer dislikeComment(@Param("commentId") Integer commentId);

}
