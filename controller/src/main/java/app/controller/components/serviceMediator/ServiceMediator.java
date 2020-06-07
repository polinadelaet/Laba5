package controller.components.serviceMediator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public final class ServiceMediator {
    private final Set<Service> services;


    public ServiceMediator(@Nonnull Set<Service> services) {
        this.services = services;
    }


    @Nullable
    public Service get(Class<? extends Service> targetServiceClass) {
        for (Service service : services) {
            try{
                targetServiceClass.cast(service);
                return service;
            } catch (ClassCastException e) {
                //Not acceptable service
            }
        }

        return null;
    }

    public void add(Service service) {
        services.add(service);
    }
}
