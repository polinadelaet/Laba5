package app.controller.services.exitingDirector;

import app.controller.components.serviceMediator.Service;

import java.util.List;

public final class ExitingDirector implements Service {
    private final List<INeedExiting> needExitList;


    public ExitingDirector(List<INeedExiting> needExitList) {
        this.needExitList = needExitList;
    }


    public void exit() {
        needExitList.forEach(INeedExiting::exit);
    }

    public void addINeedExiting(INeedExiting iNeedExiting) {
        needExitList.add(iNeedExiting);
    }
}
