package com.jack.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jack.blog.dao.dos.Archives;
import com.jack.blog.dao.mapper.ArticleBodyMapper;
import com.jack.blog.dao.mapper.ArticleMapper;
import com.jack.blog.dao.pojo.Article;
import com.jack.blog.dao.pojo.ArticleBody;
import com.jack.blog.dao.pojo.SysUser;
import com.jack.blog.service.*;
import com.jack.blog.vo.*;
import com.jack.blog.vo.params.PageParams;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.jack.blog.vo.ErrorCode.RECORD_NOT_EXIST;

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

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ArticleBodyMapper articleBodyMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ThreadService threadService;

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

    @Override
    public Result findArticlebyId(Long articleId) {
        Article article = articleMapper.selectById(articleId);
        if (null == article) {
            return  Result.fail(ErrorCode.RECORD_NOT_EXIST.getCode(), RECORD_NOT_EXIST.getMsg());
        }
        ArticleVo articleVo = copy(article, true, true, true, true);
        //查看完文章了，新增阅读数，有没有问题呢？
        //查看完文章之后，本应该直接返回数据了，这时候做了一个更新操作，更新时加写锁，阻塞其他的读操作，性能就会比较低
        // 更新 增加了此次接口的 耗时 如果一旦更新出问题，不能影响 查看文章的操作
        //线程池  可以把更新操作 扔到线程池中去执行，和主线程就不相关了

        threadService.updateArticleViewCount(articleMapper, article);
        return Result.success(articleVo);
    }

    private ArticleVo copy(Article article, boolean isTag, boolean isAuthor, boolean isBody,boolean isCategory){
        ArticleVo articleVo = new ArticleVo();
        articleVo.setId(article.getId());
        BeanUtils.copyProperties(article, articleVo);

        articleVo.setCreateDate(new DateTime(article.getCreateDate()).toString("yyyy-MM-dd HH:mm"));
        //并不是所有的接口 都需要标签 ，作者信息
        if (isTag){
            Long articleId = article.getId();
            articleVo.setTags(tagService.findTagsByArticleId(articleId));
        }
        if (isAuthor){
            Long authorId = article.getAuthorId();
            SysUser sysUser = sysUserService.findUserById(authorId);
            UserVo userVo = new UserVo();
            userVo.setAvatar(sysUser.getAvatar());
            userVo.setId(sysUser.getId().toString());
            userVo.setNickname(sysUser.getNickname());
            articleVo.setAuthor(userVo);
        }
        if (isBody){
            Long bodyId = article.getBodyId();
            articleVo.setBody(findArticleBodyById(bodyId));
        }
        if (isCategory){
            Long categoryId = article.getCategoryId();
            articleVo.setCategory(categoryService.findCategoryById(categoryId));
        }
        return articleVo;
    }



    private ArticleBodyVo findArticleBodyById(Long bodyId) {
        ArticleBody articleBody = articleBodyMapper.selectById(bodyId);
        ArticleBodyVo articleBodyVo = new ArticleBodyVo();
        articleBodyVo.setContent(articleBody.getContent());
        return articleBodyVo;
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
