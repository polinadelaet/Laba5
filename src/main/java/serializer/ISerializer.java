package serializer;

import serializer.exception.DeserializationException;
import serializer.exception.SerializationException;

public interface ISerializer {
    byte[] toByteArray(Object object) throws SerializationException;

    <T> T fromByteArray(byte[] bytes, Class<T> clazz) throws DeserializationException;
}
