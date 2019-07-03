package cn.edu.zucc.caviar.searchengine.core.service.impl;

import cn.edu.zucc.caviar.searchengine.core.dao.CommentDao;
import cn.edu.zucc.caviar.searchengine.core.pojo.Comment;
import cn.edu.zucc.caviar.searchengine.core.service.CommentService;
import cn.edu.zucc.caviar.searchengine.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {


    @Autowired
    private CommentDao commentDao;



    @Override
    public boolean addComment(Comment comment) {
        comment.setDate(new Timestamp(System.currentTimeMillis()));
        int row = this.commentDao.addComment(comment);

        if (row <= 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean deleteComment(Comment comment) {

        int row = this.commentDao.deleteComment(comment);

        if (row <= 0)
            return false;
        else
            return true;
    }

    @Override
    public List<Comment> getNoteComments(String noteId) {
        List<Comment> list = this.commentDao.getNoteComments(noteId);

        return list;
    }

    @Override
    public boolean likeComment(Integer userId, Integer commendId) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int row = this.commentDao.likeCommentRecord(userId, commendId, timestamp);
        this.commentDao.likeComment(commendId);

        if (row <= 0)
            return false;
        else
            return true;
    }

    @Override
    public boolean dislikeComment(Integer userId, Integer commendId) {


        int row = this.commentDao.dislikeCommentRecord(userId, commendId);
        this.commentDao.dislikeComment(commendId);

        if (row <= 0)
            return false;
        else
            return true;
    }


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        CommentService commentService = applicationContext.getBean(CommentService.class);

        commentService.dislikeComment(4, 2);
    }
}
