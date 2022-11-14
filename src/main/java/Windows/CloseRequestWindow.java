package Windows;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CloseRequestWindow {
    public static void launch(){
        Stage stage = new Stage();
        HBox hBox = new HBox();
        Button apply = new Button("apply changes");
        Button discard = new Button("discard changes");

        hBox.getChildren().addAll(apply, discard);

        Scene scene = new Scene(hBox);
        stage.setScene(scene);
        stage.setHeight(200);
        stage.setWidth(300);
        stage.showAndWait();
    }
}
