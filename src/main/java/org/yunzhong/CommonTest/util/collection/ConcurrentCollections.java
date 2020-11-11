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
     *  线程安全的 HashMap
     */
    private ConcurrentHashMap<String, String> concurrentHashMap;
    
    /**
     * 线程安全的 List。适用于多读少写的情形。
     */
    private CopyOnWriteArrayList<String> copyOnWriteArrayList;
    
    /**
     * 高效的并发队列，使用链表实现。非阻塞队列。
     */
    private ConcurrentLinkedQueue<String> concurrentLinkedQueue;
    
    private ConcurrentSkipListMap<String, String> concurrentSkipListMap;
    
    /**
     * 数组实现的，固定容量的阻塞队列。
     */
    private ArrayBlockingQueue<String> arrayBlockingQueue;
    
    private LinkedBlockingQueue<String> linkedBlockingQueue;
    
    /**
     * 支持优先级的无界阻塞队列。
     */
    private PriorityBlockingQueue<String> priorityBlockingQueue;
}
