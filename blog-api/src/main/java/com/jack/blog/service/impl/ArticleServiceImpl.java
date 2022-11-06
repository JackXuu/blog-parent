package com.jack.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.blog.dao.dos.Archives;
import com.jack.blog.dao.mapper.ArticleMapper;
import com.jack.blog.dao.pojo.Article;
import com.jack.blog.service.ArticleService;
import com.jack.blog.service.TagService;
import com.jack.blog.vo.ArticleVo;
import com.jack.blog.vo.Result;
import com.jack.blog.vo.TagVo;
import com.jack.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author jack
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Autowired
    TagService tagService;

    @Override
    public Result listArticle(PageParams pageParams) {
        /**
         *
         */
        Page<Article> page = new Page<>(pageParams.getPage(), pageParams.getPageSize());
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        /*// 是否置顶
        queryWrapper.orderByDesc(Article::getWeight);
        // 时间倒叙排序
        queryWrapper.orderByDesc(Article::getCreateDate);*/
        // 合二为一
        queryWrapper.orderByDesc(Article::getWeight, Article::getCreateDate);

        Page<Article> articlePage = articleMapper.selectPage(page, queryWrapper);
        List<Article> records = articlePage.getRecords();

        List<ArticleVo> articleVoList = copyList(records);
        return Result.success(articleVoList);
    }

    @Override
    public Result hotArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.orderByDesc(Article::getViewCounts);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles));
    }

    @Override
    public Result newArticle(int limit) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Article::getId, Article::getTitle);
        queryWrapper.orderByDesc(Article::getCreateDate);
        queryWrapper.last("limit " + limit);
        List<Article> articles = articleMapper.selectList(queryWrapper);
        return Result.success(copyList(articles));
    }

    @Override
    public Result listArticle() {
        List<Archives> archivesList = articleMapper.listArchives();

        return Result.success(archivesList);
    }

    private List<ArticleVo> copyList(List<Article> articles) {
        List<ArticleVo> list = new ArrayList<>();
        for (Article record : articles) {
            ArticleVo articleVo = new ArticleVo();
            BeanUtils.copyProperties(record, articleVo);
            articleVo.setCreateDate(new DateTime(record.getCreateDate()).toString("yyyy-mm-dd HH:mm"));
            
            // 获取Tag信息
            List<TagVo> tagVoList = tagService.findTagsByArticleId(articleVo.getId());
            articleVo.setTags(tagVoList);

            list.add(articleVo);
        }
        return list;
    }
}
