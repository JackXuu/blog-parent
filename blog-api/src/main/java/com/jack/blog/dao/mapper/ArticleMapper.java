package com.jack.blog.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jack.blog.dao.dos.Archives;
import com.jack.blog.dao.pojo.Article;

import java.util.List;

/**
 * @Description:
 * @Author jack
 */
public interface ArticleMapper extends BaseMapper<Article> {


    List<Archives> listArchives();

}
