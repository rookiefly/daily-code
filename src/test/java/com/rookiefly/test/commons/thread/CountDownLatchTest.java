package com.rookiefly.test.commons.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

/**
 * @author rookiefly
 * 线程的测试
 */
@Slf4j
public class CountDownLatchTest {

    /**
     * 5个线程的线程池
     */
    private static ExecutorService scheduler = Executors.newFixedThreadPool(5);

    public static CountDownLatch countDownLatch;

    public static void main(String[] args) {

        countDownLatch = new CountDownLatch(4);

        //执行线程a任务
        scheduler.execute(() -> {
            try {
                CountDownLatchTest.countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("thread a done");
        });

        scheduler.execute(new JobThread("thread b")); //执行线程b任务
        scheduler.execute(new JobThread("thread c")); //执行线程c任务
        scheduler.execute(new JobThread("thread d")); //执行线程d任务
        scheduler.execute(new JobThread("thread e")); //执行线程e任务

        //任务执行完关闭线程池
        scheduler.shutdown();
    }

    public static class JobThread implements Runnable {
        /**
         * 线程名称
         */
        private String threadName;

        public JobThread(String threadName) {
            this.threadName = threadName;
        }

        public void run() {
            log.info(threadName + " start");
            //do something
            try {
                sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info(threadName + " done");
            CountDownLatchTest.countDownLatch.countDown();
        }
    }
}
