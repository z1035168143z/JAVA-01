package io.zrzhao.redis.datastorage;

/**
 * @author zrzhao
 * @date 2021/4/5
 */
public interface ConcurrentCounterOperator {

    /**
     * 获取数值
     *
     * @return
     */
    long getNum();

    /**
     * 设置数值
     *
     * @return
     */
    boolean setNum(long num);

}
