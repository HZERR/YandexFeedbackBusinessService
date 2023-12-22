package ru.hzerr.configuration;

import org.springframework.context.annotation.Scope;
import ru.hzerr.fx.engine.core.annotation.Registered;

@Registered
@Scope("prototype")
public @interface RegisteredPrototype {
}
