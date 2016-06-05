package org.seckill.dao;

import org.seckill.Serializer.ProtostuffSerializer;
import org.seckill.cache.redis.JedisCallback;
import org.seckill.cache.redis.JedisExecutor;
import org.seckill.cache.redis.PooledJedisExecutor;
import org.seckill.entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Luohong on 2016/6/4.
 */
public class RedisDao {

    private final PooledJedisExecutor jedisExecutor;

    private final static String KEY_PREFIX = "seckill:";

    ProtostuffSerializer<Seckill> proSer = new ProtostuffSerializer<Seckill>(Seckill.class);

    public RedisDao(String ip, int port) {
        JedisPool jedisPool = new JedisPool(ip, port);
        this.jedisExecutor = new PooledJedisExecutor(jedisPool);
    }

    public Seckill getSeckill(final long seckillId){
        return jedisExecutor.execute(new JedisCallback<Seckill>() {
            @Override
            public Seckill execute(Jedis jedis) throws Exception {
                String key = KEY_PREFIX + seckillId;
                byte[] bytes = jedis.get(key.getBytes());
                if (bytes != null){
                    return proSer.deserialize(bytes);
                }
                return null;
            }
        });
    }

    public String putSeckill(final Seckill seckill){
        return jedisExecutor.execute(new JedisCallback<String>() {
            @Override
            public String execute(Jedis jedis) throws Exception {
                String key =  KEY_PREFIX + seckill.getSeckillId();
                byte[] data = proSer.serialize(seckill);
                int timeout = 60*60; //1小时
                String re = jedis.setex(key.getBytes(), timeout, data);
                return  re;
            }
        });
    }

}
