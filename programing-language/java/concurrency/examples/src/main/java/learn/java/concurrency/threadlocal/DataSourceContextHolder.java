package learn.java.concurrency.threadlocal;

/**
 * DataSourceContextHolder
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    public static String getDbType() {
        return (contextHolder.get());
    }

    public static void clearDbType() {
        contextHolder.remove();
    }

    public static void main(String[] args) {
        try {
            DataSourceContextHolder.setDbType("ds-foo");
            // do something
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceContextHolder.clearDbType();
        }
    }
}