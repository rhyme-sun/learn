package learn.design.pattern.creational.factory.dicontainer;

public class RedisCounter {

    private String ipAddress;

    private Integer port;

    public RedisCounter(String ipAddress, Integer port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
