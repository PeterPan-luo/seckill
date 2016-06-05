package org.seckill.cache.redis;

/**
 * Created by Luohong on 2016/6/4.
 */
public interface JedisExecutor {

    public <V> V execute(JedisCallback<V> jedis);
}
