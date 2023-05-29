package gameoflife;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Objects;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLGenerator {

  public static final String INTERNAL_CONFIGURATION = "gameoflife.text";
  private static final String TEMPLATE = "/template/Sample1.xml";
  public static void generateXMLFile(File file, Properties simInfo, Properties simParams) {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    try (InputStream is = (Objects.requireNonNull(Main.class.getResourceAsStream(TEMPLATE)))) {
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(is);

      String[] xmlElements = {"title", "author", "description", "width", "height", "cells", "simtype"};
      for (String element : xmlElements) {
        Node xmlElement = doc.getElementsByTagName(element).item(0);
        xmlElement.setTextContent(simInfo.getProperty(element));
      }
      Node parametersElement=doc.getElementsByTagName("parameters").item(0);
      Enumeration e = simParams.propertyNames();
      while (e.hasMoreElements()) {
        String key = (String) e.nextElement();
        Element newParam=doc.createElement(key);
        newParam.setTextContent(simParams.getProperty(key));
        parametersElement.appendChild(newParam);
      }
      transformDocumentToFile(doc, file);
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static void transformDocumentToFile(Document doc, File file) {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(file);
      transformer.transform(source, result);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }
}
