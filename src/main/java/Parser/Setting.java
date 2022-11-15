package Parser;

import java.util.ArrayList;
import java.util.List;

public class Setting {
    public String parser;
    public String converter;
    public List<String> daysOpen;
    public List<String> degree;
    public List<String> fieldsOfStudy;

    public Setting(){
        daysOpen = new ArrayList<>();
        degree = new ArrayList<>();
        fieldsOfStudy = new ArrayList<>();
        parser = "SAX";
        converter = "KI HTML Converter";
    }
}
