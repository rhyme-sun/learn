package learn.java.concurrency.threadlocal;

/**
 * UserContext.
 * <a href="https://www.liaoxuefeng.com/wiki/1252599548343744/1306581251653666">UserContext</a>
 */
public class UserContext implements AutoCloseable {

    static final ThreadLocal<String> ctx = new ThreadLocal<>();

    public UserContext(String user) {
        ctx.set(user);
    }

    public static String currentUser() {
        return ctx.get();
    }

    @Override
    public void close() {
        ctx.remove();
    }

    public static void main(String[] args) {
        try (UserContext ctx = new UserContext("Bob")) {
            String currentUser = UserContext.currentUser();
            // do something
        }
    }
}