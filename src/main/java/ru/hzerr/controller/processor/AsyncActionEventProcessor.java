package ru.hzerr.controller.processor;

import javafx.event.ActionEvent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AsyncActionEventProcessor extends AsyncEventProcessor<ActionEvent> {

    @Override
    protected ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }
}
