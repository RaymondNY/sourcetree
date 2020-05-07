package com.example.one.service;

import com.example.one.pojo.bo.UserBo;
import com.example.one.pojo.po.User;

public interface UserService {
    /**
     * 用户注册
     *
     * @return
     */
    User createUser(UserBo userBo);

    /**
     * 判断用户名是否存在
     *
     * @param username
     * @return
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 检索用户名和密码是否匹配，用于登录
     *
     * @param username
     * @param password
     * @return
     */
    User queryUserForLogin(String username, String password);
}
