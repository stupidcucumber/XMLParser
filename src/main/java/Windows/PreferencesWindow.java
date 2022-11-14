package Windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Scanner;

public class PreferencesWindow {
    private static final String PATH_TO_SETTINGS_FILE = "src/main/resources/settings.txt";
    private static ComboBox<String> stringComboBoxChooseFontSize;
    private static ComboBox<String> comboBox;
    public static void launch(){
        Stage stage = new Stage();
        stage.setWidth(500);
        stage.setHeight(400);
        BorderPane layout = new BorderPane();
        setLayout(layout);

        // Finishing setting up stage
        Scene scene = new Scene(layout);
        stage.setScene(scene);
        //stage.setOnCloseRequest(e -> CloseRequestWindow.launch());
        stage.setTitle("Preferences");
        stage.show();
    }

    private static void setLayout(BorderPane pane){
        pane.setPadding(new Insets(20));

        TilePane controls = new TilePane();
        controls.setHgap(10);
        controls.setVgap(20);
        controls.setPrefColumns(2);
        controls.setTileAlignment(Pos.BASELINE_LEFT);
        // Setting up controls
        Text chooseParser = new Text("Choose parser: ");
        comboBox = new ComboBox<>();
        setChooseParserComboBox(comboBox);
        controls.getChildren().addAll(chooseParser, comboBox);

        Text chooseFontSize = new Text("Choose font size: ");
        stringComboBoxChooseFontSize = new ComboBox<>();
        setStringComboBoxChooseFontSize(stringComboBoxChooseFontSize);
        controls.getChildren().addAll(chooseFontSize, stringComboBoxChooseFontSize);

        try {
            loadChanges();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Setting up buttons
        Button saveOptions = new Button("Save");
        setSave(saveOptions);
        Button cancelOptions = new Button("Cancel");
        setCancel(cancelOptions);
        Button setToDefault = new Button("Reset");
        setReset(setToDefault);

        HBox stackPane = new HBox(20);
        stackPane.setAlignment(Pos.CENTER);
        stackPane.getChildren().addAll(saveOptions, cancelOptions, setToDefault);
        pane.setBottom(stackPane);
        pane.setCenter(controls);
    }

    private static void loadChanges() throws FileNotFoundException {
        File file = new File(PATH_TO_SETTINGS_FILE);

        if(file.exists()){
            FileInputStream inputStream = new FileInputStream(file);
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()){
                String parser = scanner.nextLine();
                String font = scanner.nextLine();


                comboBox.getSelectionModel().select(parser);
                stringComboBoxChooseFontSize.getSelectionModel().select(font);
            }
        }
    }

    private static void setSave(Button button){
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String preferredParser = comboBox.getSelectionModel().getSelectedItem();
                String preferredFont = stringComboBoxChooseFontSize.getSelectionModel().getSelectedItem();

                File file = new File(PATH_TO_SETTINGS_FILE);
                if(!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    file.delete();
                    file.createNewFile();

                    PrintWriter printWriter = new PrintWriter(file);

                    printWriter.println(preferredParser);
                    printWriter.flush();
                    printWriter.println(preferredFont);
                    printWriter.flush();

                } catch (IOException e ) {
                    throw new RuntimeException(e);
                }

            }
        });
    }

    private static void setCancel(Button button){

    }

    private static void setReset(Button button){
        button.setOnAction(e -> {
            comboBox.getSelectionModel().selectFirst();
            stringComboBoxChooseFontSize.getSelectionModel().selectFirst();
            File file = new File(PATH_TO_SETTINGS_FILE);

            file.delete();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println(comboBox.getSelectionModel().getSelectedItem());
                printWriter.println(stringComboBoxChooseFontSize.getSelectionModel().getSelectedItem());
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private static void setStringComboBoxChooseFontSize(ComboBox<String> comboBox){
        comboBox.getItems().addAll("12", "14", "16", "20");
        comboBox.setPrefWidth(150);
    }

    private static void setChooseParserComboBox(ComboBox<String> comboBox){
        comboBox.getItems().add("SAX");
        comboBox.getItems().add("DOM");
        comboBox.getSelectionModel().selectFirst();
    }
}
