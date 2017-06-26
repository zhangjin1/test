package com.weimob.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程计算1+2+3+4+5+...+10
 * 
 * @author zhangjin
 *
 */
public class Calculate {

    private static AtomicInteger step = new AtomicInteger();
    private static AtomicInteger account = new AtomicInteger();
    private static int threadCount = 5;
    private static final CountDownLatch countDownLatch = new CountDownLatch(threadCount);

    public static int calculate(final int end) throws InterruptedException {
        for (int i = 0; i < threadCount; i++) {
            new Thread(new Runnable() {
                public void run() {
                    int currentStep;
                    while ((currentStep = step.incrementAndGet()) <= end)
                        account.addAndGet(currentStep);
                    countDownLatch.countDown();
                }
            }).start();
        }
        countDownLatch.await();
        return account.get();
    }

    public static void main(String[] args) throws InterruptedException {
        int end = 1000;
        System.out.println(calculate(end));
        int amount = 0;
        for (int i = 1; i <= end; i++) {
            amount += i;
        }
        System.out.println(amount);
    }
}
