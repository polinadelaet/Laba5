package message;

import message.exception.WrongTypeException;
import query.Query;
import query.QueryDTO;
import response.Response;
import response.ResponseDTO;

import java.io.Serializable;

/**
 * This class stores entityDTO and information about its Type.
 */
public final class Message implements Serializable {
    private static final long serialVersionUID = 12345L;

    private EntityType entityType;
    private EntityDTO entityDTO;


    public Message() {}

    public Message(EntityType entityType, EntityDTO entityDTO) {
        this.entityType = entityType;
        this.entityDTO = entityDTO;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Response getResponse() throws WrongTypeException {
        if (entityType.equals(EntityType.RESPONSE)) {
            return Response.of((ResponseDTO) entityDTO);
        }

        throw new WrongTypeException();
    }

    public Query getCommandQuery() throws WrongTypeException {
        if (entityType.equals(EntityType.COMMAND_QUERY)) {
            return Query.of((QueryDTO) entityDTO);
        }

        throw new WrongTypeException();
    }
}
