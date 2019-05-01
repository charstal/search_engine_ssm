package cn.edu.zucc.caviar.searchengine.core.service.impl;

import cn.edu.zucc.caviar.searchengine.common.utils.EncryptUtil;
import cn.edu.zucc.caviar.searchengine.core.dao.UserDao;
import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.UserService;
import com.sun.xml.internal.bind.v2.TODO;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private EncryptUtil encryptUtil;


    @Autowired
    private UserDao userDao;


    @Override
    public User findUserByRegisterId(String registerId, String password) {
        String md5Password = encryptUtil.encrypt(password);
        User user = userDao.findUserByRegisterId(registerId);
        if(password == null) {
            //TODO
            // diff between logged and no logged
        }
        else if(md5Password.equals(user.getPassword())){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            user.setLastLoginTime(timestamp);

            int rows = userDao.updateUser(user);

            if(rows <= 0)
                user = null;

        }
        else
            user = null;

        return user;
    }

    @Override
    public User registerUser(User user) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String md5Password = encryptUtil.encrypt(user.getPassword());
        user.setPassword(md5Password);
        user.setRegisterTime(timestamp);
        user.setLastLoginTime(timestamp);

        int rows = userDao.createUser(user);
        if(rows <= 0) {
            user = null;
        }

        return user;
    }

    @Override
    public User updateUser(User user) {
        int rows = userDao.updateUser(user);

        if (rows <= 0) {
            user = null;
        } else {
            user = userDao.findUserById(user.getUserId());
        }

        return user;
    }


}
