package learn.design.pattern.creational.prototype;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * 对{@link HashTableIndexInSameVersion3}的实现方式进一步优化：拷贝对象时对需要修改的对象使用深拷贝，不需要变更的对象使用浅拷贝
 */
public class HashTableIndexInSameVersion4 {

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
            // some searchWork that you want to modify
            boolean needMod = newKeywords.containsKey(keyword);
            if (needMod) {
                newKeywords.remove(keyword);
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