package Parser.SAX;

import Items.Item;
import Parser.Parser;
import Parser.SAX.SAXHandler;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;
import Parser.Setting;
import Parser.Validator;

public class CustomSAXParser implements Parser {
    @Override
    public List<Item> parse(String value, Setting setting) {
        List<Item> lecturers;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = saxParserFactory.newSAXParser();
            SAXHandler handler = new SAXHandler(value, new Validator(setting));

            parser.parse(PATH, handler);
            lecturers = handler.getItems();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        return lecturers;
    }
}
