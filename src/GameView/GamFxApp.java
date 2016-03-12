package GameView;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.Loader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GamFxApp extends Application {
Controller clr;
    @Override
    public void start(Stage primaryStage) throws Exception {
      FXMLLoader load = new FXMLLoader(getClass().getResource("GameViewFx.fxml"));
       Parent root = (Parent) load.load();
       clr = load.getController();
        primaryStage.setTitle("Patiens");
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getResource("patiens.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public void enterGame() {
        launch();
    }

    public Controller getController(){
        return clr;
    }
}
