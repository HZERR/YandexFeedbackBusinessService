package ru.hzerr.controller.processor;

import javafx.event.Event;
import javafx.event.EventHandler;
import ru.hzerr.FutureEngineFeature;
import ru.hzerr.fx.engine.core.annotation.as.ApplicationLogProvider;
import ru.hzerr.fx.engine.logging.provider.ILogProvider;

import java.util.concurrent.ExecutorService;

@FutureEngineFeature
public abstract class AsyncEventProcessor<T extends Event> implements EventHandler<T>, AutoCloseable {

    private ILogProvider logProvider;
    private final ExecutorService executorService = executorService();

    @Override
    public void handle(T event) {
        executorService.execute(() -> {
            try {
                onProcess(event);
            } catch (Exception e) {
                onFailure(e);
            }
        });
    }

    protected abstract void onProcess(T event) throws Exception;

    protected abstract void onFailure(Exception e);

    protected abstract ExecutorService executorService();

    @Override
    public void close() {
        executorService.close();
    }

    @ApplicationLogProvider
    public void setLogProvider(ILogProvider logProvider) {
        this.logProvider = logProvider;
    }

    public ILogProvider getLogProvider() {
        return logProvider;
    }
}
