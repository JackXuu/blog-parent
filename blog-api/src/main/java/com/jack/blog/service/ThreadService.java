package com.jack.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.jack.blog.dao.mapper.ArticleMapper;
import com.jack.blog.dao.pojo.Article;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author jack
 */

@Component
public class ThreadService {

    // 期望更新操作在线程池执行，不影响主流程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {

        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts + 1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        // 多线程时线程安全
        updateWrapper.eq(Article::getId, article.getId());
        // update article set view_count=100 where view_count=99 and id=11
        articleMapper.update(articleUpdate, updateWrapper);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("执行完毕");
        }

    }
}
