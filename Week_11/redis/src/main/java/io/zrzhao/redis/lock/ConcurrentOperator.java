package io.zrzhao.redis.lock;

/**
 * @author zrzhao
 * @date 2021/4/5
 */
@FunctionalInterface
public interface ConcurrentOperator {

    /**
     * 是否继续执行
     *
     * @return
     */
    default boolean continueExecution(){
        return true;
    }

    /**
     * 具体操作
     */
    void doSomething();

}
