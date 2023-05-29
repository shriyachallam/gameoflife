package gameoflife.View;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

/**
 * This class creates a parameter box that allows the user to change the parameters of the simulation
 * @author Sophie
 */
public class ParamBox extends GridPane {
  public static final String INTERNAL_CONFIGURATION = "gameoflife.text";
  private final ResourceBundle myResources = ResourceBundle.getBundle(INTERNAL_CONFIGURATION);
  private final ArrayList<Spinner> myParamSpinners = new ArrayList<>();
  private final Callback<Properties, Void> updateCallback;
  private final Button updateButton;

  /**
   * Constructor for the ParamBox class
   * @param simParams the parameters of the simulation
   * @param updateCallback the callback that is called when the user updates the parameters
   */
  public ParamBox(Properties simParams, Callback<Properties, Void> updateCallback) {
    this.setId("infobox");
    Label titleLabel=new Label(myResources.getString("simParams"));
    this.updateCallback=updateCallback;

    titleLabel.setId("titleLabel");
    this.add(titleLabel, 0,0);
    setColumnSpan(titleLabel,2);

    Enumeration<?> e = simParams.propertyNames();
    int row=1;
    while (e.hasMoreElements()) {
      String key = (String) e.nextElement();
      generateParamSpinner(key, simParams.getProperty(key), row);
      row++;
    }

    updateButton=new Button("update"); //TODO: Change text to something more "updateable"
    updateButton.setDisable(true);
    updateButton.setOnAction(event -> {
      handleParamUpdate();
      updateButton.setDisable(true);
    });
    this.add(updateButton, 1,row);

  }

  private void generateParamSpinner(String key, String val, int rowIndex) {
    Spinner paramSpinner;
    Label textfieldLabel = new Label(key);
    if(key.contains("Percent")){ //TODO: Find better way to do this!
      double initialVal= Double.parseDouble(val);
      paramSpinner=new Spinner<>(0,1,initialVal, 0.05);
    } else {
      int initialVal=Integer.parseInt(val);
      paramSpinner=new Spinner<>(0,Integer.MAX_VALUE, initialVal);
    }
    this.add(textfieldLabel, 0, rowIndex);
    this.add(paramSpinner, 1, rowIndex);
    paramSpinner.setId(key);
    paramSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
      updateButton.setDisable(false);
    });

    myParamSpinners.add(paramSpinner);
  }

  private void handleParamUpdate(){
    Properties paramProps=retrieveInfo();
    this.updateCallback.call(paramProps);
  }

  /**
   * Retrieves the information from the spinners
   * @return the properties of the spinners
   */
  public Properties retrieveInfo() {
    Properties paramProperties = new Properties();
    for (Spinner spinner : myParamSpinners) {
      paramProperties.setProperty(spinner.getId(), String.valueOf(spinner.getValue()));
    }
    return paramProperties;
  }

}
