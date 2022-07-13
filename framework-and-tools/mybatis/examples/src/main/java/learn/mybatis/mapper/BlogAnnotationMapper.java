package learn.mybatis.mapper;

import learn.mybatis.entity.Blog;
import org.apache.ibatis.annotations.Select;

/**
 * BlogAnnotationMapper.
 */
public interface BlogAnnotationMapper {

    @Select("select * from blog where id =#{id}")
    Blog selectBlog(Integer id);
}
