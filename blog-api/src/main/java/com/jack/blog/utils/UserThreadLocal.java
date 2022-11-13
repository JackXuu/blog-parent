package com.jack.blog.utils;

import com.jack.blog.dao.pojo.SysUser;

/**
 * @Description:
 * @Author jack
 */

public class UserThreadLocal {

    private UserThreadLocal(){}

    private static final ThreadLocal<SysUser> local = new ThreadLocal<>();

    public static void put(SysUser sysUser) {
        local.set(sysUser);
    }

    public static SysUser get() {
        return local.get();
    }

    public static void remove(){
        local.remove();
    }
}
