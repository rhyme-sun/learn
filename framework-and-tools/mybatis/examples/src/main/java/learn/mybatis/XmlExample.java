package learn.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import learn.mybatis.entity.Blog;
import learn.mybatis.mapper.BlogMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * Main.
 */
public class XmlExample {

    public static void main(String[] args){
        SqlSessionFactory sqlSessionFactory = createSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog1 = mapper.selectBlog(1);
            Blog blog2 = mapper.selectBlog(1);
            System.out.println(blog1);
            System.out.println(blog2);
        }
        // or
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog = session.selectOne("learn.mybatis.mapper.BlogMapper.selectBlog", "1");
            System.out.println(blog);
        }
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog = session.selectOne("selectBlog", 1);
            System.out.println(blog);
        }
    }

    private static SqlSessionFactory createSqlSessionFactory() {
        try {
            String resource = "mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(resource);
            return new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
