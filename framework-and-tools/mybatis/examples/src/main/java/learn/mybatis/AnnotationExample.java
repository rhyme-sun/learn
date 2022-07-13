package learn.mybatis;

import javax.sql.DataSource;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import learn.mybatis.entity.Blog;
import learn.mybatis.mapper.BlogAnnotationMapper;
import learn.mybatis.mapper.BlogMapper;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.datasource.unpooled.UnpooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

/**
 * AnnotationExample.
 */
public class AnnotationExample {

    public static void main(String[] args) {
        SqlSessionFactory sqlSessionFactory = createSqlSessionFactory();
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog = session.selectOne("learn.mybatis.mapper.BlogAnnotationMapper.selectBlog", 1);
            System.out.println(blog);
        }
        // or
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogAnnotationMapper mapper = session.getMapper(BlogAnnotationMapper.class);
            System.out.println(mapper.selectBlog(1));
        }
    }

    /**
     * 编码配置 SqlSessionFactory
     */
    private static SqlSessionFactory createSqlSessionFactory() {
        DataSource dataSource = createDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogAnnotationMapper.class);
        return new SqlSessionFactoryBuilder().build(configuration);
    }

    private static DataSource createDataSource() {
        try {
            String path = "db.properties";
            InputStream inputStream = Resources.getResourceAsStream(path);
            Properties properties = new Properties();
            properties.load(inputStream);
            return new PooledDataSource((String) properties.get("driver"), (String) properties.get("url"),
                    (String) properties.get("username"), (String) properties.get("password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
