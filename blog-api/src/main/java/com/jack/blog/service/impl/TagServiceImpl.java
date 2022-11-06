package com.jack.blog.service.impl;

import com.jack.blog.dao.mapper.TagMapper;
import com.jack.blog.dao.pojo.Tag;
import com.jack.blog.service.TagService;
import com.jack.blog.vo.Result;
import com.jack.blog.vo.TagVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @Description:
 * @Author jack
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    TagMapper tagMapper;

    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        List<Tag> tagsList = tagMapper.findTagsByArticleId(articleId);
        return copyList(tagsList);
    }

    @Override
    public Result hots(int limit) {
        // 先获取标签ID列表
        List<Long> tagIds = tagMapper.findHotTagIds(limit);
        if (CollectionUtils.isEmpty(tagIds)) {
            return  Result.success(Collections.emptyList());
        }

        // 再获取标签列表
        List<Tag> tagList = tagMapper.findTagsByIds(tagIds);

        return Result.success(tagList);
    }

    private List<TagVo> copyList(List<Tag> tagsList) {
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagsList) {
            TagVo tagVo = new TagVo();
            BeanUtils.copyProperties(tag, tagVo);
            tagVo.setId(String.valueOf(tag.getId()));
            tagVoList.add(tagVo);
        }
        return tagVoList;
    }

}
