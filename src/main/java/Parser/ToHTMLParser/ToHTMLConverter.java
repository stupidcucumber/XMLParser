package Parser.ToHTMLParser;

import Items.Item;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ToHTMLConverter implements ConverterToHTML {
    public void convert(List<Item> itemList, String specification) {
        ToXMLParser toXMLParser = new ToXMLParser();
        toXMLParser.parse(itemList, specification);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        File template;
        if(specification.equals("classes"))
            template = new File("src/main/resources/facultyClasses.xsl");
        else
            template = new File("src/main/resources/facultyScientists.xsl");
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer(new StreamSource(template));
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }

        StreamSource streamSource = new StreamSource("queryXML.xml");
        File result = new File("query.html");
        if(!result.exists()) {
            try {
                result.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        StreamResult streamResult = new StreamResult(result);

        try {
            transformer.transform(streamSource, streamResult);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Done.");
    }
}
