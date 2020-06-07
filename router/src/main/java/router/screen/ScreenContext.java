package router.screen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import router.Router;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * see {@link router.Router Router} for details.
 */
public final class ScreenContext {
    private final Router router;
    private final Map<String, Object> sharedData;
    @Nullable
    private final String pathToFile;


    public ScreenContext(Router router,
                         Map<String, Object> sharedData,
                         @Nullable String pathToFile) {
        this.router = router;
        this.sharedData = sharedData;
        this.pathToFile = pathToFile;
    }


    /**
     * Use it if you want to access Router.
     * @return Router impl.
     */
    public Router getRouter() {
        return router;
    }

    /**
     * Use it if you want to add some shared data.
     * see {@link router.Router Router} for details.
     * @param key the key for this data.
     * @param data certain data.
     * @param <T> type of data.
     */
    public <T> void add(String key, T data) {
        sharedData.put(key, data);
    }

    /**
     * Use it if you want to access shared data associated with given key.
     * see {@link router.Router Router} for details.
     * <br></br>
     * <br></br>
     * NOTE: You have to offer right wildcard T or handle ClassCastException.
     * @param key the key for this data.
     * @param <T> type of data.
     * @return shared data.
     * @throws ClassCastException if <T> is wrong.
     */
    public <T> T get(String key) {
        return (T) sharedData.get(key);
    }

    /**
     * Use it to clear shared data map.
     * @param key the key for this data.
     */
    public void remove(String key) {
        sharedData.remove(key);
    }

    /**
     * Use it to clear shared data map.
     * @param keys the key set for this data.
     */
    public void removeAll(Set<String> keys) {
        keys.forEach(sharedData::remove);
    }

    /**
     * Use it to save sharedData into file specified while Router's building.
     * <br></br>
     * <br></br>
     * NOTE: All values will be saved as strings.
     * @throws IOException if I\O exception occurs.
     * @throws NullPointerException if you try to save ScreenContext when while Router's building
     * you have not used
     * {@link router.RouterBuilder#specifyFileForSavingSharedData(String) specifyFileForSavingSharedData()}
     */
    public void save() throws IOException {
        if (pathToFile == null) {
            throw new NullPointerException();
        }

        Gson gson = new GsonBuilder()
                            .create();
        Map<String, String> target = new HashMap<>();
        sharedData.forEach((key, value) -> target.put(key, value.toString()));

        String json = gson.toJson(target, new TypeToken<Map<String, String>>(){}.getType());

        StringFileDAO.save(json, pathToFile, StandardCharsets.UTF_8);
    }
}
