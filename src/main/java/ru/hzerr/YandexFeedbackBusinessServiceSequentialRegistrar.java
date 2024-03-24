package ru.hzerr;

import ru.hzerr.configuration.ExtendedStructureConfigurationInitializer;
import ru.hzerr.fx.engine.core.annotation.Include;
import ru.hzerr.fx.engine.core.annotation.Registered;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.context.registrar.ExtendedAnnotationConfigApplicationContextSequentialRegistrar;

@Registered
public class YandexFeedbackBusinessServiceSequentialRegistrar extends ExtendedAnnotationConfigApplicationContextSequentialRegistrar {

    @Include
    public YandexFeedbackBusinessServiceSequentialRegistrar(IExtendedAnnotationConfigApplicationContext context) {
        super(context);
    }

    @Override
    public void onRegisterAll() {
        register(ExtendedStructureConfigurationInitializer.class);
    }
}
