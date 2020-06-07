package router;

import router.exception.NoScreenForRoutException;
import router.exception.NoSuchRoutException;
import router.exception.ScreenIsNotRegisteredException;
import router.screen.Screen;
import router.screen.ScreenContext;
import router.screen.ScreenMemento;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;


/**
 * Here you can find Router framework summary.
 *
 * <br></br>
 * <br></br>
 *
 * Router class encapsulates routing logic between screen.
 * <br></br>
 * Use {@link Router#start() Router.start()} to open an initialScreen.
 * <br></br>
 * Use {@link Router#go(String) Router.go()} to go to another screen.
 * <br></br>
 * Use {@link Router#goAndSaveState(String, Screen) Router.goAndSave()} to go to
 * another screen and save the state of currentScreen.
 * <br></br>
 * Use {@link Router#goBack() Router.goBack()} to go back throw routing history.
 * Initial screen is always in the end of history. So you can be sure this method is safe.
 * <br></br>
 * To access Router in your Screen implementation use
 * {@link ScreenContext#getRouter() screenContext.getRouter()}.
 *
 * <br></br>
 * <br></br>
 *
 * {@link Screen Screen} interface defines a container which can be toggle active
 * via {@link Screen#onStart(ScreenContext) Screen.onStart()} or {@link Screen#onActive(ScreenMemento, ScreenContext) Screen.onActive()}
 * inside the Router.
 * PreviousState in the last method defines a state which you can save via
 * {@link Router#goAndSaveState(String, Screen) Router.goAndSave()}. If you want to use
 * this functionality you need to override {@link Screen#getState() Screen.getState()}.
 * <br></br>
 * <br></br>
 * NOTE: All {@link Screen Screen} implementations must override {@link Object#equals(Object) equals method}
 *
 * <br></br>
 * <br></br>
 *
 * {@link ScreenMemento ScreenMemento} interface defines a marker interface. It's goal is to determine some class
 * that stores a state of certain Screen. Implement this interface for all Screen which you want to be able to restore
 * via {@link Screen#onActive(ScreenMemento, ScreenContext) Screen.onActive()}. You have to cast previousState to your
 * certain state in the Screen.onActive() implementation. The Router framework guarantees that previousState object will
 * be the same as {@link Screen#getState() Screen.getState()} returns.
 *
 * <br></br>
 * <br></br>
 *
 * {@link ScreenContext ScreenContext} class defines a useful container. It contains link to the Router impl and shared data.
 * Via shared data you can send some objects between Screens.
 * Use
 * {@link ScreenContext#add(String, Object) add()},
 * {@link ScreenContext#get(String) get()},
 * {@link ScreenContext#remove(String) remove()},
 * {@link ScreenContext#removeAll(Set) removeAll()} for this purpose.
 * Use {@link ScreenContext#save() save()} if you want to save the shared data into file. Remember that you must
 * use {@link RouterBuilder#specifyFileForSavingSharedData(String) RouterBuilder.specifyFileForSavingData()} if
 * you want to use this functionality. Also NOTE that when you use
 * {@link RouterBuilder#restoreSharedData() RouterBuilder.restoreSharedData()} shared data will be restore in format
 * KEY: STRING, VALUE: STRING. Be attentive when you use {@link ScreenContext#get(String) ScreenContext.get()} method.
 * <br></br>
 * <br></br>
 * Do not use shared data functionality to exchange some services or other business-logic objects between Screen.
 * In this case use DI mechanism to inject services directly in the Screen object.
 *
 * <br></br>
 * <br></br>
 *
 * {@link RouterBuilder RouterBuilder} class for constructing Router. It is easier to see once than to read twice.
 * <blockquote><pre>
 *     Router router = new RouterBuilder("start")
 *                              .registerScreen("main", mainScreen)
 *                              .registerScreen("end", endScreen)
 *                              .addSharedData("userId", userId)
 *                              .specifyFileForSavingSharedData("D:\some_file.txt")
 *                              .restoreSharedData()
 *                              .build();
 * </pre></blockquote>
 * @author Mr.Kefir
 */
public final class Router {
    private final ScreenContext screenContext;

    private final Map<String, Screen> screenMap;
    private final Map<String, ScreenMemento> screenMementoMap;

    private final Deque<String> history;
    private String currentRout;

    private final String initialRout;


