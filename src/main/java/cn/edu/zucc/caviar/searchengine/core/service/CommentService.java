package cn.edu.zucc.caviar.searchengine.core.service;

import cn.edu.zucc.caviar.searchengine.core.pojo.Comment;

import java.util.List;

public interface CommentService {

    public boolean addComment(Comment comment);
    public boolean deleteComment(Comment comment);

    public List<Comment> getNoteComments(String noteId);

    public boolean likeComment(Integer userId, Integer commendId);
    public boolean dislikeComment(Integer userId, Integer commendId);
}
