package com.jack.blog.dao.pojo;

import lombok.Data;

/**
 * @Description:
 * @Author jack
 */
@Data
public class ArticleBody {

    private Long id;

    private String content;

    private String contentHtml;

    private Long articleId;

}
