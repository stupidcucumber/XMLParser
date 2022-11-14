package Windows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class InfoWindow {
    public static void launch(){
        Stage infoWindow = new Stage();
        VBox layout =  new VBox();
        Image logo = new Image("logotype.png");
        layout.getChildren().add(new ImageView(logo));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, new Insets(0))));
        Label label = new Label("@All rights reserved. Ihor Kostiuk, 2022");
        layout.getChildren().add(label);

        Scene scene = new Scene(layout);
        infoWindow.setHeight(240);
        infoWindow.setWidth(400);
        infoWindow.setScene(scene);
        infoWindow.setTitle("Info");
        infoWindow.show();
    }
}
