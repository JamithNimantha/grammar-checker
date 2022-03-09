package com.debuggerme.fiverr.grammarchecker.controller;

import com.debuggerme.fiverr.grammarchecker.util.GrammarChecker;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jamith Nimantha
 */
public class MainController implements Initializable {

    @FXML
    private Button btnFolder;

    @FXML
    private TextField txtFolder;

    @FXML
    private Button btnStart;

    private static String EXTENSION = ".txt";

    @FXML
    Parent root;

    @FXML
    private Label lblNumOfArticles;

    @FXML
    private ImageView imgLoader;

    @FXML
    private Label lblProgress;

    private String dirPath;

    @FXML
    void btnFolderOnAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        Stage stage = (Stage) MainController.this.root.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory != null) {
            dirPath = selectedDirectory.getAbsolutePath();
            txtFolder.setText(dirPath);
            btnStart.setDisable(false);
            lblNumOfArticles.setText("Found " + getTextFiles(selectedDirectory).length + " Articles");
            lblNumOfArticles.setVisible(true);
        } else {
            if (dirPath == null) {
                btnStart.setDisable(true);
            }
        }

    }

    private File[] getTextFiles(File dir) {
        return dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(EXTENSION));
    }

    @FXML
    void btnStartOnStart(ActionEvent event) throws IOException {
        loading(true);
        if (dirPath != null) {
            File outputFolder = new File(dirPath.concat(" - Grammar Fixed"));
            File[] files = getTextFiles(new File(dirPath));
            if (files.length < 1) {
                showAlert(
                        Alert.AlertType.ERROR,
                        "No Text Files Exists!",
                        ButtonType.OK,
                        "Make Sure Text Files(.txt) Exists!"
                );
                loading(false);
            } else {
                fixGrammar(files, outputFolder);
            }
        } else {
            showAlert(
                    Alert.AlertType.WARNING,
                    "Error! Folder Not Selected",
                    ButtonType.OK,
                    "Please Select a Folder to Continue!"
            );
            loading(false);
        }
    }

    private void fixGrammar(File[] files, File outputFolder) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                AtomicInteger index = new AtomicInteger(1);
                for (File file : Objects.requireNonNull(files)) {
                    try {
                        String text = FileUtils.readFileToString(file, StandardCharsets.UTF_8); // Use Charset.forName("windows-1252") If Encoding does not work correctly
                        File outputFile = new File(outputFolder, file.getName());
                        String correctedText = GrammarChecker.correctGrammar(text);
                        FileUtils.writeStringToFile(outputFile, correctedText, StandardCharsets.UTF_8);
                        Platform.runLater(() -> lblProgress.setText("Completed " + index.getAndIncrement() + " of " + files.length));
                    } catch (FileNotFoundException e) {
                        showAlert(
                                Alert.AlertType.ERROR,
                                "File Not Found!",
                                ButtonType.CLOSE,
                                "File Not Found! Make Sure File is exists."
                        );
                        loading(false);
                    }
                }
                showAlert(
                        Alert.AlertType.INFORMATION,
                        "Successful!",
                        ButtonType.CLOSE,
                        "Successfully Checked and Fixed Grammar!"
                );
                loading(false);
                return null;
            }
        };
        new Thread(task).start();

    }

    private void showAlert(final Alert.AlertType alertType, final String title, final ButtonType buttonType, final String msg) {
        Platform.runLater(() -> {
            Stage stage = (Stage) MainController.this.root.getScene().getWindow();
            Alert a = new Alert(alertType, title, buttonType);
            a.initOwner(stage);
            a.setTitle(title);
            a.setHeaderText(null);
            a.setContentText(msg);
            a.show();
        });
    }

    private void loading(final boolean show) {
        Platform.runLater(() -> {
            imgLoader.setVisible(show);
            btnStart.setDisable(show);
            btnFolder.setDisable(show);
        });
    }

    public void initialize(URL location, ResourceBundle resources) {
        btnStart.setDisable(true);
        lblNumOfArticles.setVisible(false);
    }
}
