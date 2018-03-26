package logging;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleLogger {

    String loggerDestination;

    public SimpleLogger(String filePath) {
        this.loggerDestination = filePath;
    }

    public void setLoggerDestination(String filePath) {
        this.loggerDestination = filePath;
    }

    public String getLoggerDestination(){
        return this.loggerDestination;
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

    public void writeToLogger(String content) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.loggerDestination, true));
            writer.write(content);
            writer.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

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
}
