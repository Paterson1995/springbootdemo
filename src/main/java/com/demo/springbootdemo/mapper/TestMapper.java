package com.demo.springbootdemo.mapper;


import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TestMapper {

    public List<Map<String,Object>> exportExcel(JSONObject jsonObject);
}
