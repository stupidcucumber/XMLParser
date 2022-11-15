package Parser.ToHTMLParser;

import Items.Item;
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

public class CustomToHTMLConverter implements ConverterToHTML{

    private Document parsedXMLQuery;

    public void convert(List<Item> itemList, String specifier){
        try {
            marshall(itemList, specifier);
        } catch (ParserConfigurationException | TransformerException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void marshall(List<Item> itemList, String specifier) throws ParserConfigurationException, TransformerException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        parsedXMLQuery = builder.newDocument();

        switch (specifier){
            case "classes" -> marshallClass(itemList);
            case "scientists" -> marshallScientist(itemList);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

        Writer output = new StringWriter();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.transform(new DOMSource(parsedXMLQuery), new StreamResult(output));

        File file = new File("query.html");
        if(!file.exists())
            file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        PrintWriter printWriter = new PrintWriter(fileOutputStream);

        printWriter.println(output);
        printWriter.flush();

        fileOutputStream.close();
    }

    private void marshallClass(List<Item> items){
        Element html = parsedXMLQuery.createElement("html");

        Element head = parsedXMLQuery.createElement("head");
            Element title = parsedXMLQuery.createElement("title");
            title.setTextContent("Classes");
            head.appendChild(title);
        html.appendChild(head);

        Element body = parsedXMLQuery.createElement("body");
        for (Item item : items) {
            StudyClass studyClass = (StudyClass) item;

            Element header = parsedXMLQuery.createElement("h2");
            header.setTextContent(studyClass.getName());
            body.appendChild(header);

            Element paragraph = parsedXMLQuery.createElement("p");
            paragraph.setTextContent(studyClass.getDescription());
            body.appendChild(paragraph);

            Element lecturers = parsedXMLQuery.createElement("p");
            lecturers.setTextContent("Lecturers: " + studyClass.listOfLecturersToString());
            body.appendChild(lecturers);

            Element practicants = parsedXMLQuery.createElement("p");
            practicants.setTextContent("Practicants: " + studyClass.listOfPracticantsToString());
            body.appendChild(practicants);
        }

        html.appendChild(body);
        parsedXMLQuery.appendChild(html);
    }

    private void marshallScientist(List<Item> items){

    }
}
