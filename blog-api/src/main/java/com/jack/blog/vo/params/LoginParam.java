package com.jack.blog.vo.params;

import lombok.Data;

/**
 * @Description:
 * @Author jack
 */
@Data
public class LoginParam {

    private String account;

    private String password;

    private String nickname;
}
