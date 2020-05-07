package com.example.one.service.impl;

import com.example.one.common.DateUtil;
import com.example.one.common.MD5Utils;
import com.example.one.dao.UserDao;
import com.example.one.pojo.Sex;
import com.example.one.pojo.bo.UserBo;
import com.example.one.pojo.po.User;
import com.example.one.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User createUser(UserBo userBo) {
        User user = new User();
        user.setUsername(userBo.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户昵称同用户名
        user.setNickname(userBo.getUsername());
        // 设置默认头像
        user.setFace("abc");
        // 设置默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 设置默认性别
        user.setSex(Sex.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        userDao.save(user);
        return user;
    }

    @Override
    public boolean queryUsernameIsExist(String username) {
        User result = userDao.findByUsername(username);
        return result == null ? false : true;
    }

    @Override
    public User queryUserForLogin(String username, String password) {
        User user = userDao.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public boolean changePwd(User user) {
        user.setUpdatedTime(new Date());
        User res = userDao.save(user);
        return res == null ? false : true;
    }
}
