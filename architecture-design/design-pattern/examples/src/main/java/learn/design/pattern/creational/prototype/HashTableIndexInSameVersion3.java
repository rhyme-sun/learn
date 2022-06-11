package learn.design.pattern.creational.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * {@link HashTableIndexInSameVersion2}的实现方式使用 {@link Object#clone()}方法来拷贝原有散列表，由于 Object 的克隆方法为浅拷贝，
 * 即在拷贝散列表时，只会复制图中的索引（散列表），不会复制数据（SearchWord 对象）本身，这样旧的散列表和新的散列表 value 都指向同一个对象，
 * 当我们修改了其中一个散列表中的某个 SearchWord 的属性值，另一个散列表的 SearchWord 的值也会改变。
 * 解决办法：使用深拷贝。
 */
public class HashTableIndexInSameVersion3 {

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
        HashMap<String, SearchWord> newKeywords = new HashMap<>();
        // 深拷贝
        currentKeywords.forEach((k, v) -> {
            newKeywords.put(k, new SearchWord(v.getKeyword(), v.getTimes(), v.getLastUpdateTime()));
        });
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
     * 利用序列化和反序列化拷贝对象
     *
     * @param object 待拷贝的对象
     * @return 拷贝后的对象
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object deepCopy(Object object) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(object);
        ByteArrayInputStream bi = new ByteArrayInputStream(bo.toByteArray());
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
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