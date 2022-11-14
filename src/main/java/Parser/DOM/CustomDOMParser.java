package Parser.DOM;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Items.Lecturer;
import Items.Practicant;
import Items.StudyClass;
import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;

import Items.Item;
import Parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomDOMParser implements Parser {
    private Document document;
    @Override
    public List<Item> parse(String value) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        document = documentBuilder.parse(new File(PATH));

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
                for(int j = 0; j < lecturerList.getLength(); j++){
                    Node itemLecturer = lecturerList.item(j);

                    if(itemLecturer.getNodeType() == Node.ELEMENT_NODE){
                        Lecturer lecturer = new Lecturer();
                        Element lecturerElement = (Element) itemLecturer;

                        lecturer.setId(lecturerElement.getAttribute("id"));
                        lecturer.setName(lecturerElement.getElementsByTagName("credentials").item(0).getTextContent());
                        lecturer.setDegree(lecturerElement.getElementsByTagName("degree").item(0).getTextContent());
                        lecturer.setFieldsOfStudy(lecturerElement.getElementsByTagName("fields").item(0).getTextContent());
                        if(!lecturers.contains(lecturer))
                            lecturers.add(lecturer);
                    }
                }

                // Searching for practicants
                NodeList practicantList = ((Element) positions).getElementsByTagName("practicant");
                for(int j = 0; j < practicantList.getLength(); j++){
                    Node itemPracticant = practicantList.item(j);

                    if(itemPracticant.getNodeType() == Node.ELEMENT_NODE){
                        Practicant practicant = new Practicant();

                        Element practicantElement = (Element) itemPracticant;

                        practicant.setId(practicantElement.getAttribute("id"));
                        practicant.setName(practicantElement.getElementsByTagName("credentials").item(0).getTextContent());
                        practicant.setDegree(practicantElement.getElementsByTagName("degree").item(0).getTextContent());
                        practicant.setFieldsOfStudy(practicantElement.getElementsByTagName("fields").item(0).getTextContent());
                        if(!practicants.contains(practicant))
                            practicants.add(practicant);
                    }

                }

                studyClass.setLecturers(lecturers);
                studyClass.setPracticants(practicants);
                if(!result.contains(studyClass))
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
                for (int j = 0; j < lecturerList.getLength(); j++) {
                    Node itemLecturer = lecturerList.item(j);

                    if (itemLecturer.getNodeType() == Node.ELEMENT_NODE) {
                        Lecturer lecturer = new Lecturer();
                        Element lecturerElement = (Element) itemLecturer;

                        lecturer.setId(lecturerElement.getAttribute("id"));
                        lecturer.setName(lecturerElement.getElementsByTagName("credentials").item(0).getTextContent());
                        lecturer.setDegree(lecturerElement.getElementsByTagName("degree").item(0).getTextContent());
                        lecturer.setFieldsOfStudy(lecturerElement.getElementsByTagName("fields").item(0).getTextContent());

                        if(!result.contains(lecturer))
                            result.add(lecturer);
                    }
                }
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
                for(int j = 0; j < practicantList.getLength(); j++){
                    Node itemPracticant = practicantList.item(j);

                    if(itemPracticant.getNodeType() == Node.ELEMENT_NODE){
                        Practicant practicant = new Practicant();

                        Element practicantElement = (Element) itemPracticant;

                        practicant.setId(practicantElement.getAttribute("id"));
                        practicant.setName(practicantElement.getElementsByTagName("credentials").item(0).getTextContent());
                        practicant.setDegree(practicantElement.getElementsByTagName("degree").item(0).getTextContent());
                        practicant.setFieldsOfStudy(practicantElement.getElementsByTagName("fields").item(0).getTextContent());

                        if(!result.contains(practicant))
                            result.add(practicant);
                    }
                }
            }
        }

        return result;
    }


}
