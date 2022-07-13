package learn.spring.tx.annotation.service;

import learn.spring.tx.annotation.dao.BookDao;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * BookService.
 */
public class BookService {

    @Autowired
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
//    @Transactional(propagation = Propagation.REQUIRED)
    public void checkout(String username,int id){

        bookDao.updateStock(id);
        int price = bookDao.getPrice(id);
        bookDao.updateBalance(username,price);
//        try{

//        for (int i = 1 ;i>=0 ;i--)
//            System.out.println(10/i);
//        }catch (Exception e){
//            System.out.println("...............");
//        }
    }
}
