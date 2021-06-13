package hash.consistenthash;

/**
 * @author 🌺xuliangliang🌺
 * @Description
 * @Date 2021/6/13
 */
public interface BookStorage {

    /**
     * add book
     * @param book
     */
    void addBook(Book book);

    /**
     * query book
     * @param id
     * @return
     */
    Book searchBook(long id);

}
