package com.jack.blog.service;

import com.jack.blog.vo.Result;
import com.jack.blog.vo.params.CommentParam;

public interface CommentsService {

    public Result commentsByArticleId(Long id);

    Result comment(CommentParam commentParam);
}
