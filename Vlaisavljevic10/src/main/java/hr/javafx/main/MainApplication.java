package hr.javafx.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

import static hr.javafx.main.Main.log;

public class MainApplication extends Application {
    protected static Stage appStage;
    @Override
    public void start(Stage stage) throws IOException {
        appStage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/hr/javafx/firstScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Vlaisavljevic lab");
        stage.setScene(scene);

        Image icon = new Image(Objects.requireNonNull(MainApplication.class.getResource("/images/icon.png")).toExternalForm());
        stage.getIcons().add(icon);

        stage.show();
    }

    public static Stage getStage() {
        return appStage;
    }

    public static void showWindow(String resourcePath) {
        try {
            var window = (Parent) FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource(resourcePath)));
            appStage.setScene(new Scene(window));
            appStage.show();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch();
    }
}