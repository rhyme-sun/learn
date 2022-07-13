package learn.mybatis.mapper;

import learn.mybatis.entity.Blog;

/**
 * BlogMapper.
 */
public interface BlogMapper {

    Blog selectBlog(Integer id);
}
