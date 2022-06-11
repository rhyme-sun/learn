package learn.design.pattern.creational.prototype;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * {@link HashTableIndexInSameVersion1}的实现方式虽然满足了需求3，但是对于10万条数据，每次重新创建散列表的成本比较高，并且大部分数
 * 据没有变更，这样就浪费了很多开销。
 * 解决办法：每次更新变更数据不是重新创建哈希表索引，而是克隆旧的，将有变更的数据更新到克隆的哈希表索引中，这种利用已有对象进行拷贝的方式
 * 来创建新的对象的方式叫做原型模型。
 */
public class HashTableIndexInSameVersion2 {

    /**
     * 散列表索引
     */
    private HashMap<String, SearchWord> currentKeywords = new HashMap<>();

    /**
     * 上次更新时间
     */
    private long lastUpdateTime = -1;

    /**
     * 刷新散列表索引方法
     */
    public void refresh() {
        HashMap<String, SearchWord> newKeywords = (HashMap<String, SearchWord>) currentKeywords.clone();
        List<SearchWord> toBeUpdatedSearchWords = listModSearchWords(lastUpdateTime);
        long maxNewUpdateTime = lastUpdateTime;
        for (SearchWord searchWord : toBeUpdatedSearchWords) {
            String keyword = searchWord.getKeyword();
            // 取变更关键字列表最大的更新时间作为系统刷新散列表的上次更新时间
            if (searchWord.getLastUpdateTime() > maxNewUpdateTime) {
                maxNewUpdateTime = searchWord.getLastUpdateTime();
            }
            if (newKeywords.containsKey(keyword)) {
                newKeywords.replace(keyword, searchWord);
            } else {
                newKeywords.put(keyword, searchWord);
            }
        }
        currentKeywords = newKeywords;
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