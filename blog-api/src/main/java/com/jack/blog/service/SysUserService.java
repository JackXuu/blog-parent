package com.jack.blog.service;

import com.jack.blog.dao.pojo.SysUser;
import com.jack.blog.vo.Result;

public interface SysUserService {
    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    SysUser findUserById(Long id);

    void save(SysUser user);
}
