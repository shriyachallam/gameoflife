package gameoflife.View;

import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
/**
 * This class creates an info box that allows the user to change the title, author, and description of the simulation
 * @author Sophie
 */
public class InfoBox extends GridPane {
  public static final String INTERNAL_CONFIGURATION = "gameoflife.text";
  private final ArrayList<TextField> myUserInputFields = new ArrayList<>();

  /**
   * This method creates the info box
   * @param title   the title of the simulation
   * @param author  the author of the simulation
   * @param description    the description of the simulation
   * @param simType     the type of simulation
   */
  public InfoBox(String title, String author, String description, String simType) {
    this.setId("infobox");
    ResourceBundle myResources = ResourceBundle.getBundle(INTERNAL_CONFIGURATION);
    Label titleLabel=new Label(myResources.getString("simInfo"));
    titleLabel.setId("titleLabel");
    this.add(titleLabel, 0,0);
    setColumnSpan(titleLabel,2);

    Label simTypeLabel = new Label(myResources.getString("simType")+simType);
    this.add(simTypeLabel,0,1);
    setColumnSpan(simTypeLabel,2);

    generateInfoTextfield(myResources.getString("simTitle"), "title", title, 2);
    generateInfoTextfield(myResources.getString("simAuthor"), "author", author, 3);
    generateInfoTextfield(myResources.getString("simDescription"), "description", description, 4);
  }

  private void generateInfoTextfield(String label, String id, String input, int rowIndex) {
    Label textfieldLabel = new Label(label);
    TextField textfieldInput = new TextField(input);
    textfieldInput.setId(id);
    this.add(textfieldLabel, 0, rowIndex);
    this.add(textfieldInput, 1, rowIndex);
    myUserInputFields.add(textfieldInput);
  }

  /**
   * This method retrieves the info from the info box
   * @return  the info from the info box
   */
  public Properties retrieveInfo() {
    Properties infoProperties = new Properties();
    for (TextField field : myUserInputFields) {
      infoProperties.setProperty(field.getId(), field.getText());
    }
    return infoProperties;
  }
}
