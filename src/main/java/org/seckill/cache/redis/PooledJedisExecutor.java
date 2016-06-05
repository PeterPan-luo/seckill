package org.seckill.cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

/**
 * Created by Luohong on 2016/6/4.
 */
public class PooledJedisExecutor implements JedisExecutor {

    private final JedisPool jedisPool;

    public PooledJedisExecutor(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public <V> V execute(JedisCallback<V> cb) {
        Jedis jedis = jedisPool.getResource();
        try{
            return cb.execute(jedis);
        }catch (JedisException e){
            jedisPool.returnResource(jedis);
            throw  e;
        }finally {
            jedisPool.returnResource(jedis);
        }
    }
}
