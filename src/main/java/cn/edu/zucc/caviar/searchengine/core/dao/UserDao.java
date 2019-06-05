package cn.edu.zucc.caviar.searchengine.core.dao;

import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    public List<User> loadAllUser();

    public User findUserByRegisterId(@Param("registerId") String registerId);

    public User findUserById(@Param("id") Integer id);

    public int createUser(User user);

    public int updateUser(User user);

    public int insertLikeNote(@Param("userId") Integer userId, @Param("noteId") String noteId);

    public int insertCollectNote(@Param("userId")Integer userId, @Param("noteId") String noteId);

    public int enableUser(@Param("userId") Integer userId);
    public int disableUser(@Param("userId") Integer userId);
}
