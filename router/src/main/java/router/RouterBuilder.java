package router;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import router.screen.Screen;
import router.screen.StringFileDAO;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * see {@link Router} for details.
 */
public final class RouterBuilder {
    private final Map<String, Screen> screenMap;
    private final String initialRout;
    private final Map<String, Object> sharedData;
    private String pathForSavingSharedData;


    /**
     * @param initialRout define initial rout for future Router.
     */
    public RouterBuilder(String initialRout) {
        this.screenMap = new HashMap<>();
        this.initialRout = initialRout;
        this.sharedData = new HashMap<>();
    }


    /**
     * Register a screen for this rout in future Router.
     */
    public RouterBuilder registerScreen(@Nonnull String rout,
                                        @Nonnull Screen screen) {
        screenMap.put(rout, screen);
        return this;
    }

    /**
     * Add shared data for future Router.
     */
    public RouterBuilder addSharedData(@Nonnull String key,
                                       @Nonnull Object data) {
        sharedData.put(key, data);
        return this;
    }

    /**
     * Specify path for file for saving or restoring shared data.
     */
    public RouterBuilder specifyFileForSavingSharedData(@Nonnull String absolutePath) {
        pathForSavingSharedData = absolutePath;
        return this;
    }

    /**
     * Reads shared data as KEY: STRING, VALUE: STRING and added this pairs to the shared data.
     * <br></br>
     * <br></br>
     * NOTE: Remember that after restoring all values in the shared data will be String! Be attentive
     * when you use {@link router.screen.ScreenContext#get(String) ScreenContext.get()}
     * @throws IOException if any I\O exception occurs.
     * @throws NullPointerException if you have not called
     * {@link RouterBuilder#specifyFileForSavingSharedData(String) specifyFileForSavingSharedData()} before
     * invocation of this method.
     */
    public RouterBuilder restoreSharedData() throws IOException {
        if (pathForSavingSharedData == null) {
            throw new NullPointerException();
        }

        Gson gson = new GsonBuilder()
                            .create();

        String json = StringFileDAO.read(pathForSavingSharedData, StandardCharsets.UTF_8);
        Map<String, String> source = gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());

        source.forEach(sharedData::put);
        return this;
    }

    /**
     * Get a Router.
     */
    public Router build() {
        return new Router(screenMap,
                          initialRout,
                          sharedData,
                          pathForSavingSharedData);
    }
}
