package ru.hzerr;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.hzerr.controller.YandexFeedbackBusinessServiceController;
import ru.hzerr.fx.engine.core.FXEngine;
import ru.hzerr.fx.engine.core.context.IExtendedAnnotationConfigApplicationContext;
import ru.hzerr.fx.engine.core.entity.ControllerLoadMetaData;
import ru.hzerr.fx.engine.core.entity.Entity;

// FXEngine -> Добавить обработку args и возможность создавать контроллеры через Spring Context
// Добавить информацию для понятия какие классы необходимо реализовать READY
// Добавить информация для понятия какие ресурсы необходимо создать и как их связать с программой
// Добавить в EntityLoader ExtendedCompletableFuture для возможности запускать runnable в JavaFX Application Thread
// Rename ControllerLoadMetaData -> LoadMetaData
public class YandexFeedbackBusinessService extends FXEngine {

    @Override
    protected IExtendedAnnotationConfigApplicationContext createApplicationContext() {
        return createAutomaticExtendedAnnotationConfigApplicationContextProvider(FXEngine.class).getContext();
    }

    @Override
    protected Scene onStart(Stage stage) throws Exception {
        Entity<YandexFeedbackBusinessServiceController, Parent> entity = context.getEntityLoader().load(ControllerLoadMetaData.from(YandexFeedbackBusinessServiceController.class), Parent.class);

        return new Scene(entity.getNode());
    }
}