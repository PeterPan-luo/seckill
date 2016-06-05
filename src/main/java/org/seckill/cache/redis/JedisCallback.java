package org.seckill.cache.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by Luohong on 2016/6/4.
 */
public interface JedisCallback<V> {

    public V execute(Jedis jedis) throws Exception;
}
