package com.jack.blog.service;

import com.jack.blog.dao.pojo.SysUser;
import com.jack.blog.vo.Result;
import com.jack.blog.vo.UserVo;

public interface SysUserService {
    UserVo findUserVoById(Long id);

    SysUser findUser(String account, String password);

    Result findUserByToken(String token);

    SysUser findUserByAccount(String account);

    SysUser findUserById(Long id);

    void save(SysUser user);
}
