package cn.edu.zucc.caviar.searchengine.core.service.impl;

import cn.edu.zucc.caviar.searchengine.common.utils.EncryptUtil;
import cn.edu.zucc.caviar.searchengine.core.dao.UserDao;
import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Timestamp;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private EncryptUtil encryptUtil;

    @Autowired
    private UserDao userDao;


    @Override
    public User findUserByRegisterId(User user) {

        if(user.getPassword() == null || "".equals(user.getPassword())) {
            user = userDao.findUserById(user.getUserId());
            return user;
        }

        String md5Password = encryptUtil.encrypt(user.getPassword());
        User searchedUser = userDao.findUserByRegisterId(user.getRegisterId());
        if(md5Password.equals(searchedUser.getPassword())){
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            searchedUser.setLastLoginTime(timestamp);

//            System.out.println(user.getUserId());
            int rows = userDao.updateUser(searchedUser);

            if(rows <= 0)
                searchedUser = null;

        }
        else
            searchedUser = null;

        return searchedUser;
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
        if(user.getPassword() != null) {
            user.setPassword(encryptUtil.encrypt(user.getPassword()));
        }

        if(user.getAvatar() != null && !("".equals(user.getAvatar()))) {
            user.setAvatar(copy(user.getAvatar(), "images/avatar"));
        }

        int rows = userDao.updateUser(user);

        if (rows <= 0) {
            user = null;
        } else {
            user = userDao.findUserById(user.getUserId());
        }

        return user;
    }

    @Override
    public User insertLikeNote(User user, String noteId) {

        Integer userId = user.getUserId();

        int rows = userDao.insertLikeNote(userId, noteId);

        if (rows <= 0) {
            user = null;
        } else {
            user = userDao.findUserById(user.getUserId());
        }
        return null;
    }

    @Override
    public User insertCollectNote(User user, String noteId) {

        int rows = userDao.insertCollectNote(user.getUserId(), noteId);

        if (rows <= 0) {
            user = null;
        } else {
            user = userDao.findUserById(user.getUserId());
        }
        return null;
    }


    private static String copy(String srcPathStr, String desPathStr) {
        //获取源文件的名称
        String newFileName = srcPathStr.substring(srcPathStr.lastIndexOf("/")+1); //目标文件地址
        System.out.println("源文件:"+newFileName);

        String preFile = srcPathStr.substring(0, srcPathStr.lastIndexOf("/"));
        System.out.println(preFile);

        String webPath = desPathStr + File.separator + newFileName;

        desPathStr = preFile.substring(0, preFile.lastIndexOf("/")) + "/" + webPath; //源文件地址
        System.out.println("目标文件地址:"+desPathStr);
        try
        {
            FileInputStream fis = new FileInputStream(srcPathStr);//创建输入流对象
            FileOutputStream fos = new FileOutputStream(desPathStr); //创建输出流对象
            byte datas[] = new byte[1024*8];//创建搬运工具
            int len = 0;//创建长度
            while((len = fis.read(datas))!=-1)//循环读取数据
            {
                fos.write(datas,0,len);
            }
            fis.close();//释放资源
            fis.close();//释放资源
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  webPath;
    }


    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

    }

}
