package com.jack.blog.service;

import com.jack.blog.vo.Result;
import com.jack.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);


    Result hots(int limit);
}
