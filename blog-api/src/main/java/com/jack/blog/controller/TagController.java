package com.jack.blog.controller;

import com.jack.blog.service.TagService;
import com.jack.blog.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author jack
 */
@RestController
@RequestMapping("tags")
public class TagController {

    @Autowired
    TagService tagService;

    /**
     * 标签所拥有最多的文章，排名前n的标签
     * @return
     */
    @GetMapping("hot")
    public Result hot(){
        int limit = 3;
        return tagService.hots(limit);
    }




}
