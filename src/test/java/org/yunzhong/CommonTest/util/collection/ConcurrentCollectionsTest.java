package org.yunzhong.CommonTest.util.collection;

import java.util.concurrent.ArrayBlockingQueue;

import org.junit.jupiter.api.Test;

class ConcurrentCollectionsTest {

    /**
     * 
     */
    @Test
    void testarrayBlockingQueue() {
        ArrayBlockingQueue<String> arrayBlockingQueue = ConcurrentCollections.getArrayBlockingQueue();
    }

}
