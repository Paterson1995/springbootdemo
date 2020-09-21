package com.demo.springbootdemo.aspect;

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
import java.util.*;

@Component
@Aspect
public class MyAspect {

    private Logger logger = LoggerFactory.getLogger(MyAspect.class);

    /**
     * execution()是最常用的切点函数，整个表达式可以分为五个部分：
     * public：方法修饰符的的类型，可选
     * 第一个*号：表示返回类型，*号表示所有的类型
     * com.demo.springbootdemo.service..:包名，后面的..表示当前包和当前包的所有子包
     * 第二个*号：表示类名，*号表示所有的类。
     * *(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
     */
    @Pointcut("execution(public * com.demo.springbootdemo.service..*.*(..))")
    public void pc(){
    }

//    @Before("execution(public * com.demo.springbootdemo.service..*.*(..))&&args(name,..)")
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

    }

//    @After("pc()")
    public void after(){
        logger.info("*****************************后置切面");
    }

//    @AfterReturning(pointcut = "execution(public * com.demo.springbootdemo.controller.TestController.index(..))", returning="s")
    public void afterReturning(String s) {

        logger.info("afterReturning={}", s);
    }

//    @AfterThrowing(pointcut = "execution(public * com.demo.springbootdemo.controller.TestController.index(..))", throwing = "exception")
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
//    @Around("pc()")
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
