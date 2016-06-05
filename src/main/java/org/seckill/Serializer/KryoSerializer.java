package org.seckill.Serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

/**
 * Created by Luohong on 2016/6/4.
 */
public class KryoSerializer implements Serializer<Object> {

    private final static Kryo kryo = new Kryo();
    static {
        kryo.setReferences(false);
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
    }

    @Override
    public byte[] serialize(Object obj) throws Exception {
        Output output = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            output = new Output(baos);
            kryo.writeClassAndObject(output, obj);
            output.flush();
            return baos.toByteArray();
        }finally{
            if(output != null)
                output.close();
        }
    }

    @Override
    public Object deserialize(byte[] bits) throws Exception {
        if(bits == null || bits.length == 0)
            return null;
        Input input = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(bits);
            input = new Input(bais);
            return kryo.readClassAndObject(input);
        } finally {
            if(input != null)
                input.close();
        }
    }
}
