package cn.edu.zucc.caviar.searchengine.core.service;

import cn.edu.zucc.caviar.searchengine.core.pojo.User;

import java.util.List;

public interface UserService {
    public User findUserByRegisterId(User user);
    public List<User> loadAllUser();
    public User registerUser(User user);
    public User updateUser(User user);
    public User insertLikeNote(User user, String noteId);
    public User insertCollectNote(User user, String noteId);

    public Boolean enableUser(Integer userId);
    public Boolean disableUser(Integer userId);
}
