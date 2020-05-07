package com.example.one.api;

import com.example.one.dao.TestDao;
import com.example.one.pojo.po.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private TestDao testDao;

    @GetMapping("test/getTest")
    public String getTest(){
        return testDao.getOne(1l).toString();
    }

    @PostMapping("test/saveTest")
    public String saveTest(){
        Test test = new Test();
        test.setName("bbb");
        testDao.save(test);
        return testDao.getOne(1l).toString();
    }
}
