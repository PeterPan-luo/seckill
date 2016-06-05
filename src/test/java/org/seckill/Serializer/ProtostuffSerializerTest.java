package org.seckill.Serializer;

import io.protostuff.runtime.RuntimeSchema;
import org.junit.Test;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.Objects;

import static org.junit.Assert.*;

/**
 * Created by Luohong on 2016/6/4.
 */
public class ProtostuffSerializerTest {

    @Test
    public void testSerialize() throws Exception {
        Date nowDate = new Date();
        Seckill seckill = new Seckill(1111, "test", 1111, nowDate, nowDate, nowDate);
        ProtostuffSerializer<Seckill> protostuffSerializer = new ProtostuffSerializer<Seckill>(Seckill.class);
       // SeckillSerializer protostuffSerializer = new SeckillSerializer();
        byte[] bytes = protostuffSerializer.serialize(seckill);
        System.out.println(bytes);
        Seckill deSeckill = protostuffSerializer.deserialize(bytes);
        System.out.println(deSeckill);
    }

    @Test
    public void testSchema(){
        RuntimeSchema<Object> schema = RuntimeSchema.createFrom(Object.class);
        assertNotNull(schema);
    }
    @Test
    public void testDeserialize() throws Exception {

    }
}