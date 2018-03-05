import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class RestaurantSimulation {

  
  public static void main(String[] args) {
  String fileName = "Workers.txt";
      File file = new File(fileName);
      ArrayList<IWorker> workers = new ArrayList<>();
      try {
          Scanner line = new Scanner(file);
          while (line.hasNextLine()) {
              String tableLine = line.nextLine();
              String[] splitString = tableLine.split("\\|");
              if (splitString[0].equals("Server")){
                  workers.add(new Server(splitString[1],));
              }
              else if(splitString[0].equals("Manager")){}
              else if(splitString[0].equals("Cook")){}



          }
          line.close();
      } catch (FileNotFoundException e) {
          e.printStackTrace();
      }
  }
}
