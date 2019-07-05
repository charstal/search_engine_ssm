package cn.edu.zucc.caviar.searchengine.core.pojo;

import java.sql.Timestamp;

public class Comment {
    private Integer commentId;
    private Integer userId;
    private String userName;
    private String avatarUrl;
    private String content;
    private Timestamp date;
    private String noteId;
    private Double score;
    private Integer star;

    public Comment() {

    }

    public Comment(Integer userId, String content, String noteId, Double score) {
        this.userId = userId;
        this.content = content;
        this.noteId = noteId;
        this.score = score;
        this.date = new Timestamp(System.currentTimeMillis());
    }

    public Comment(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }
}
