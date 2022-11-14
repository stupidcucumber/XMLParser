package Windows;

import Items.*;
import Parser.*;
import Parser.DOM.CustomDOMParser;
import Parser.SAX.CustomSAXParser;
import Parser.ToHTMLParser.CustomToHTMLParser;
import Parser.ToHTMLParser.ToHTMLParser;
import Parser.ToHTMLParser.ToXMLParser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Window {
    private static Integer FONT_SIZE = 16;
    enum State{
        DEFAULT,
        CLASSES,
        LECTURERS,
        PRACTICANTS
    }
    private static final ComboBox<State> sceneControl = new ComboBox<>();
    private static State currentState = State.DEFAULT;
    private static Setting currentSetting = new Setting();

    public static void launch(Stage stage){

        // Editing layout
        BorderPane defaultControls = new BorderPane();
        setLayout(defaultControls);

        sceneControl.getItems().addAll(State.CLASSES, State.LECTURERS, State.PRACTICANTS);
        sceneControl.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentSetting = new Setting();
                currentState = sceneControl.getSelectionModel().getSelectedItem();
                setLayout(defaultControls);
            }
        });

        // Making final actions with stage node
        Scene scene = new Scene(defaultControls);
        stage.setWidth(1000);
        stage.setHeight(600);
        stage.setTitle("XMLParser");
        stage.setScene(scene);
        stage.show();
    }
    private static void setLayout(BorderPane borderPane){
        ToolBar toolBar = new ToolBar();
        borderPane.setTop(toolBar);

        Button info = new Button("Info");
        info.setOnAction(e -> InfoWindow.launch());
        toolBar.getItems().add(info);
        Button preferences = new Button("Preferences");
        preferences.setOnAction(e -> PreferencesWindow.launch());
        toolBar.getItems().add(preferences);

        ScrollPane dataView = new ScrollPane();
        dataView.setPadding(new Insets(20));
        VBox controls = new VBox(20);

        controls.setPadding(new Insets(20));
        controls.setPrefSize(300, 600);
        dataView.setMinViewportWidth(700);

        borderPane.setRight(dataView);
        borderPane.setLeft(controls);

        Button reset = new Button("Reset");
        Button show = new Button("Show");
        Button toHTML = new Button("Convert to HTML");
        setToHTML(toHTML);
        Button toXML = new Button("Convert to XML");
        setToXML(toXML);

        show.setOnAction(e -> updateDataView(dataView));

        reset.setOnAction(e -> resetSetOnAction());

        /*
          For Class scene
         */
        Label daysAccessible = new Label("Choose days: ");
        String[] daysNames = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        CheckBox[] days = new CheckBox[daysNames.length];
        for(int i = 0; i < days.length; i++){

            String dayName = daysNames[i];
            days[i] = new CheckBox(dayName);
            days[i].selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                    if(t1)
                        currentSetting.daysOpen.add(dayName);
                    else
                        currentSetting.daysOpen.remove(dayName);
                }
            });
        }

        /*
          For Lecturer scene
         */
        Text chooseFieldsOfStudyText = new Text("Fields of study: ");
        ComboBox<String> chooseFieldsOfStudy = new ComboBox<>();
        setChooseFieldsOfStudy(chooseFieldsOfStudy);

        Text chooseDegreeText = new Text("Choose degree text: ");
        ComboBox<String> chooseDegree = new ComboBox<>();
        setChooseDegree(chooseDegree);

        controls.getChildren().add(sceneControl);
        switch (currentState){
            case CLASSES ->{
                controls.getChildren().add(daysAccessible);
                for(CheckBox checkBox : days)
                    controls.getChildren().add(checkBox);
            }
            case LECTURERS -> controls.getChildren().addAll(chooseFieldsOfStudyText, chooseFieldsOfStudy, chooseDegreeText, chooseDegree);
            case PRACTICANTS -> controls.getChildren().addAll(chooseFieldsOfStudyText, chooseFieldsOfStudy, chooseDegreeText, chooseDegree);
        }

        controls.getChildren().addAll(reset, show, toHTML, toXML);
    }
    private static void setToXML(Button toXML){
        toXML.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String settingParser = switch (currentState){
                    case CLASSES -> "classes";
                    case LECTURERS -> "lecturers";
                    case PRACTICANTS -> "practicants";
                    default -> throw new RuntimeException("Unrecognizable specification!");
                };
                List<Item> itemList = parseSearch(new CustomSAXParser(), settingParser);

                List<Item> result = new ArrayList<>();
                if(currentState == State.CLASSES){
                    for(int i = 0; i < itemList.size(); i++){
                        StudyClass studyClass = (StudyClass) itemList.get(i);
                        if(validateClass(studyClass))
                            result.add(studyClass);
                    }
                }else{
                    for(int i = 0; i < itemList.size(); i++){
                        Scientist scientist = (Scientist) itemList.get(i);
                        if(validateScientist(scientist))
                            result.add(scientist);
                    }
                }

                ToXMLParser parser = new ToXMLParser();
                parser.parse(result, currentState == State.CLASSES ? "classes" : "scientists");
            }
        });
    }
    private static void setToHTML(Button toHTML){
        toHTML.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String settingParser = switch (currentState){
                    case CLASSES -> "classes";
                    case LECTURERS -> "lecturers";
                    case PRACTICANTS -> "practicants";
                    default -> throw new RuntimeException("Unrecognizable specification!");
                };
                List<Item> itemList = parseSearch(new CustomSAXParser(), settingParser);

                List<Item> result = new ArrayList<>();
                if(currentState == State.CLASSES){
                    for(int i = 0; i < itemList.size(); i++){
                        StudyClass studyClass = (StudyClass) itemList.get(i);
                        if(validateClass(studyClass))
                            result.add(studyClass);
                    }
                }else{
                    for(int i = 0; i < itemList.size(); i++){
                        Scientist scientist = (Scientist) itemList.get(i);
                        if(validateScientist(scientist))
                            result.add(scientist);
                    }
                }

                ToHTMLParser parser = new ToHTMLParser();
                try {
                    parser.parse(result, currentState == State.CLASSES ? "classes" : "scientists");
                } catch (TransformerException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private static void setChooseDegree(ComboBox<String> comboBox){
        List<Item> scientists;

        loadChanges();

        String update = switch (currentState){
            case CLASSES -> "lecturers";
            case LECTURERS -> "lecturers";
            case PRACTICANTS -> "practicants";
            case DEFAULT -> "lecturers";
        };

        if(currentSetting.parser.equals("SAX"))
            scientists = parseSearch(new CustomSAXParser(), update);
        else
            scientists = parseSearch(new CustomDOMParser(), update);

        Set<String> degrees = new HashSet<>();
        comboBox.getItems().add("");

        for(Item item : scientists){
            Scientist lecturer = (Scientist) item;
            degrees.add(lecturer.getDegree());
        }

        for (String degree : degrees){
            comboBox.getItems().add(degree);
        }

        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentSetting.degree.clear();
                if(!comboBox.getSelectionModel().isSelected(0)){
                    if(!currentSetting.degree.contains(comboBox.getSelectionModel().getSelectedItem()))
                        currentSetting.degree.add(comboBox.getSelectionModel().getSelectedItem());
                }
            }
        });
    }
    private static void setChooseFieldsOfStudy(ComboBox<String> comboBox){
        List<Item> lecturers;
        if(currentSetting.parser.equals("SAX"))
            lecturers = parseSearch(new CustomSAXParser(), "lecturers");
        else
            lecturers = parseSearch(new CustomDOMParser(), "lecturers");

        Set<String> fields = new HashSet<>();

        comboBox.getItems().add("");
        for(Item item : lecturers){
            Lecturer lecturer = (Lecturer) item;
            String[] field = lecturer.getFieldsOfStudy().split(",");
            for(int i = 0; i < field.length; i++)
                field[i] = field[i].strip();
            Collections.addAll(fields, field);
        }
        for (String field : fields) {
            comboBox.getItems().add(field);
        }

        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentSetting.fieldsOfStudy.clear();
                if(!currentSetting.fieldsOfStudy.contains(comboBox.getSelectionModel().getSelectedItem()) &&
                !comboBox.getSelectionModel().isSelected(0))
                    currentSetting.fieldsOfStudy.add(comboBox.getSelectionModel().getSelectedItem());
            }
        });
    }
    private static void resetSetOnAction(){
        //TODO: make an adequate reset
        //currentState = State.DEFAULT;
    }
    private static void updateDataView(ScrollPane dataView){
        VBox pane = new VBox(20);
        dataView.setContent(pane);

        switch (currentState){
            case CLASSES -> updateClasses(pane);
            case LECTURERS -> updateLecturers(pane);
            case PRACTICANTS -> updatePracticants(pane);
        }
    }
    private static void drawScientist(VBox vBox, List<Item> itemList){
        for(Item item : itemList){
            Scientist lecturer = (Scientist) item;

            if(validateScientist(lecturer)){
                TilePane tilePane = new TilePane();
                tilePane.setTileAlignment(Pos.BASELINE_LEFT);
                tilePane.setPrefTileWidth(200);
                tilePane.setPrefColumns(2);

                Label name = new Label(lecturer.getName());
                name.setStyle("-fx-font-weight: bold; -fx-font-size: %dpt;".formatted(FONT_SIZE));
                Label degree = new Label(lecturer.getDegree());
                Label field = new Label(lecturer.getFieldsOfStudy());

                Text textDegree = new Text("Degree: ");
                textDegree.setStyle("-fx-underline: true;");
                tilePane.getChildren().addAll(textDegree, degree);

                Text textFields = new Text("Fields of study: ");
                textFields.setStyle("-fx-underline: true");
                tilePane.getChildren().addAll(textFields, field);

                vBox.getChildren().addAll(name, tilePane);
            }
        }
    }
    private static void loadChanges(){
        try {
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/settings.txt");
            Scanner scanner = new Scanner(fileInputStream);
            while (scanner.hasNextLine()){
                currentSetting.parser = scanner.nextLine();
                FONT_SIZE = Integer.parseInt(scanner.nextLine());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static void updateLecturers(VBox vBox){
        List<Item> itemList;

        loadChanges();

        if(currentSetting.parser.equals("SAX"))
            itemList = parseSearch(new CustomSAXParser(), "lecturers");
        else
            itemList = parseSearch(new CustomDOMParser(), "lecturers");

        drawScientist(vBox, itemList);
    }
    private static void updatePracticants(VBox vBox){

        List<Item> itemList;

        loadChanges();

        if(currentSetting.parser.equals("SAX"))
            itemList = parseSearch(new CustomSAXParser(), "practicants");
        else
            itemList = parseSearch(new CustomDOMParser(), "practicants");

        drawScientist(vBox, itemList);
    }
    private static void updateClasses(VBox vBox) {
        List<Item> itemList;

        loadChanges();

        if(currentSetting.parser.equals("SAX"))
            itemList = parseSearch(new CustomSAXParser(), "classes");
        else
            itemList = parseSearch(new CustomDOMParser(), "classes");


        for (Item item : itemList) {
            StudyClass studyClass = (StudyClass) item;
            if(validateClass(studyClass)){
                Text name = new Text(studyClass.getName());
                name.setStyle("-fx-font-weight: bold; -fx-font-size: 18pt;");

                Label description = new Label(studyClass.getDescription());

                description.setStyle("-fx-font-size: %dpt;".formatted(FONT_SIZE));
                Label days = new Label(studyClass.getDaysWork());

                StringBuilder lecturersStr = new StringBuilder();
                lecturersStr.append("Lecturers: ");
                for(Lecturer lecturer : studyClass.getLecturers())
                    lecturersStr.append(lecturer.getName()).append("  ");
                Label lecturers = new Label(lecturersStr.toString());

                StringBuilder practicantsStr = new StringBuilder();
                practicantsStr.append("Practicants: ");
                for(Practicant practicant : studyClass.getPracticants())
                    practicantsStr.append(practicant.getName()).append("  ");
                Label practicants = new Label(practicantsStr.toString());

                vBox.getChildren().addAll(name, description, lecturers, practicants, days);
            }
        }
    }
    private static boolean validateScientist(Scientist scientist){
        String[] fields = scientist.getFieldsOfStudy().split(",");
        for(int i = 0; i < fields.length; i++){
            fields[i] = fields[i].strip();
            System.out.println(fields[i]);
        }

        boolean isChoosed = false;
        for (String field : fields){
            for(String requiredField : currentSetting.fieldsOfStudy)
                if(field.equals(requiredField)){
                    isChoosed = true;
                    break;
                }
        }

        boolean isDegree = false;
        for(String degree : currentSetting.degree)
            if (degree.equals(scientist.getDegree())) {
                isDegree = true;
                break;
            }

        System.out.println(currentSetting.fieldsOfStudy.size());
        return (isChoosed || currentSetting.fieldsOfStudy.size() == 0) && (isDegree || currentSetting.degree.size() == 0);
    }
    private static boolean validateClass(StudyClass studyClass){
        String[] days = studyClass.getDaysWork().replace(" ", "").split(",");
        boolean isOpen = false;
        for(String requiredDays : currentSetting.daysOpen){
            for (String day : days)
                if (requiredDays.equals(day)) {
                    isOpen = true;
                    break;
                }
        }


        return isOpen || currentSetting.daysOpen.size() == 0;
    }
    private static List<Item> parseSearch(Parser parser, String expression){
        try {
            return parser.parse(expression);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
