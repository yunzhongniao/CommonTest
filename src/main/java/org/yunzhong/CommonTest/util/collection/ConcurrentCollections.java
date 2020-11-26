package org.yunzhong.CommonTest.util.collection;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author yunzhong
 *
 */
public class ConcurrentCollections {

    /**
     * 线程安全的 HashMap
     */
    final static private ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();

    /**
     * 线程安全的 List。适用于多读少写的情形。
     */
    final static private CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<String>();

    /**
     * 高效的并发队列，使用链表实现。非阻塞队列。
     */
    final static private ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<String>();

    final static private ConcurrentSkipListMap<String, String> concurrentSkipListMap = new ConcurrentSkipListMap<String, String>();

    /**
     * 数组实现的，固定容量的阻塞队列。
     */
    final static private ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<String>(100);

    /**
     * LinkedBlockingQueue默认容量Integer.MAX_VALUE,可能发生内存耗尽。指定max
     */
    final static private LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>(100);

    /**
     * 支持优先级的无界阻塞队列。
     */
    final static private PriorityBlockingQueue<String> priorityBlockingQueue = new PriorityBlockingQueue<String>();

    public static ConcurrentHashMap<String, String> getConcurrentHashMap() {
        return concurrentHashMap;
    }

    public static CopyOnWriteArrayList<String> getCopyOnWriteArrayList() {
        return copyOnWriteArrayList;
    }

    public static ConcurrentLinkedQueue<String> getConcurrentLinkedQueue() {
        return concurrentLinkedQueue;
    }

    public static ConcurrentSkipListMap<String, String> getConcurrentSkipListMap() {
        return concurrentSkipListMap;
    }

    public static ArrayBlockingQueue<String> getArrayBlockingQueue() {
        return arrayBlockingQueue;
    }

    public static LinkedBlockingQueue<String> getLinkedBlockingQueue() {
        return linkedBlockingQueue;
    }

    public static PriorityBlockingQueue<String> getPriorityBlockingQueue() {
        return priorityBlockingQueue;
    }

}
