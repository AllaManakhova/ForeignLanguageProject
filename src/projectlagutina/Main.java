
package projectlagutina;

import java.io.FileNotFoundException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException {

        Model model = new Model();
        View view = new View(model);

        Scene scene = new Scene(view);
        stage.setTitle("French Lessons");
        stage.setScene(scene);
        stage.setMaximized(true);

        scene.getStylesheets().add(Main.class.getResource("newCascadeStyleSheet.css").toExternalForm());
         //scene.getStylesheets().add(Test1.class.getResource("newCascadeStyleSheetView.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
