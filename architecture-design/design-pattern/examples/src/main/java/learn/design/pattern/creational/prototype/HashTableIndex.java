package learn.design.pattern.creational.prototype;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 给从数据库查出的搜索关键字实体对象建立散列表索引，并定时更新散列表中的数据；
 * 更新数据时（不考虑数据删除）新增的数据添加的散列表中，更新的数据替换散列表中内容
 */
public class HashTableIndex {

    /**
     * 散列表索引
     */
    private ConcurrentHashMap<String, SearchWord> currentKeyWords = new ConcurrentHashMap<>();

    /**
     * 上次更新时间
     */
    private long lastUpdateTime = -1;

    /**
     * 刷新方法
     */
    public void refresh() {
        List<SearchWord> toBeUpdatedSearchWords = listModSearchWords(lastUpdateTime);
        long maxNewUpdateTime = lastUpdateTime;
        for (SearchWord searchWord : toBeUpdatedSearchWords) {
            String keyword = searchWord.getKeyword();
            // 取变更关键字列表最大的更新时间作为系统刷新列表的上次更新时间
            if (searchWord.getLastUpdateTime() > maxNewUpdateTime) {
                maxNewUpdateTime = searchWord.getLastUpdateTime();
            }
            if (currentKeyWords.containsKey(keyword)) {
                currentKeyWords.replace(keyword, searchWord);
            } else {
                currentKeyWords.put(keyword, searchWord);
            }
        }
        lastUpdateTime = maxNewUpdateTime;
    }

    /**
     * 获取变更的搜索关键字
     *
     * @param lastUpdateTime 上次更新时间
     * @return 搜索关键字列表，没有变更的数据返回空列表
     */
    private List<SearchWord> listModSearchWords(long lastUpdateTime) {
        // TODO... 从数据库中取出更新时间 > lastUpdateTime的数据
        return Collections.emptyList();
    }
}
