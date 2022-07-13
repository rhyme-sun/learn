package learn.spring.tx.xml.dao;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * BookDao.
 */
public class BookDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 减去某个用户的余额
     */
    public void updateBalance(String userName, int price) {
        String sql = "update account set balance=balance-? where username=?";
        jdbcTemplate.update(sql, price, userName);
    }

    /**
     * 按照图书的id来获取图书的价格
     */
    public int getPrice(int id) {
        String sql = "select price from book where id=?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id);
    }

    /**
     * 减库存，减去某本书的库存
     */
    public void updateStock(int id) {
        String sql = "update book_stock set stock=stock-1 where id=?";
        jdbcTemplate.update(sql, 1);
//        MockExceptionUtils.mockException();
    }
}
