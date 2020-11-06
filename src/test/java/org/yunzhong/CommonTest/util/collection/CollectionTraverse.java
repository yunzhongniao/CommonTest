package org.yunzhong.CommonTest.util.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author yunzhong
 *
 */
public class CollectionTraverse {
    private List<String> list = new ArrayList<>();

    @BeforeEach
    public void beforeEach() {
        list.clear();
        for (int i = 0; i < 10; i++) {
            list.add("value" + i);
        }
    }

    @Test
    public void testIterator() {
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
            System.out.println(iterator.next());
        }
    }

    @Test
    public void testForEach() {
        for (String value : list) {
            System.out.println(" value:" + value);
        }
    }

    @Test
    public void testFor() {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(" value:" + list.get(i));
        }
    }

    @Test
    public void testStream() {
        list.stream().forEach(value -> {
            System.out.println(" value:" + value);
        });
    }

    @Test
    public void testStreamParall() {
        list.parallelStream().forEach(value -> {
            System.out.println(" value:" + value);
        });
    }
}
