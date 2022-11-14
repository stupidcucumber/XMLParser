package Parser.SAX;

import Items.Item;
import Items.Lecturer;
import Items.Practicant;
import Items.StudyClass;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.ArrayList;
import java.util.List;

public class SAXHandler extends DefaultHandler{

    enum SearchFor{
        LECTURERS,
        CLASSES,
        PRACTICANTS
    }

    private List<Item> items;
    private Lecturer lecturer;
    private Practicant practicant;
    private StudyClass studyClass;
    private StringBuilder data;

    public List<Item> getItems() {
        return items;
    }

    // Lecturer
    private boolean isLecturer = false;
    private boolean isPracticant = false;
    private boolean bDegree = false;
    private boolean bCredentials = false;
    private boolean bFields = false;

    // StudyClass
    private boolean isClass = false;
    private boolean bNewLecturer = false;
    private boolean bNewPracticant = false;
    private boolean bDescription = false;
    private boolean bDays = false;

    private final SearchFor searchFor; // lecturers, classes, students

    public SAXHandler(String specification){
        switch (specification) {
            case "lecturers" -> searchFor = SearchFor.LECTURERS;
            case "classes" -> searchFor = SearchFor.CLASSES;
            case "practicants" -> searchFor = SearchFor.PRACTICANTS;
            default -> throw new RuntimeException("This specification is invalid!");
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (searchFor) {
            case LECTURERS -> searchForLecturers(uri, localName, qName, attributes);
            case CLASSES -> searchForClasses(uri, localName, qName, attributes);
            case PRACTICANTS -> searchForPracticants(uri, localName, qName, attributes);
            default -> throw new RuntimeException("This specification is not defined!");
        }
    }

    private void searchForPracticants(String uri, String localName, String qName, Attributes attributes){
        if(qName.equalsIgnoreCase("practicant")){
            String id = attributes.getValue("id");
            practicant = new Practicant();
            practicant.setId(id);
            isPracticant = true;

            if(items == null)
                items = new ArrayList<>();
        }else if(isPracticant && qName.equalsIgnoreCase("credentials")){
            bCredentials = true;
        }else if(isPracticant && qName.equalsIgnoreCase("degree")){
            bDegree = true;
        }else if(isPracticant && qName.equalsIgnoreCase("fields")){
            bFields = true;
        }

        data = new StringBuilder();
    }


    private void searchForLecturers(String uri, String localName, String qName, Attributes attributes){
        if(qName.equalsIgnoreCase("lecturer")){
            String id = attributes.getValue("id");
            lecturer = new Lecturer();
            lecturer.setId(id);
            isLecturer = true;

            if(items == null)
                items = new ArrayList<>();
        }else if(qName.equalsIgnoreCase("credentials") && isLecturer){
            bCredentials = true;
        }else if (qName.equalsIgnoreCase("degree") && isLecturer){
            bDegree = true;
        }else if(qName.equalsIgnoreCase("fields") && isLecturer){
            bFields = true;
        }

        data = new StringBuilder();
    }

    private void endForPracticants(String qName){
        if(bCredentials){
            practicant.setName(data.toString());
            bCredentials = false;
        }else if(bDegree){
            practicant.setDegree(data.toString());
            bDegree = false;
        }else if(bFields){
            practicant.setFieldsOfStudy(data.toString());
            bFields = false;
        }

        if(qName.equalsIgnoreCase("practicant")){
            if(!items.contains(practicant)){
                System.out.println("Hey");
                items.add(practicant);
            }

            isPracticant = false;
        }
    }

    private void endForLecturers(String qName){
        if(bCredentials){
            lecturer.setName(data.toString());
            bCredentials = false;
        }else if(bDegree){
            lecturer.setDegree(data.toString());
            bDegree = false;
        }else if(bFields){
            lecturer.setFieldsOfStudy(data.toString());
            bFields = false;
        }

        if(qName.equalsIgnoreCase("lecturer")){
            if(!items.contains(lecturer))
                items.add(lecturer);
            isLecturer = false;
        }
    }

    private void searchForClasses(String uri, String localName, String qName, Attributes attributes){
        if(qName.equalsIgnoreCase("class")){
            studyClass = new StudyClass();
            isClass = true;

            studyClass.setId(attributes.getValue("id"));
            studyClass.setName(attributes.getValue("name"));

            if(items == null)
                items = new ArrayList<>();
        }else if(qName.equalsIgnoreCase("description")){
            bDescription = true;
        }else if(qName.equalsIgnoreCase("days")){
            bDays = true;
        }else if(isClass && qName.equalsIgnoreCase("lecturer")){
            String id = attributes.getValue("id");
            lecturer = new Lecturer();
            lecturer.setId(id);

            bNewLecturer = true;
        }else if(isClass && qName.equalsIgnoreCase("practicant")){
            String id = attributes.getValue("id");
            practicant = new Practicant();
            practicant.setId(id);

            bNewPracticant = true;
        }else if(isClass && (bNewLecturer || bNewPracticant) && qName.equalsIgnoreCase("credentials")){
            bCredentials = true;
        }else if(isClass && (bNewLecturer || bNewPracticant) && qName.equalsIgnoreCase("degree")){
            bDegree = true;
        }else if(isClass && (bNewLecturer || bNewPracticant) && qName.equalsIgnoreCase("fields")){
            bFields = true;
        }

        data = new StringBuilder();
    }

    private void endForClasses(String qName){
        if(bDescription){
            studyClass.setDescription(data.toString());
            bDescription = false;
        }else if(bDays){
            studyClass.setDaysWork(data.toString());
            bDays = false;
        }else if(bNewLecturer && bCredentials){
            lecturer.setName(data.toString());
            bCredentials = false;
        }else if(bNewLecturer && bDegree){
            lecturer.setDegree(data.toString());
            bDegree = false;
        }else if(bNewLecturer && bFields){
            lecturer.setFieldsOfStudy(data.toString());
            bFields = false;
        } else if (bNewPracticant && bCredentials) {
            practicant.setName(data.toString());
            bCredentials = false;
        }else if(bNewPracticant && bDegree){
            practicant.setDegree(data.toString());
            bDegree = false;
        }else if(bNewPracticant && bFields){
            practicant.setFieldsOfStudy(data.toString());
            bFields = false;
        }

        if(qName.equalsIgnoreCase("class")){
            items.add(studyClass);
            isClass = false;
        }

        if(bNewLecturer && qName.equalsIgnoreCase("lecturer")){
            if(!studyClass.getLecturers().contains(lecturer))
                studyClass.getLecturers().add(lecturer);
            bNewLecturer = false;
        }

        if(bNewPracticant && qName.equalsIgnoreCase("practicant")){
            if(!studyClass.getPracticants().contains(practicant))
                studyClass.getPracticants().add(practicant);
            bNewPracticant = false;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (searchFor){
            case LECTURERS -> endForLecturers(qName);
            case CLASSES -> endForClasses(qName);
            case PRACTICANTS -> endForPracticants(qName);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
    }
}
