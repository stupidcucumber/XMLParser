package Parser.ToHTMLParser;

import Items.Item;

import java.util.List;

public interface ConverterToHTML {
    void convert(List<Item> itemList, String specifier);
}
