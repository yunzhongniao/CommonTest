package org.yunzhong.CommonTest.util.multithread;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.yunzhong.CommonTest.util.multithread.ExecutorManagerDemo.ThreadPoolInfoDemo;

class ExecutorManagerTest {
    
    @BeforeEach
    public void beforeEach() {
        
    }

    @Test
    void testPrintInfo() {
        for (int i = 0; i < 3; i++) {
            Callable<String> callable = new Callable<String>() {

                @Override
                public String call() throws Exception {
                    while (true) {
                        Thread.sleep(100L);
                    }
                }
            };
            ExecutorManagerDemo.execute(callable);
        }
        ExecutorManagerDemo.printInfo();
    }

    @Test
    public void testGetInfo() {
        for (int i = 0; i < 2; i++) {
            Callable<String> callable = new Callable<String>() {

                @Override
                public String call() throws Exception {
                    while (true) {
                        Thread.sleep(100L);
                    }
                }
            };
            ExecutorManagerDemo.execute(callable);
        }
        ThreadPoolInfoDemo info = ExecutorManagerDemo.getInfo();
        assertEquals(2, info.getActiveCount());
        assertEquals(2, info.getCorePoolSize());
        assertEquals(5, info.getMaximumPoolSize());
    }

    @Test
    public void testGetInfoActive() {
        for (int i = 0; i < 5; i++) {
            Callable<String> callable = new Callable<String>() {

                @Override
                public String call() throws Exception {
                    while (true) {
                        Thread.sleep(100L);
                    }
                }
            };
            ExecutorManagerDemo.execute(callable);
        }
        ThreadPoolInfoDemo info = ExecutorManagerDemo.getInfo();
        assertEquals(2, info.getActiveCount());
        assertEquals(2, info.getCorePoolSize());
        assertEquals(5, info.getMaximumPoolSize());
        assertEquals(3, info.getWaitingSize());
    }

    @Test
    public void testGetInfoQueue() {
        for (int i = 0; i < 8; i++) {
            Callable<String> callable = new Callable<String>() {

                @Override
                public String call() throws Exception {
                    while (true) {
                        Thread.sleep(100L);
                    }
                }
            };
            ExecutorManagerDemo.execute(callable);
        }
        ThreadPoolInfoDemo info = ExecutorManagerDemo.getInfo();
        assertEquals(3, info.getActiveCount());
        assertEquals(2, info.getCorePoolSize());
        assertEquals(5, info.getMaximumPoolSize());
        assertEquals(5, info.getWaitingSize());
    }

    @Test
    public void testGetInfoQueueMax() {
        for (int i = 0; i < 10; i++) {
            Callable<String> callable = new Callable<String>() {

                @Override
                public String call() throws Exception {
                    while (true) {
                        Thread.sleep(100L);
                    }
                }
            };
            ExecutorManagerDemo.execute(callable);
        }
        ThreadPoolInfoDemo info = ExecutorManagerDemo.getInfo();
        assertEquals(5, info.getActiveCount());
        assertEquals(2, info.getCorePoolSize());
        assertEquals(5, info.getMaximumPoolSize());
        assertEquals(5, info.getWaitingSize());
    }

    @Test
    public void testGetInfoQueueException() {
        assertThrows(RejectedExecutionException.class, () -> {

            for (int i = 0; i < 11; i++) {
                Callable<String> callable = new Callable<String>() {

                    @Override
                    public String call() throws Exception {
                        while (true) {
                            Thread.sleep(100L);
                        }
                    }
                };
                ExecutorManagerDemo.execute(callable);
            }
        });
    }
}
