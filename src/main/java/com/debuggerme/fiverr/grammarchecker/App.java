package com.debuggerme.fiverr.grammarchecker;

import com.debuggerme.fiverr.grammarchecker.util.GrammarChecker;
import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * @author Jamith Nimantha
 *
 */
public class App extends Application {

    @Override
    public void stop() throws Exception {
        super.stop();
        GrammarChecker.quitWebDriver();
    }

    public static void main(String[] args) {
        LauncherImpl.launchApplication(App.class, AppPreLoader.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        notifyPreloader(new Preloader.StateChangeNotification(Preloader.StateChangeNotification.Type.BEFORE_START));
        Parent parent = FXMLLoader.load(Objects.requireNonNull(this.getClass().getResource("/com/debuggerme/fiverr/grammarchecker/view/Main.fxml")));
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("/com/debuggerme/fiverr/grammarchecker/assets/logo.png"))));
        Scene temp = new Scene(parent);
        primaryStage.setScene(temp);
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Grammar Checker Tool v1.1 By DebuggerMe");
        primaryStage.setResizable(false);

        primaryStage.show();
    }


}
