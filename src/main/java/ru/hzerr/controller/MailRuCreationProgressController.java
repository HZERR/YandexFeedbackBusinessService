package ru.hzerr.controller;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import javafx.util.Subscription;
import ru.hzerr.fx.engine.core.annotation.FXController;
import ru.hzerr.fx.engine.core.annotation.FXEntity;
import ru.hzerr.fx.engine.core.entity.PopupController;
import ru.hzerr.fx.engine.core.language.ILocalization;

@FXController
@FXEntity(fxml = "popup/creationProgress.fxml", internationalization = "popup/creationProgress.json", theme = "popup/creationProgress.css")
public class MailRuCreationProgressController extends PopupController {

    @FXML private Label title, description;

    @FXML private ProgressBar progressBar;

    private DoubleProperty heightPopupProperty = new SimpleDoubleProperty();
    private DoubleProperty widthPopupProperty = new SimpleDoubleProperty();
    private double popupHeight;
    private double popupWidth;

    @Override
    protected void onInit() {
        popup.centerOnScreen();
        popup.setAutoFix(true);
        popup.setAutoHide(false);
        popup.getContent().addFirst(getContentAsParent());
    }

    @Override
    protected void onChangeLanguage(ILocalization localization) {
        title.setText(localization.getConfiguration().getString("title"));
    }

    @Override
    public void onDestroy() {
        Platform.runLater(popup::hide);
        super.onDestroy();
    }

    @Override
    public void onConnectDestroyEvent() {
        // вызов метода onDestroy должен быть выполнен извне или в другом методе данного контроллера
    }

    public void setProgressAsSetUpRandomData() {
        acceptLocalizationProvider(localizationProvider -> {
            Platform.runLater(() -> description.setText(localizationProvider.getLocalization().getConfiguration().getString("progress.setRandomData")));
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 0.07)));
        timeline.play();
    }

    public void setProgressAsLaunchBrowser() {
        acceptLocalizationProvider(localizationProvider -> {
            Platform.runLater(() -> description.setText(localizationProvider.getLocalization().getConfiguration().getString("progress.browserLaunch")));
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 0.1)));
        timeline.play();
    }

    public void setProgressAsFillingForm() {
        acceptLocalizationProvider(localizationProvider -> {
            Platform.runLater(() -> description.setText(localizationProvider.getLocalization().getConfiguration().getString("progress.fillingForm")));
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 0.45)));
        timeline.play();
    }

    public void setProgressAsGettingCaptchaImage() {
        acceptLocalizationProvider(localizationProvider -> {
            Platform.runLater(() -> description.setText(localizationProvider.getLocalization().getConfiguration().getString("progress.gettingCaptchaImageAsBytes")));
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 0.55)));
        timeline.play();
    }

    public void setProgressAsSubstituteRecognizeData() {
        acceptLocalizationProvider(localizationProvider -> {
            Platform.runLater(() -> description.setText(localizationProvider.getLocalization().getConfiguration().getString("progress.substituteRecognizeDataIntoForm")));
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 0.75)));
        timeline.play();
    }

    public void setProgressAsCheckRecognizeData() {
        acceptLocalizationProvider(localizationProvider -> {
            Platform.runLater(() -> description.setText(localizationProvider.getLocalization().getConfiguration().getString("progress.validateRecognizeData")));
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 0.9)));
        timeline.play();
    }

    public void setProgressAsSuccess() {
        acceptLocalizationProvider(localizationProvider -> {
            Platform.runLater(() -> description.setText(localizationProvider.getLocalization().getConfiguration().getString("progress.success")));
        });

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(progressBar.progressProperty(), 1)));
        timeline.setOnFinished(event -> onDestroy());
        timeline.play();
    }

    public void temporarilyHide() {
        popupHeight = popup.getHeight();
        popupWidth = popup.getWidth();

        heightPopupProperty.set(popup.getHeight());
        widthPopupProperty.set(popup.getWidth());

        Subscription heightSubscription = heightPopupProperty.subscribe(nValue -> popup.setHeight((double) nValue));
        Subscription widthSubscription = widthPopupProperty.subscribe(nValue -> popup.setWidth((double) nValue));

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(heightPopupProperty, 0), new KeyValue(widthPopupProperty, 0)));
        timeline.setOnFinished(event -> {
            heightSubscription.unsubscribe();
            widthSubscription.unsubscribe();
            popup.hide();
        });
        timeline.play();
    }

    public void temporarilyShow() {
        Subscription heightSubscription = heightPopupProperty.subscribe(nValue -> popup.setHeight((double) nValue));
        Subscription widthSubscription = widthPopupProperty.subscribe(nValue -> popup.setWidth((double) nValue));

        Platform.runLater(this::view);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(heightPopupProperty, popupHeight), new KeyValue(widthPopupProperty, popupWidth)));
        timeline.setOnFinished(event -> {
            heightSubscription.unsubscribe();
            widthSubscription.unsubscribe();
        });
        timeline.play();
    }
}
