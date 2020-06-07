package controller.components.serviceMediator.sharedData;

import controller.components.serviceMediator.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Use this default service if you want to share some data with other commands.
 * <br></br>
 * <br></br>
 * NOTE: This service will be added automatically by ControllerBuilder if you use
 * {@link controller.ControllerBuilder#buildServiceMediator(Set) ControllerBuilder.buildServiceMediator()}.
 * So do not create this service by yourself.
 */
public final class SharedDataService implements Service {
    private final Map<String, Object> sharedData;


    public SharedDataService() {
        this.sharedData = new HashMap<>();
    }

    /**
     * Use it if you want to add some shared data.
     * @param key the key for this data.
     * @param data certain data.
     * @param <T> type of data.
     */
    public <T> void add(String key, T data) {
        sharedData.put(key, data);
    }

    /**
     * Use it if you want to access shared data associated with given key.
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
}
