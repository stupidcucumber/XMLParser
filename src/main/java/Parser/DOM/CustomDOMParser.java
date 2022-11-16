package Parser.DOM;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Items.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import Parser.Parser;
import Parser.Setting;
import Parser.Validator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomDOMParser implements Parser {
    private Document document;
    private Validator validator;
    @Override
    public List<Item> parse(String value, Setting setting) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(new File(PATH));
        validator = new Validator(setting);

        document.getDocumentElement().normalize();

        return switch (value){
            case "classes" -> findClasses();
            case "lecturers" -> findLecturers();
            case "practicants" -> findPracticants();
            default -> new ArrayList<>();
        };
    }


    private List<Item> findClasses(){
        List<Item> result = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("class");
        for(int i = 0; i < nodeList.getLength(); i++){
            Node item = nodeList.item(i);

            if(item.getNodeType() == Node.ELEMENT_NODE){
                StudyClass studyClass = new StudyClass();
                Element element = (Element) item;

                studyClass.setId(element.getAttribute("id"));
                studyClass.setName(element.getAttribute("name"));
                studyClass.setDescription(element.getElementsByTagName("description").item(0).getTextContent());
                studyClass.setDaysWork(element.getElementsByTagName("days").item(0).getTextContent());

                // Searching for lecturers and practicants
                Node positions = element.getElementsByTagName("poisitions").item(0);
                List<Lecturer> lecturers = new ArrayList<>();
                List<Practicant> practicants = new ArrayList<>();

                // Searching for lecturers
                NodeList lecturerList = ((Element) positions).getElementsByTagName("lecturer");
                searchingForScientist(lecturers, lecturerList, "lecturer");

                // Searching for practicants
                NodeList practicantList = ((Element) positions).getElementsByTagName("practicant");
                searchingForScientist(practicants, practicantList, "practicant");

                studyClass.setLecturers(lecturers);
                studyClass.setPracticants(practicants);
                if(!result.contains(studyClass) && validator.validateClass(studyClass))
                    result.add(studyClass);
            }
        }

        return result;
    }

    private List<Item> findLecturers(){
        List<Item> result = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("class");
        for(int i = 0; i < nodeList.getLength(); i++){
            Node item = nodeList.item(i);

            if(item.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) item;

                // Searching for lecturers and practicants
                Node positions = element.getElementsByTagName("poisitions").item(0);

                // Searching for lecturers
                NodeList lecturerList = ((Element) positions).getElementsByTagName("lecturer");
                searchingForScientist(result, lecturerList, "lecturer");
            }
        }

        return result;
    }

    private List<Item> findPracticants(){
        List<Item> result = new ArrayList<>();

        NodeList nodeList = document.getElementsByTagName("class");
        for(int i = 0; i < nodeList.getLength(); i++){
            Node item = nodeList.item(i);

            if(item.getNodeType() == Node.ELEMENT_NODE){
                Element element = (Element) item;

                // Searching for lecturers and practicants
                Node positions = element.getElementsByTagName("poisitions").item(0);

                // Searching for practicants
                NodeList practicantList = ((Element) positions).getElementsByTagName("practicant");
                searchingForScientist(result, practicantList, "practicant");
            }
        }

        return result;
    }

    private <T extends Item> void  searchingForScientist(List<T> result, NodeList practicantList, String specification){
        for(int j = 0; j < practicantList.getLength(); j++){
            Node itemPracticant = practicantList.item(j);

            if(itemPracticant.getNodeType() == Node.ELEMENT_NODE){
                Scientist scientist = (specification.equals("practicant") ? new Practicant() : new Lecturer());

                Element practicantElement = (Element) itemPracticant;

                scientist.setId(practicantElement.getAttribute("id"));
                scientist.setName(practicantElement.getElementsByTagName("credentials").item(0).getTextContent());
                scientist.setDegree(practicantElement.getElementsByTagName("degree").item(0).getTextContent());
                scientist.setFieldsOfStudy(practicantElement.getElementsByTagName("fields").item(0).getTextContent());

                if(!result.contains(scientist) && validator.validateScientist(scientist))
                    result.add((T) scientist);
            }
        }
    }
}
