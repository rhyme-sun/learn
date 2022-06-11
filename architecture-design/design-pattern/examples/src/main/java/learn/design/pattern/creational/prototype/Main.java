package learn.design.pattern.creational.prototype;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * for test.
 */
public class Main {

    private static final Logger log = Logger.getGlobal();

    public static void main(String[] args) {
        SearchWord searchWord = new SearchWord("hello-world", 100, 1);
        SearchWord searchWord1 = new SearchWord("spring", 100, 1);
        HashMap<String, SearchWord> map = new HashMap<>();
        map.put(searchWord.getKeyword(), searchWord);
        map.put(searchWord1.getKeyword(), searchWord1);

        SearchWord searchWord2 = new SearchWord("hello-world", 101, 1);
        HashMap<String, SearchWord> cloneMap = (HashMap<String, SearchWord>) map.clone();

        cloneMap.replace(searchWord.getKeyword(), searchWord2);
        log.info("old map hello-world: " + map.get("hello-world").toString());
        log.info("new map hello-world: " + cloneMap.get("hello-world").toString());

        // replace方法修改了value应用地址        
        cloneMap.get("hello-world").setTimes(102);
        log.info("old map hello-world: " + map.get("hello-world").toString());
        log.info("new map hello-world: " + cloneMap.get("hello-world").toString());

        // clone方法为浅拷贝
        cloneMap.get("spring").setTimes(99);
        log.info("old map spring: " + map.get("spring").toString());
        log.info("new map spring: " + cloneMap.get("spring").toString());
    }
}
