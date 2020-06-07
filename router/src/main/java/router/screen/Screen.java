package router.screen;

/**
 * See {@link router.Router Router} for framework details.
 */
public interface Screen {
    /**
     * This method will be invoked every time when Router tries open
     * this screen and Router does not store a previous state for this screen.
     * @param screenContext see {@link router.Router Router} for details.
     */
    void onStart(ScreenContext screenContext);

    /**
     * This method will be invoked every time when Router tries open
     * this screen and Router stores a previous state for this screen.
     * @param screenMemento see {@link router.Router Router} for details.
     * @param screenContext see {@link router.Router Router} for details.
     */
    void onActive(ScreenMemento screenMemento, ScreenContext screenContext);

    /**
     * This method will be invoked if you try to save the state of this screen
     * via {@link router.Router#goAndSaveState(String, Screen) Router.goAndSaveState()}.
     * see {@link router.Router Router} for details.
     * @return an implementation of {@link ScreenMemento ScreenMemento} marker interface.
     */
    ScreenMemento getState();
}
