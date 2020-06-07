package middleware;

import manager.LogManager;
import query.Query;
import response.Response;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public abstract class Middleware {
    private static final LogManager LOG_MANAGER = LogManager.createDefault(Middleware.class);

    @Nonnull
    protected final Map<String, Middleware> leaves;


    public Middleware() {
        this.leaves = new HashMap<>();
    }


    public void addLeave(String leaveKey, Middleware leave) {
        leaves.put(leaveKey, leave);
    }


    /**
     * @throws NoLeaveException if no leave with such leaveKey has been found.
     */
    protected Response callLeave(@Nonnull Query query,
                                 @Nonnull String leaveKey) throws MiddlewareException {
        if (!leaves.containsKey(leaveKey) || leaves.get(leaveKey) == null) {
            throw new NoLeaveException(leaveKey);
        }

        return leaves.get(leaveKey).handle(query);
    }

    /**
     * @throws NoLeaveException if no leave with such leaveKey has been found.
     */
    protected Response callLeave(@Nonnull Query query,
                                 @Nonnull String leaveKey,
                                 @Nonnull String targetLeaveForCallableMiddleware) throws MiddlewareException{
        if (!leaves.containsKey(leaveKey) || leaves.get(leaveKey) == null) {
            throw new NoLeaveException(leaveKey);
        }

        return leaves.get(leaveKey).handle(query, targetLeaveForCallableMiddleware);
    }

    public abstract Response handle(@Nonnull Query query) throws MiddlewareException;

    /**
     * Use it when this middleware cannot resolve which leave it has to choose next.
     */
    public Response handle(@Nonnull Query query, @Nonnull String targetLeaveKey) throws MiddlewareException {
        LOG_MANAGER.errorThrowable("Is not applicable. Middleware: " + this.getClass().getSimpleName());
        return Response.createInternalError();
    }
}
