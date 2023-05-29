package gameoflife.View;

import java.io.File;
import javafx.stage.FileChooser;
/**
 * This class creates a file chooser that is used to select the data file for the simulation
 * @author Sophie
 */
public class SimFileChooser {
  private static final String DATA_FILE_EXTENSION = "*.xml";
  private static final String DATA_FILE_FOLDER = System.getProperty("user.dir") + "/data";

  /**
   * This method creates the file chooser
   * @return  the file chooser
   */
  protected static FileChooser makeChooser() {
    FileChooser result = new FileChooser();
    result.setTitle("Open Data File");
    result.setInitialDirectory(new File(DATA_FILE_FOLDER));
    result.getExtensionFilters()
        .setAll(new FileChooser.ExtensionFilter("Data Files", DATA_FILE_EXTENSION));
    return result;
  }

}
