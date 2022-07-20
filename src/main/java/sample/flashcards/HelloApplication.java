package sample.flashcards;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage stg;

    @Override
    public void start(Stage stage) throws IOException {
        stg = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("cardPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        // String css = this.getClass().getResource("style.css").toExternalForm();
        // scene.getStylesheets().add(css);
        stage.setTitle("Welcome to the FlashCard-app");
        stage.setScene(scene);
        stage.show();
    }
    public void changeScene(String fxml,String title, Integer height_max, Integer width_max, Integer height_min, Integer width_min, Boolean Resizable_bool) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.setResizable(Resizable_bool);
        stg.setTitle(title);
        stg.setMaxHeight(height_max);
        stg.setMaxWidth(width_max);
        stg.setMinWidth(width_min);
        stg.setMinHeight(height_min);

        stg.getScene().setRoot(pane);

    }


    public static void main(String[] args) {
        launch();
    }
}