package org.yunzhong.CommonTest.util.collection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
public class MapTraverse {
    private HashMap<String, String> hashMap = new HashMap<>();

    @BeforeEach
    public void beforeEach() {
        hashMap.clear();
        for (int i = 0; i < 10; i++) {
            hashMap.put("key" + i, "value" + i);
        }
    }

    @Test
    public void testIteratorKey() {
        Iterator<String> iterator = hashMap.keySet().iterator();
        for (; iterator.hasNext();) {
            String value = hashMap.get(iterator.next());
            System.out.println(value);
        }
    }

    @Test
    public void testIteratorEntry() {
        Iterator<Entry<String, String>> iterator = hashMap.entrySet().iterator();
        for (; iterator.hasNext();) {
            Entry<String, String> next = iterator.next();
            String key = next.getKey();
            String value = next.getValue();
            System.out.println("key:" + key + " value:" + value);
        }
    }

    @Test
    public void testForEach() {
        Set<Entry<String, String>> entrySet = hashMap.entrySet();
        for (Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("key:" + key + " value:" + value);
        }
    }

    @Test
    public void testForEachKey() {
        Set<String> keySet = hashMap.keySet();
        for (String key : keySet) {
            String value = hashMap.get(key);
            System.out.println("key:" + key + " value:" + value);
        }
    }

    @Test
    public void testFunction() {
        hashMap.forEach((key, value) -> {
            System.out.println("key:" + key + " value:" + value);
        });
    }

    @Test
    public void testStream() {
        hashMap.entrySet().stream().forEach(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("key:" + key + " value:" + value);
        });
    }

    @Test
    public void testStreamParall() {
        hashMap.entrySet().parallelStream().forEach(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
            System.out.println("key:" + key + " value:" + value);
        });
    }

    @Test
    public void testWhile() {
        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext()) {
            String value = hashMap.get(iterator.next());
            System.out.println(value);
        }
    }
}
