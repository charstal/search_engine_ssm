package cn.edu.zucc.caviar.searchengine.core.dao;

import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserDao {
    public User findUserByRegisterId(@Param("register_id") String registerId);
    public User findUserById(@Param("id") Integer id);
    public int createUser(User user);
    public int updateUser(User user);
}
