package learn.design.pattern.creational.prototype;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 对于需求 3，{@link HashTableIndex}的实现方式则不能满足要求，因为直接操作的是原有的散列表，就会出现多个版本数据的情况
 * 解决版本：每次更新都创建新的散列表，这样既保证了数据一直可用，又避免了中间状态的存在
 */
public class HashTableIndexInSameVersion1 {

    /**
     * 散列表索引
     */
    private Map<String, SearchWord> currentKeywords = new HashMap<>();

    /**
     * 刷新散列表索引方法
     */
    public void refresh() {
        HashMap<String, SearchWord> newKeywords = new LinkedHashMap<>();
        List<SearchWord> allSearchWords = listAllSearchWords();
        for (SearchWord searchWord : allSearchWords) {
            String keyword = searchWord.getKeyword();
            newKeywords.put(keyword, searchWord);
        }
        currentKeywords = newKeywords;
    }

    /**
     * 获取全部搜索关键字实体对象
     *
     * @return 搜索关键字实体对象列表，搜索不到返回空列表
     */
    private List<SearchWord> listAllSearchWords() {
        // TODO: 从数据库中取出所有的数据 return null;
        return Collections.emptyList();
    }
}