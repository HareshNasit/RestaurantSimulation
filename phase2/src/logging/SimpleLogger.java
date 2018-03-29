package logging;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This allows the program to log actions. All the log messages get sent to a txt.
 */
public class SimpleLogger {

  protected String filePath;

  /**
   * SimpleLogger constructor.
   *
   * @param filePath filePath for the logger.
   */
  public SimpleLogger(String filePath) {
    this.filePath = filePath;
  }


  /**
   * Creates the logger file w/ date.
   */
  public void createAndSetNewLoggerFile(String filePath, String namePrefix) {
    String pattern = "dd-MM-yyyy";
    String dateInString = new SimpleDateFormat(pattern).format(new Date());
    String name = filePath + "/" + namePrefix + dateInString + ".txt";
    File file = new File(name);
    setFilePath(name);
  }

  /**
   * Writes a message to the log.
   *
   * @param content information being written to the logger.
   */
  public void writeToLogger(String content) {

    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(this.filePath, true));
      writer.write(content);
      writer.close();
    } catch (IOException e) {

      e.printStackTrace();
    }
  }

  /**
   * Returns a string with all the info from the logger.
   *
   * @return String with all info from the logger (separated by line).
   */
  public String returnAllContent() {
    String info = "";
    try (BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)))) {
      String line;
      while ((line = reader.readLine()) != null) {
        info += line + System.lineSeparator();
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    return info;
  }

  /**
   * Setter for the filePath.
   *
   * @param filePath filePath of the logger.
   */
  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  /**
   * Getter for the filePath.
   *
   * @return filePath of the logger.
   */
  public String getFilePath() {
    return this.filePath;
  }


  /**
   * Returns a string representation of the current Hour, Minute, and Second.
   */
  public String getTime() {
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    return "[" + sdf.format(cal.getTime()) + "]";
  }

}
