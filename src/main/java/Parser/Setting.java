package Parser;

import java.util.ArrayList;
import java.util.List;


public class Setting {
    private String parser;
    private String converter;
    private List<String> daysOpen;
    private List<String> degree;
    private List<String> fieldsOfStudy;

    public Setting(){
        daysOpen = new ArrayList<>();
        degree = new ArrayList<>();
        fieldsOfStudy = new ArrayList<>();
        parser = "SAX";
        converter = "KI HTML Converter";
    }

    public String getParser() {
        return parser;
    }

    public void setParser(String parser) {
        this.parser = parser;
    }

    public String getConverter() {
        return converter;
    }

    public void setConverter(String converter) {
        this.converter = converter;
    }

    public List<String> getDaysOpen() {
        return daysOpen;
    }

    public void setDaysOpen(List<String> daysOpen) {
        this.daysOpen = daysOpen;
    }

    public List<String> getDegree() {
        return degree;
    }

    public void setDegree(List<String> degree) {
        this.degree = degree;
    }

    public List<String> getFieldsOfStudy() {
        return fieldsOfStudy;
    }

    public void setFieldsOfStudy(List<String> fieldsOfStudy) {
        this.fieldsOfStudy = fieldsOfStudy;
    }
}
