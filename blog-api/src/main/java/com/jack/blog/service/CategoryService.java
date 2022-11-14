package com.jack.blog.service;


import com.jack.blog.vo.CategoryVo;
import com.jack.blog.vo.Result;

public interface CategoryService {

    CategoryVo findCategoryById(Long categoryId);


    Result findAll();
}
