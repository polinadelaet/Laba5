package app.controller.command.wish;

import app.controller.components.serviceMediator.Service;
import app.controller.components.serviceMediator.ServiceMediator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class Wish {
    private final Class<? extends Service> targetServiceClass;

    public Class<? extends Service> getTargetServiceClass() {
        return targetServiceClass;
    }

    @Nullable
    private Service service;


    public Wish(@Nonnull Class<? extends Service> targetServiceClass) {
        this.targetServiceClass = targetServiceClass;
    }


    public void visit(ServiceMediator serviceMediator) {
        service = serviceMediator.get(targetServiceClass);
    }

    @Nullable
    public Service getService() {
        return service;
    }

    public boolean contains(Class<? extends Service> serviceClass) {
        if (service == null) {
            return false;
        }

        try {
            serviceClass.cast(service);
            return true;
        } catch (ClassCastException e) {
            //Not acceptable service
            return false;
        }

    }
}
