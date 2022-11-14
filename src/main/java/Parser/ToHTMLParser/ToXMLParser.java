package Parser.ToHTMLParser;

import Items.Item;
import Items.Scientist;
import Items.StudyClass;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.List;

public class ToXMLParser {
    private Document parsedXML;

    public void parse(List<Item> items, String specification){
        try {
            marshall(items, specification);
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void marshall(List<Item> items, String specification) throws ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        parsedXML = documentBuilder.newDocument();

        switch (specification){
            case "classes" -> marshallClasses(items);
            case "scientists" -> marshallScientist(items);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

        Writer output = new StringWriter();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(parsedXML), new StreamResult(output));

        File file = new File("queryXML.xml");
        if(!file.exists())
            file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        PrintWriter printWriter = new PrintWriter(fileOutputStream);

        printWriter.println(output.toString());
        printWriter.flush();

        fileOutputStream.close();
    }

    private void marshallClasses(List<Item> items){
        Element classes = parsedXML.createElement("classes");
        for(int i = 0; i < items.size(); i++){
            StudyClass studyClass = (StudyClass) items.get(i);

            Element studyClassElement = parsedXML.createElement("class");

            Element classID = parsedXML.createElement("id");
            classID.setTextContent(studyClass.getId());
            studyClassElement.appendChild(classID);

            Element className = parsedXML.createElement("name");
            className.setTextContent(studyClass.getName());
            studyClassElement.appendChild(className);

            Element classDescription = parsedXML.createElement("description");
            classDescription.setTextContent(studyClass.getDescription());
            studyClassElement.appendChild(classDescription);

            Element classLecturers = parsedXML.createElement("lecturers");
            classLecturers.setTextContent("lecturers");
            studyClassElement.appendChild(classLecturers);

            Element classPracticants = parsedXML.createElement("practicants");
            classPracticants.setTextContent("practicants");
            studyClassElement.appendChild(classPracticants);

            classes.appendChild(studyClassElement);
        }

        parsedXML.appendChild(classes);
    }

    private void marshallScientist(List<Item> items){
        Element scientists = parsedXML.createElement("scientists");
        for(int i = 0; i < items.size(); i++){
            Scientist scientist = (Scientist) items.get(i);

            Element scientistElement = parsedXML.createElement("scientist");

            Element scientistId = parsedXML.createElement("id");
            scientistId.setTextContent(scientist.getId());
            scientistElement.appendChild(scientistId);

            Element scientistName = parsedXML.createElement("name");
            scientistName.setTextContent(scientist.getName());
            scientistElement.appendChild(scientistName);

            Element scientistDegree = parsedXML.createElement("degree");
            scientistDegree.setTextContent(scientist.getDegree());
            scientistElement.appendChild(scientistDegree);

            Element scientistFields = parsedXML.createElement("fields");
            scientistFields.setTextContent(scientist.getFieldsOfStudy());
            scientistElement.appendChild(scientistFields);

            scientists.appendChild(scientistElement);
        }

        parsedXML.appendChild(scientists);
    }
}