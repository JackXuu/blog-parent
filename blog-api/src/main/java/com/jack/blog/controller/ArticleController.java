package com.jack.blog.controller;

import com.jack.blog.service.ArticleService;
import com.jack.blog.vo.Result;
import com.jack.blog.vo.params.PageParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author jack
 */
@RestController
@RequestMapping("articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 首页 文章列表
     * @param pageParams
     */
    @PostMapping
    public Result listArticle(@RequestBody PageParams pageParams){

        return articleService.listArticle(pageParams);
    }

    /**
     * 最热文章
     * @return
     */
    @PostMapping("hot")
    public Result hotArticle(){
        int limit = 3;
        return articleService.hotArticle(limit);
    }

    /**
     * 最新文章
     * @return
     */
    @PostMapping("new")
    public Result newArticle(){
        int limit = 3;
        return articleService.newArticle(limit);
    }

    /**
     * 文章归档
     */
    @PostMapping("listArticle")
    public Result listArticle(){
        return articleService.listArticle();
    }
}
