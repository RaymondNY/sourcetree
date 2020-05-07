package com.example.one.api;

import com.example.one.common.CookieUtils;
import com.example.one.common.JSONResult;
import com.example.one.common.JsonUtils;
import com.example.one.common.MD5Utils;
import com.example.one.pojo.bo.UserBo;
import com.example.one.pojo.po.User;
import com.example.one.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
public class ApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBo userBo, HttpServletRequest request,
                             HttpServletResponse response) {
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmPassword = userBo.getConfirmPassword();
        // 1. 判断用户名和密码不为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        // 2. 判断用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }
        // 3. 判断用户密码不能少于6位
        if (password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能小于6");
        }
        // 4. 判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return JSONResult.errorMsg("两次密码输入不一致");
        }
        // 5. 注册
        User userResult = userService.createUser(userBo);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user", JsonUtils.objectToJson(userResult), true);
        return JSONResult.ok();
    }
    @PostMapping("/changePassword")
    public JSONResult changePassword(@RequestBody UserBo userBo, HttpServletRequest request,
                             HttpServletResponse response) throws Exception {
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String newPassword = userBo.getNewPassword();
        // 1. 判断用户名和密码不能为空
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)|| StringUtils.isBlank(newPassword)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        // 2. 实现登录
        User userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        // 3. 账号密码校验
        if (userResult == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }
        return JSONResult.ok();
    }

    /**
     * 设置用户对象
     *
     * @param userResult
     */
    private User setNullProperty(User userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
