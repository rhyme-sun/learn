package learn.design.pattern.creational.prototype;

/**
 * 搜索关键字
 */
public class SearchWord {

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 搜索次数
     */
    private int times;

    /**
     * 上次更新时间戳
     */
    private long lastUpdateTime;

    public SearchWord() {
    }

    public SearchWord(String keyword, int times, long lastUpdateTime) {
        this.keyword = keyword;
        this.times = times;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
