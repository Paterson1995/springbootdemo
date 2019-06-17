package com.demo.springbootdemo.aspect;

import com.demo.springbootdemo.domain.Student;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Component
@Aspect
public class TestAspect {

    private Logger logger = LoggerFactory.getLogger(TestAspect.class);

    @Pointcut("execution(public * com.demo.springbootdemo.controller.TestController.index*(..))")
    public void pc(){

    }


    @Before("execution(public * com.demo.springbootdemo.controller.TestController.index*(..))&&args(name,..)")
    public void before(JoinPoint joinPoint, String name){

        logger.info("*****************************前置切面");
        logger.info("方法参数name的值："+name);

        //用的最多通知的签名
        Signature signature = joinPoint.getSignature();
        logger.info("代理的方法名:"+signature.getName());
        logger.info("代理类的名字:"+signature.getDeclaringTypeName());

        List<Object> args = Arrays.asList(joinPoint.getArgs());
        logger.info("传入参数为："+args);

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());

        //获取请求参数
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String,String> parameterMap = new HashMap();
        while (enumeration.hasMoreElements()){
            String parameter = enumeration.nextElement();
            parameterMap.put(parameter,request.getParameter(parameter));
        }
        if(!parameterMap.isEmpty()) {
            logger.info("请求参数："+parameterMap.toString());
        }

        //获取Session信息
        HttpSession session = (HttpSession) requestAttributes.resolveReference(RequestAttributes.REFERENCE_SESSION);
        logger.info("当前用户名:"+((Student)session.getAttribute("session_user")).getName());
    }

    @After("pc()")
    public void after(){

        logger.info("*****************************后置切面");
    }

    @AfterReturning(pointcut = "execution(public * com.demo.springbootdemo.controller.TestController.index(..))", returning="s")
    public void afterReturning(String s) {

        logger.info("afterReturning={}", s);
    }

    @AfterThrowing(pointcut = "execution(public * com.demo.springbootdemo.controller.TestController.index(..))", throwing = "exception")
    public void doAfterThrowingAdvice(Throwable exception) {

        if (exception instanceof NullPointerException) {
            logger.info("发生了空指针异常!!!!!");
        }
    }

    //环绕通知使用一个代理ProceedingJoinPoint类型的对象来管理目标对象，
    // 所以此通知的第一个参数必须是ProceedingJoinPoint类型。
    // 在通知体内调用ProceedingJoinPoint的proceed()方法会导致后台的连接点方法执行。
    // proceed()方法也可能会被调用并且传入一个Object[]对象，该数组中的值将被作为方法执行时的入参。
    //必须有返回值，也是目标方法的返回值
    @Around("execution(public * com.demo.springbootdemo.controller.TestController.index2(..))")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) {

        logger.info("****************************相当于before");

        Object obj = new Object();
        try {
            obj = proceedingJoinPoint.proceed();
            logger.info("****************************相当于afterReturning");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.info("****************************相当于afterThrowing");
        }
        logger.info("****************************相当于after");
        return obj;

    }


}
