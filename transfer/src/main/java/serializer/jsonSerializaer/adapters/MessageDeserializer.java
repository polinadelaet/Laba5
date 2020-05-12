package serializer.jsonSerializaer.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import message.EntityDTO;
import message.EntityType;
import message.Message;
import query.QueryDTO;
import response.ResponseDTO;

import java.lang.reflect.Type;

public final class MessageDeserializer implements JsonDeserializer<Message> {

    @Override
    public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonElement jsonEntityType = json.getAsJsonObject().getAsJsonPrimitive("entityType");
        EntityType entityType = context.deserialize(jsonEntityType, EntityType.class);

        JsonElement jsonEntityDTO = json.getAsJsonObject().getAsJsonObject("entityDTO");
        EntityDTO entityDTO = null;
        if (entityType.equals(EntityType.COMMAND_QUERY)) {
            entityDTO = context.deserialize(jsonEntityDTO, QueryDTO.class);
        }
        if (entityType.equals(EntityType.RESPONSE)) {
            entityDTO = context.deserialize(jsonEntityDTO, ResponseDTO.class);
        }

        return new Message(entityType, entityDTO);
    }
}
