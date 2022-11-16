package Parser;

import Items.Item;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface Parser {
    String PATH = "src/main/resources/faculty.xml";
    List<Item> parse(String value, Setting setting) throws ParserConfigurationException, IOException, SAXException;
}
