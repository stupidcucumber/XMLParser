package Windows;

import Items.Item;
import Items.StudyClass;
import Parser.DOM.CustomDOMParser;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Test {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        CustomDOMParser customDOMParser = new CustomDOMParser();

        for (Item item : customDOMParser.parse("classes"))
            System.out.println((StudyClass) item);
    }
}
