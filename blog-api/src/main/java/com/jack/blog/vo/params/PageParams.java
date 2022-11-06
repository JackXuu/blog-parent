package com.jack.blog.vo.params;

import lombok.Data;

/**
 * @Description:
 * @Author jack
 */
@Data
public class PageParams {

    private int page = 1;

    private int pageSize = 10;

}
