import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

/**
* Java Program to parse/read HTML documents from File using Jsoup library.
* Jsoup is an open source library which allows Java developer to parse HTML
* files and extract elements, manipulate data, change style using DOM, CSS and
* JQuery like method.
*
* @author Javin Paul
*/
public class HTMLParser{

    public static void main(String args[]) {

        Document doc;
        List<TextNode> insideNodes;
        try {
            doc = Jsoup.connect("http://www.lamantin-kafe.ru/lamantin-menu-bistro-obuhov/").get();
            Elements eles = doc.select("tbody > tr > td ");

            for (Element element : eles) {
                for (TextNode node : element.textNodes()) {
                    node.text();
                    System.out.println(node.text());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}