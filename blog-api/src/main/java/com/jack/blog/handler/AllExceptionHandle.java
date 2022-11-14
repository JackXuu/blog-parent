package com.jack.blog.handler;

import com.jack.blog.vo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description:
 * @Author jack
 */
@RestControllerAdvice
public class AllExceptionHandle {

    @ExceptionHandler
    public Result doException(Exception e) {
        e.printStackTrace();

        return Result.fail(-999, "系统异常");
    }


}
