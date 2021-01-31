本周作业：（**必做**）思考有多少种方式，在main函数启动一个新线程或线程池，
`异步运行一个方法，拿到这个方法的返回值后，退出主线程.`
写出你的方法，越多越好，提交到github。


`不知道这些有没有算是重复的，麻烦助教老师告我一下算是几种 =。=`
* 1.判断是否拿到了结果
* 2.CountDownLatch
* 3.CyclicBarrier
* 4.Semaphore
* 5.使用一个中间文件
* 6.Thread.activeCount
* 7.LockSupport.park
* 8.Condition
* 9.FutureTask
* 10.CompletableFuture
* 11.SynchronousQueue
* 12.AtomicReference
* 13.wait notify
* 14.join
* ~~15.synchronized 和 sleep~~