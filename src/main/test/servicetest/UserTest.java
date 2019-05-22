package servicetest;

import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import cn.edu.zucc.caviar.searchengine.core.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserTest {
    @Test
    public void registerTest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        UserService service = applicationContext.getBean(UserService.class);
        User user = new User();
        user.setRegisterId("jiangDoor@zucc.edu.cn");
        user.setGender("male");
        user.setPassword("123456");

        service.registerUser(user);
    }

    @Test
    public void insertLikeNote() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/applicationContext.xml");

        UserService service = applicationContext.getBean(UserService.class);

        User user = new User();
        user.setUserId(1);

        service.insertLikeNote(user, "5c9cee85000000000f01f16d");


    }


}
