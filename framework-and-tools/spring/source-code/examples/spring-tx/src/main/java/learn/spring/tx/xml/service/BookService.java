package learn.spring.tx.xml.service;

import learn.spring.tx.xml.MockExceptionUtils;
import learn.spring.tx.xml.dao.BookDao;

/**
 * BookService.
 */
public class BookService {

    private BookDao bookDao;

    public BookDao getBookDao() {
        return bookDao;
    }

    public void setBookDao(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    /**
     * 结账：传入哪个用户买了哪本书
     */
    public void checkout(String username, int id) {
        try {
            bookDao.updateStock(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username, price);
        MockExceptionUtils.mockException();
    }
}
