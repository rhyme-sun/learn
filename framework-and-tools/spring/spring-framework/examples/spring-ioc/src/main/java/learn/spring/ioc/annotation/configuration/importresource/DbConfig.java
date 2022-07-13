package learn.spring.ioc.annotation.configuration.importresource;

/**
 * DbConfig.
 */
public class DbConfig {

    private String url;
    private String username;
    private String password;

    public DbConfig(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "DbConfig{" +
                "url='" + url + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
