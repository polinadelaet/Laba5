package serializer;

import serializer.exception.DeserializationException;
import serializer.exception.SerializationException;

import java.io.*;

public final class JavaSerializer implements ISerializer {
    @Override
    public byte[] toByteArray(Object object) throws SerializationException {
        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream)) {
            objectOutputStream.writeObject(object);
        } catch (IOException e) {
            throw new SerializationException(e);
        }

        return byteOutputStream.toByteArray();
    }

    @Override
    public <T> T fromByteArray(byte[] bytes, Class<T> clazz) throws DeserializationException {
        ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
        try(ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream)) {
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new DeserializationException(e);
        }
    }
}
