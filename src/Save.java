import java.io.FileWriter;
import java.io.IOException;

public class Save {
    public void saveFile (String[][] data, String[] headers) {
        try {
            FileWriter fileWriter = new FileWriter("SaveAttendance.csv");

            for (int i = 0; i < headers.length - 1; i++) {
                fileWriter.append(headers[i]);
                fileWriter.append(",");
            }
            fileWriter.append(headers[headers.length - 1]);
            fileWriter.append("\n");

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length - 1; j++) {
                    fileWriter.append(data[i][j]);
                    fileWriter.append(",");
                }
                fileWriter.append(data[i][data[i].length - 1]);
                fileWriter.append("\n");
            }
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}