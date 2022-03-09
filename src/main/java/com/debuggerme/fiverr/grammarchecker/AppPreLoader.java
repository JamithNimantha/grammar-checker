package com.debuggerme.fiverr.grammarchecker;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author Jamith Nimantha
 */
public class AppPreLoader extends Preloader {

    private Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Scene scene = new Scene(new ProgressIndicator(-1), 100, 100);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();


    }

    @Override
    public void handleApplicationNotification(PreloaderNotification pn) {
        if (pn instanceof StateChangeNotification) {
            stage.hide();
        }
    }
}
