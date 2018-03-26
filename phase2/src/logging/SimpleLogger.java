package logging;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleLogger {

    String loggerDestination;

    /**
     * SimpleLogger constructor.
     *
     * @param filePath filePath for the logger.
     */
    public SimpleLogger(String filePath) {
        this.loggerDestination = filePath;
    }


    /**
     * Creates the logger file w/ date
     */
    public void createAndSetNewLoggerFile(String filePath, String namePrefix) {
        String pattern = "dd-MM-yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        String name = filePath + "/" + namePrefix + dateInString + ".txt";

        File file = new File(name);
        setLoggerDestination(name);


        try {
            PrintWriter writer = new PrintWriter(name, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Writes a message to the log.
     *
     * @param content information being written to the logger.
     */
    public void writeToLogger(String content) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.loggerDestination, true));
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
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(loggerDestination)))) {
            String line;
            while ((line = reader.readLine()) != null)
                info += line + System.lineSeparator();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * Setter for the filePath
     *
     * @param filePath filePath of the logger.
     */
    public void setLoggerDestination(String filePath) {
        this.loggerDestination = filePath;
    }

    /**
     * Getter for the filePath
     *
     * @return filePath of the logger.
     */
    public String getLoggerDestination() {
        return this.loggerDestination;
    }

}
