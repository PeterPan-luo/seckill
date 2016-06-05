package org.seckill.Serializer;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.seckill.Utils.ReflectionUtils;

import java.lang.reflect.Type;

/**
 * Created by Luohong on 2016/6/4.
 */
public class ProtostuffSerializer<T> implements Serializer<T> {

    private  RuntimeSchema<T> schema = null;

    public  ProtostuffSerializer(Class clazz){
       // Class<T> clazz =ReflectionUtils.getSuperGenericType(getClass());
        schema = RuntimeSchema.createFrom(clazz);
    }

    @Override
    public byte[] serialize(T o) throws Exception {
        byte[] bytes = ProtobufIOUtil.toByteArray(o, schema,
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        return bytes;
    }

    @Override
    public T deserialize(byte[] bytes) throws Exception {
        T clazz = schema.newMessage();
        ProtobufIOUtil.mergeFrom(bytes, clazz, schema);
        return clazz;
    }
}