    /**
     * @param screenMap defines your routes map.
     * @param initialRout define rout that will be used like initial in {@link Router#start() Router.start()}
     */
    protected Router(@Nonnull Map<String, Screen> screenMap,
                     @Nonnull String initialRout,
                     @Nullable Map<String, Object> sharedData,
                     @Nullable String pathForSavingSharedData) {
        this.screenMap = screenMap;
        this.screenMementoMap = new HashMap<>();
        this.initialRout = initialRout;
        this.history = new ArrayDeque<>();
        this.currentRout = "";

        if (sharedData == null) {
            screenContext = new ScreenContext(this, new HashMap<>(), pathForSavingSharedData);
        } else {
            screenContext = new ScreenContext(this, sharedData, pathForSavingSharedData);
        }
    }


    /**
     * Opens screen associated with initialRout.
     *
     * @throws NoScreenForRoutException if no screen for initialRout has been found.
     */
    public void start() throws NoScreenForRoutException {
        Screen initScreen = screenMap.get(initialRout);

        if (initScreen == null) {
            throw new NoScreenForRoutException(initialRout);
        }

        history.add(initialRout);
        currentRout = initialRout;
        initScreen.onStart(screenContext);
    }

    /**
     * Use it when you want to go to another screen and save state of the currentScreen.
     * <br></br>
     * <br></br>
     * NOTE: currentScreen must be registered in the Router via {@link Router#Router(Map, String, Map, String) constructor}
     * and currentScreen must override {@link Object#equals(Object) equals method}.
     * @param targetScreenRout rout of a screen to which you want to go.
     * @param currentScreen currentScreen to be saved.
     * @throws NoSuchRoutException if no screen is registered for given targetScreenRout.
     * @throws ScreenIsNotRegisteredException if given currentScreen is not registered in the Router.
     */
    public void goAndSaveState(@Nonnull String targetScreenRout,
                               @Nonnull Screen currentScreen) throws NoSuchRoutException,
            ScreenIsNotRegisteredException {
        ScreenMemento targetScreenPreviousState = screenMementoMap.get(targetScreenRout);

        saveState(currentScreen);

        go(targetScreenRout, targetScreenPreviousState);
    }

    /**
     * Use it when you want to go to another screen.
     * <br></br>
     * <br></br>
     * NOTE: If you want to be able to save the current screen state then use
     * {@link Router#goAndSaveState(String, Screen) router.goAndSaveState()}
     * @param targetScreenRout rout of a screen to which you want to go.
     * @throws NoSuchRoutException if no screen is registered for given targetScreenRout.
     */
    public void go(@Nonnull String targetScreenRout) throws NoSuchRoutException {
        ScreenMemento previousState = screenMementoMap.get(targetScreenRout);

        go(targetScreenRout, previousState);
    }


    /**
     * Use it when you want to go back throw routing history,
     */
    public void goBack() {
        if (history.isEmpty()) {
            throw new RuntimeException("HISTORY CANNOT BE EMPTY!!!");
        }

        String targetRout = history.pop();

        Screen targetScreen = null;
        try {
            targetScreen = getTargetScreen(targetRout);
        } catch (NoSuchRoutException e) {
            //Never raises
        }

        ScreenMemento previousState = screenMementoMap.get(targetRout);

        currentRout = targetRout;

        if (previousState == null) {
            targetScreen.onStart(screenContext);
        } else {
            targetScreen.onActive(previousState, screenContext);
        }
    }

    @Nonnull
    private Screen getTargetScreen(String rout) throws NoSuchRoutException {
        Screen targetScreen = screenMap.get(rout);

        if (targetScreen == null) {
            throw new NoSuchRoutException(rout);
        }

        return targetScreen;
    }

    private void go(@Nonnull String targetScreenRout,
                    @Nullable ScreenMemento previousState) throws NoSuchRoutException {
        Screen targetScreen = getTargetScreen(targetScreenRout);

        history.addFirst(currentRout);
        currentRout = targetScreenRout;

        if (previousState == null) {
            targetScreen.onStart(screenContext);
        } else {
            targetScreen.onActive(previousState, screenContext);
        }
    }

    //pair rout-screen must be unique
    //pair rout-memento must be unique
    private void saveState(@Nonnull Screen currentScreen) throws ScreenIsNotRegisteredException {
        String rout = getRoutOf(currentScreen);

        ScreenMemento currentState = currentScreen.getState();
        screenMementoMap.remove(rout);
        screenMementoMap.put(rout, currentState);
    }


    @Nonnull
    private String getRoutOf(@Nonnull Screen screen) throws ScreenIsNotRegisteredException {
        String rout = null;
        for (Map.Entry<String, Screen> entry : screenMap.entrySet()) {
            if (entry.getValue().equals(screen)) {
                rout = entry.getKey();
                break;
            }
        }

        if (rout == null) {
            throw new ScreenIsNotRegisteredException();
        }

        return rout;
    }
}
