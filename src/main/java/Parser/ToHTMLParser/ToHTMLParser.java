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

public class ToHTMLParser {
    public void parse(List<Item> itemList, String specification) throws TransformerException, IOException {
        ToXMLParser toXMLParser = new ToXMLParser();
        toXMLParser.parse(itemList, specification);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        File template;
        if(specification.equals("classes"))
            template = new File("src/main/resources/facultyClasses.xsl");
        else
            template = new File("src/main/resources/facultyScientists.xsl");
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(template));

        StreamSource streamSource = new StreamSource("queryXML.xml");
        File result = new File("query.html");
        if(!result.exists())
            result.createNewFile();

        StreamResult streamResult = new StreamResult(result);

        transformer.transform(streamSource, streamResult);

        System.out.println("Done.");
    }
}
