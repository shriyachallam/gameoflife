package gameoflife;

import gameoflife.Model.ForagingAnt.ForagingAntSimulator;
import gameoflife.Model.GameOfLife.ConwaySimulator;
import gameoflife.Model.Percolation.PercolateSimulator;
import gameoflife.Model.SchellingSegregation.SegregateSimulator;
import gameoflife.Model.Simulator;
import gameoflife.Model.SpreadingOfFire.FireSimulator;
import gameoflife.Model.Tumor.TumorSimulator;
import gameoflife.Model.WaTor.WaTorSimulator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class SimulationGenerator {
    public static Simulator generateSimulationFromXML(File xmlFile){
        String width;
        String height;
        String simtype;
        String author;
        String title;
        String description;
        String cellShape;
        List<List<String>> cells = new ArrayList<>();
        Properties paramProps = new Properties();

        try {
            Document xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xmlFile);
            Element root = xmlDocument.getDocumentElement();
            width = getTextValue(root, "width");
            height = getTextValue(root, "height");
            cellShape = getTextValue(root, "cellShape");
            simtype = getTextValue(root, "simtype");
            NodeList parameterNodes=root.getElementsByTagName("parameters");
            if(parameterNodes.getLength()>0) {
                Node parameterNode = parameterNodes.item(0);
                NodeList parameterList=parameterNode.getChildNodes();
                for (int j = 0; j < parameterList.getLength(); j++) {
                    Node param = parameterList.item(j);
                    String paramName = param.getNodeName();
                    if(!paramName.equals("#text")) {
                        String paramVal = param.getTextContent();
                        paramProps.setProperty(paramName, paramVal);
                    }
                }
            }
            author = getTextValue(root, "author");
            title = getTextValue(root, "title");
            description = getTextValue(root, "description");
            String[] cell_rows = getTextValue(root, "cells").split("\n");
            for (String cellRow : cell_rows) {
                List<String> cell_rows_arraylist = Arrays.asList(cellRow.split(" "));
                cells.add(cell_rows_arraylist);
            }
        }
        catch (ParserConfigurationException e) {
            throw new IllegalArgumentException("Invalid XML Configuration");
        }
        catch (SAXException | IOException e) {
            throw new IllegalArgumentException("Invalid XML Data");
        }

        if (simtype.equals("") || height.equals("") || width.equals("") || title.equals("") || author.equals("") || description.equals("")) {
            throw new IllegalArgumentException("One or more argument is missing");
        }
        return constructSimulation(simtype, height, width, cells, paramProps, title,author,description, cellShape);
    }

    public static Simulator constructSimulation(String simtype, String height, String width, List<List<String>> cells, Properties paramProps, String title, String author, String description, String cellShape){
        int grid_height = Integer.parseInt(height);
        int grid_width = Integer.parseInt(width);

        if (cells.size() > grid_height|| cells.get(0).size() > grid_width) {
            throw new IllegalArgumentException("Number of cells exceeds the bounds of the grid.");
        }

        if (cellShape.equals("")) {
            cellShape = "Rectangle";
        }
        if (cellShape.equals("Circle") && grid_height != grid_width) {
            throw new IllegalArgumentException("Height and width of circle must be equal");
        }
        Simulator simulation = switch (simtype) {
            case "ForagingAnt" -> new ForagingAntSimulator(grid_height, grid_width, cells, paramProps, cellShape);
            case "Conway" -> new ConwaySimulator(grid_height, grid_width, cells,cellShape);
            case "Segregate" -> new SegregateSimulator(grid_height, grid_width, cells, paramProps, cellShape);
            case "Percolate" -> new PercolateSimulator(grid_height, grid_width, cells, cellShape);
            case "WaTor" -> new WaTorSimulator(grid_height, grid_width, cells, paramProps, cellShape);
            case "Tumor" -> new TumorSimulator(grid_height, grid_width, cells, paramProps, cellShape);
            default -> new FireSimulator(grid_height, grid_width, cells, paramProps, cellShape);
        };
        simulation.setInfo(title, author, description, simtype, cellShape);
        return simulation;
    }
    private static String getTextValue(Element e, String tagName) {
        NodeList nodeList = e.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        else {
            // FIXME: empty string or exception? In some cases it may be an error to not find any text
            return "";
        }
    }

}
