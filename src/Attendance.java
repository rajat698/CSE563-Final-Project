import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Attendance extends JFileChooser {
    private JFileChooser fileChooser = new JFileChooser();
    private Map<String, Map<String, Integer>> map = new HashMap<>();
    public void loadAttendanceData() throws IOException {
        int result = fileChooser.showOpenDialog(getParent());

        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String filePath = selectedFile.getAbsolutePath();

            if(!fileName.endsWith(".csv"))
                throw new IOException("Invalid file format");

            if (fileName.length() < 8)
                throw new IOException("File name invalid for file: "+fileName);

            String date = convertToDateFmt(fileName.substring(0, 8));
            if (date == null)
                throw new IOException("Date format invalid for file: "+fileName);

            map.put(date, loadFileData(filePath, fileName));
        }

    }

    private String convertToDateFmt(String unformattedDate) {
        if (unformattedDate.length() != 8)
            return null;

        int year = Integer.parseInt(String.valueOf(unformattedDate.substring(0, 4)));
        int month = Integer.parseInt(unformattedDate.substring(4, 6));
        int day = Integer.parseInt(unformattedDate.substring(6, 8));

        if (month < 1 || month > 12 || day < 1 || day > 31)
            return null;

        return month+"/"+day+"/"+year;
    }

    private Map<String, Integer> loadFileData(String filePathString, String fileName) throws IOException {
        String line = "";
        String delimiter = ",";

        Map<String, Integer> attendanceMap = new HashMap<>();
        try  {
            BufferedReader br = new BufferedReader(new FileReader(filePathString));
            while ((line = br.readLine()) != null) {
                String[] attendanceData= line.split(delimiter);
                String asurite = attendanceData[0];
                int time = Integer.parseInt(attendanceData[1]);
                attendanceMap.put(asurite, attendanceMap.getOrDefault(asurite, 0) + time);
            }

            br.close();
        } catch (IOException e)  {
            e.printStackTrace();
        }

        return attendanceMap;
    }
}
