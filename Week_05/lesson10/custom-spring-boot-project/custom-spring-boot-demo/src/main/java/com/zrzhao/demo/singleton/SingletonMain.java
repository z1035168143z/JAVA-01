package com.zrzhao.demo.singleton;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * @author zrzhao
 * @date 2021/2/9
 */
public class SingletonMain {

    static final int threadNum = 10;

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum + 1);
        System.out.println("hungry begin");
        doTest(cyclicBarrier, o -> HungrySingleton.getSingleton().doSomeThing(), executorService);
        cyclicBarrier.await();
        System.out.println("hungry end");

        cyclicBarrier.reset();
        System.out.println("lazy begin");
        doTest(cyclicBarrier, o -> LazyInitSingleton.getSingleton().doSomeThing(), executorService);
        cyclicBarrier.await();
        System.out.println("lazy end");

        cyclicBarrier.reset();
        System.out.println("inner static begin");
        doTest(cyclicBarrier, o -> InnerStaticClassSingleton.getSingleton().doSomeThing(), executorService);
        cyclicBarrier.await();
        System.out.println("inner static end");

        cyclicBarrier.reset();
        System.out.println("enum begin");
        doTest(cyclicBarrier, o -> EnumSingleton.INSTANCE.doSomeThing(), executorService);
        cyclicBarrier.await();
        System.out.println("enum end");
    }

    private static <T> void doTest(CyclicBarrier cyclicBarrier, Consumer<T> consumer, ExecutorService executorService) {
        for (int i = 0; i < threadNum; i++) {
            executorService.execute(() -> {
                consumer.accept(null);
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
