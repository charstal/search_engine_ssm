package utilstest;

import cn.edu.zucc.caviar.searchengine.common.utils.EncryptUtil;
import org.junit.Test;

public class EncryptTest {

    private EncryptUtil encryptUtil = new EncryptUtil();

    @Test
    public void testMd5() {
        System.out.println(encryptUtil.encrypt("1234567"));
    }

    @Test
    public void testlogin() {
        String password = encryptUtil.encrypt("123456adfaf");
        if(encryptUtil.encrypt("123456adfaf").equals(password)) {
            System.out.println("密码正确");
        } else {
            System.out.println("密码错误");
        }
    }
}
