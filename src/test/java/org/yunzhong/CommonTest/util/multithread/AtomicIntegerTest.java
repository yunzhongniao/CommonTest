package org.yunzhong.CommonTest.util.multithread;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

public class AtomicIntegerTest {
    private AtomicInteger atomicInteger = new AtomicInteger();

    @Test
    public void test() {
        for (int j = 0; j < 10; j++) {

            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                int number = atomicInteger.getAndUpdate(p -> (p + 1) % Integer.MAX_VALUE);
                if (number % 100_000_000 == 0) {
                    System.out.println("number is :" + number);
                }
                if(number == 0) {
                    System.out.println("number is 000000000.");
                }
            }
        }

    }
}
