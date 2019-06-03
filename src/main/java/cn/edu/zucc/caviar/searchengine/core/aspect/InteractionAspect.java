package cn.edu.zucc.caviar.searchengine.core.aspect;

import cn.edu.zucc.caviar.searchengine.common.kafka.KafkaProducerServer;
import cn.edu.zucc.caviar.searchengine.core.pojo.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Aspect    //切面
@Component
public class InteractionAspect {

    @Pointcut("execution(* cn.edu.zucc.caviar.searchengine.core.controller.SearchController.*(..))")
    public void declearJoinPointExpression(){}

    @Autowired
    KafkaProducerServer kafkaProducer;

    private String topic = "interactive";
    private String value = "";
    private String ifPartition = "1";
    private Integer partitionNum = 2;
    private String role = "test";

    @Before("declearJoinPointExpression()")
    public void beforMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        if(methodName.equals("keywordSearch"))
        {
            System.out.println("ASPECT search");
            String searchWords = (String) args.get(0);
            String type = " 1 ";
            String uid;
            HttpServletRequest request =((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpSession session =request.getSession();
            User user = (User) session.getAttribute("USER_SESSION");
            if(user==null)
                uid = session.getId();
            else
                uid = String.valueOf(user.getUserId());
            value = uid+type+searchWords;
            System.out.println(value);
            Map<String,Object> res = kafkaProducer.sndMesForTemplate(topic, value, ifPartition, partitionNum, role);
        }
    }
}
