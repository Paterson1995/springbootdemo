package com.demo.springbootdemo.interceptor;


import com.demo.springbootdemo.domain.Student;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//preHandle是请求进入controller前执行的，
// postHandle是请求结束后视图渲染前执行的，但只有preHandle方法返回true的时候才会执行，
// afterCompletion是视图渲染完成后执行的，同样需要preHandle返回true，该方法通常用于清理资源等工作。
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    //第三个参数为响应的处理器
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {


        Student student = (Student) request.getSession().getAttribute("session_user");
        if (student==null){
            //拦截后跳转的方法
            response.sendRedirect(request.getContextPath()+"/");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
