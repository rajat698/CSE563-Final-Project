import java.io.FileWriter;
import java.io.IOException;

/**
* This Save class consists of methods to save the attendance
* file.
*/
public class Save {

    /**
     * @param data contains all the data of the roster
     * @param headers contains the headers.
     * Saves headers as well as its data in the CSV file.
     * All the details will be saved in "SaveAttendance.csv" after save button is clicked.
     * @return void
     */
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
                    String val = "0";
                    if (data[i][j] != null)
                        val = data[i][j];

                    fileWriter.append(val);
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