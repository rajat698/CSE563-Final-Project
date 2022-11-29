import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Attendance extends JFileChooser {
    private JFileChooser fileChooser = new JFileChooser();
    private Map<String, Map<Student, Integer>> dateMap;
    private Map<String, Student> studentMap;
    private static Map<String, List<String>> asuriteMissingInRoster;

    public Attendance(List<Student> students) {
        asuriteMissingInRoster = new HashMap<>();
        if (students != null)
            studentMap = convertListToMap(students);

        dateMap =  new HashMap<>();
    }

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

            dateMap.put(date, loadFileData(filePath, date));

            System.out.println(dateMap);
        }
    }

    private Map<String, Student> convertListToMap(List<Student> students) {
        Map<String, Student> studentMap = new HashMap<>();
        for (Student student : students)
            studentMap.put(student.getASURITE(), student);
        return studentMap;
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

    private Map<Student, Integer> loadFileData(String filePathString, String date) {
        String line = "";
        String delimiter = ",";
        Map<Student, Integer> attendanceMap = new HashMap<>();

        try  {
            BufferedReader br = new BufferedReader(new FileReader(filePathString));
            while ((line = br.readLine()) != null) {
                String[] attendanceData= line.split(delimiter);
                String asurite = attendanceData[0];
                int time = Integer.parseInt(attendanceData[1]);

                if (!studentMap.containsKey(asurite)) {
                    asuriteMissingInRoster.putIfAbsent(date, new ArrayList<>());
                    asuriteMissingInRoster.get(date).add(asurite);
                } else {
                    Student studentObj = studentMap.get(asurite);
                    attendanceMap.put(studentObj, attendanceMap.getOrDefault(studentObj, 0) + time);
                }
            }
            br.close();
        } catch (IOException e)  {
            e.printStackTrace();
        }

        return attendanceMap;
    }
}
