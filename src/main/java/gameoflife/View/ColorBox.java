package gameoflife.View;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Callback;


/**
 * This class creates a color box that allows the user to change the colors of the cells in the simulation
 * @author Sophie
 */
public class ColorBox extends GridPane {
  public static final String INTERNAL_CONFIGURATION = "gameoflife.text";
  private final ResourceBundle myResources = ResourceBundle.getBundle(INTERNAL_CONFIGURATION);
  private final Properties colorParams;
  private final Callback<Void,Void> updateGridCallback;
  ColorBox(Properties colorParams, Callback<Void, Void> updateGridCallback) {
    this.updateGridCallback=updateGridCallback;
    this.setId("infobox");
    Label titleLabel=new Label(myResources.getString("simColors"));
    titleLabel.setId("titleLabel");
    this.add(titleLabel, 0,0);
    setColumnSpan(titleLabel,2);

    this.colorParams=colorParams;
    Enumeration<?> e = colorParams.propertyNames();
    int row=1;
    while (e.hasMoreElements()) {
      String key = (String) e.nextElement();
      generateColorPicker(myResources.getString(key), colorParams.getProperty(key), row);
      row++;
    }
  }

  private void generateColorPicker(String key, String val, int rowIndex) {
    Label textfieldLabel = new Label(key);
    Color initialColor = Color.web(val);
    ColorPicker colorPicker=new ColorPicker(initialColor);
    colorPicker.setId(key);
    colorPicker.setOnAction(event -> handleColorChange(colorPicker));
    this.add(textfieldLabel, 0, rowIndex);
    this.add(colorPicker, 1, rowIndex);
  }

  private void handleColorChange(ColorPicker colorPicker){
    Color color= colorPicker.getValue();
    String hexColor = colorToHex(color);
    colorParams.setProperty(colorPicker.getId(),hexColor);
    updateGridCallback.call(null);
  }

  private String colorToHex(Color color) { //from https://stackoverflow.com/questions/17925318/how-to-get-hex-web-string-from-javafx-colorpicker-color
      String hex1;
      String hex2;

      hex1 = Integer.toHexString(color.hashCode()).toUpperCase();

      switch (hex1.length()) {
        case 2:
          hex2 = "000000";
          break;
        case 3:
          hex2 = String.format("00000%s", hex1.substring(0,1));
          break;
        case 4:
          hex2 = String.format("0000%s", hex1.substring(0,2));
          break;
        case 5:
          hex2 = String.format("000%s", hex1.substring(0,3));
          break;
        case 6:
          hex2 = String.format("00%s", hex1.substring(0,4));
          break;
        case 7:
          hex2 = String.format("0%s", hex1.substring(0,5));
          break;
        default:
          hex2 = hex1.substring(0, 6);
      }
      return hex2;
    }

}
