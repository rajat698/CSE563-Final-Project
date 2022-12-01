import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This Attendance class consists of functionalities such as uploading one
 * or more attendance files, parsing the attendance files, and map the attendees
 * to the previously uploaded roster.
 */
public class Attendance extends JFileChooser {
    private JFileChooser fileChooser;
    private Map<String, Student> studentMap;
    private static Map<String, Map<String, Integer>> asuriteMissingInRoster;
    private static int columnNum;

    /**
     * Default constructor of Attendance
     * @param students list of Student Objects received from the roster.
     */
    public Attendance(List<Student> students) {
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(true);

        asuriteMissingInRoster = new HashMap<>();
        if (students != null)
            studentMap = convertListToMap(students);
        columnNum = 4;
    }

    /**
     * This method is used to upload one or more attendance files and parse them.
     * @param  datesMap key value pair of date with its corresponding columnNumber
     * @throws IOException
     */
    public void loadAttendanceData(Map<String, Integer> datesMap) throws IOException {
        int result = fileChooser.showOpenDialog(getParent());
        if (result == JFileChooser.APPROVE_OPTION) {
            File[] files = fileChooser.getSelectedFiles();
            for (File file : files) {
                String fileName = file.getName();
                String filePath = file.getAbsolutePath();

                if (!fileName.endsWith(".csv"))
                    throw new IOException("Invalid file format");

                if (fileName.length() < 8)
                    throw new IOException("File name invalid for file: " + fileName);

                String date = convertToDateFmt(fileName.substring(0, 8));
                if (date == null)
                    throw new IOException("Date format invalid for file: " + fileName);

                if (!datesMap.containsKey(date)) {
                    datesMap.put(date, columnNum);
                    columnNum++;
                }

                loadFileData(filePath, date);
            }
        }
    }

    /**
     * This is a helper method to convert a list of Student object to a hashmap.
     * @param students list of Student object
     * @return a key value pair of student's asurite id mapped to Student's object
     */
    private Map<String, Student> convertListToMap(List<Student> students) {
        Map<String, Student> studentMap = new HashMap<>();
        for (Student student : students)
            studentMap.put(student.getASURITE(), student);
        return studentMap;
    }

    /**
     * This Attendance class consists of functionalities such as uploading one
     * or more attendance files, parsing the attendance files, and map the attendees
     * to the previously uploaded roster.
     * @param unformattedDate date of the format YYYYMMDD
     * @return String date of the format MM/DD/YYYY
     */
    private String convertToDateFmt(String unformattedDate) {
        if (unformattedDate.length() != 8)
            return null;

        int year = Integer.parseInt(unformattedDate.substring(0, 4));
        int month = Integer.parseInt(unformattedDate.substring(4, 6));
        int day = Integer.parseInt(unformattedDate.substring(6, 8));

        if (month < 1 || month > 12 || day < 1 || day > 31)
            return null;

        return month+"/"+day+"/"+year;
    }

    /**
     * This methods has parses an attendance file, and map the attendees
     * to the previously uploaded roster. It also stores the students who are
     * not present in the roster.
     * @param filePathString path of the file to read
     * @param date attendance file date
     */
    private void loadFileData(String filePathString, String date) {
        String line = "";
        String delimiter = ",";

        try  {
            BufferedReader br = new BufferedReader(new FileReader(filePathString));
            while ((line = br.readLine()) != null) {
                String[] attendanceData= line.split(delimiter);
                String asurite = attendanceData[0];
                int time = Integer.parseInt(attendanceData[1]);

                if (!studentMap.containsKey(asurite)) {
                    asuriteMissingInRoster.putIfAbsent(date, new HashMap<>());
                    Map<String, Integer> studentAttendance = asuriteMissingInRoster.get(date);
                    studentAttendance.put(asurite, studentAttendance.getOrDefault(asurite, 0) + time);
                    asuriteMissingInRoster.put(date, studentAttendance);
                } else {
                    Student studentObj = studentMap.get(asurite);
                    Map<String, Integer> dateWiseAttendance = studentObj.getAttendance();
                    dateWiseAttendance.put(date, dateWiseAttendance.getOrDefault(date, 0) + time);
                    studentObj.setAttendance(dateWiseAttendance);
                }
            }

            br.close();
        } catch (IOException e)  {
            e.printStackTrace();
        }
    }
}
