package serializer.jsonSerializaer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import message.Message;
import serializer.ISerializer;
import serializer.exception.DeserializationException;
import serializer.exception.SerializationException;
import serializer.jsonSerializaer.adapters.MessageDeserializer;

import java.nio.charset.StandardCharsets;

/**
 * Serializer objects by this chain:
 * object -> json -> bytes array.
 */
public final class JSONSerializer implements ISerializer {
    private final Gson gson = new GsonBuilder()
                                            .registerTypeAdapter(Message.class, new MessageDeserializer())
                                            .serializeNulls()
                                            .create();

    @Override
    public byte[] toByteArray(Object object) throws SerializationException {
        try {
            return gson.toJson(object).getBytes(StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new SerializationException(e);
        }
    }

    @Override
    public <T> T fromByteArray(byte[] bytes, Class<T> clazz) throws DeserializationException {
        try {
            return gson.fromJson(new String(bytes, StandardCharsets.UTF_8).trim(), clazz);
        } catch (JsonSyntaxException e) {
            throw new DeserializationException(e);
        }
    }
}
