package com.demo.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/redis")
public class RedisController {

    //若使用RedisTemplate，因为其默认使用的是JDK序列化器，而它使用的编码是ISO-8859-1,会出现乱码，需要setKeySerializer(new StringRedisSerializer())
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

//    @Resource(name = "stringRedisTemplate")
//    private ValueOperations<String, String> vOps;

    @RequestMapping("/set")
    @ResponseBody
    public String set(String key, String value){

        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key, value);
        String result = "key:"+ key + "---value:" + valueOperations.get(key);
        return result;
    }

    @RequestMapping("/transanction")
    @ResponseBody
    public String transanction(String k1, String k2) {

        stringRedisTemplate.setEnableTransactionSupport(true);
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        stringRedisTemplate.multi();
        String before = valueOperations.get(k1)+"---------"+valueOperations.get(k2);
        valueOperations.set(k1, valueOperations.get(k1)+"1");
        valueOperations.set(k2, valueOperations.get(k2)+"2");
        stringRedisTemplate.exec();
        return "before:" + before + "\n"+ "after:" + valueOperations.get(k1)+"---------"+valueOperations.get(k2);
    }

    //使用redis的session管理。注意:当session中数据发生变化时必须将session中变化的数据同步到redis中
    @RequestMapping("session/get")
    public void getSession(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<String> list = (List<String>) request.getSession().getAttribute("list");
        if(list==null){
            list = new ArrayList<>();
        }
        list.add("xxxx");
        request.getSession().setAttribute("list",list);//每次session变化都要同步session

        response.getWriter().println("size: "+list.size());
        response.getWriter().println("sessionid: "+request.getSession().getId());
    }

    @RequestMapping("session/clear")
    public void clearSession(HttpServletRequest request){
        request.getSession().invalidate();
    }

}
