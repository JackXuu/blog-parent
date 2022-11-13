package com.jack.blog.service;

import com.jack.blog.dao.pojo.SysUser;
import com.jack.blog.vo.Result;
import com.jack.blog.vo.params.LoginParam;

public interface LoginService {
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);

    Result logout(String token);

    Result register(LoginParam loginParam);
}
