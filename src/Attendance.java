import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
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
    private Map<String, Integer> datewiseStudents;

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
        datewiseStudents = new HashMap<>();
    }

    /**
     * This method is used to return the date-wise count of students who attended the class.
     * @return Map<String, Integer> datewise count of students, present in roster, that attended the class
     */
    public Map<String, Integer> getDatewiseStudents() {
        return datewiseStudents;
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
            displayAttendanceResult(asuriteMissingInRoster);
            asuriteMissingInRoster = new HashMap<>();
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

                    /** Find date-wise student count*/
                    if (!dateWiseAttendance.containsKey(date))
                        datewiseStudents.put(date, datewiseStudents.getOrDefault(date, 0) + 1);

                    dateWiseAttendance.put(date, dateWiseAttendance.getOrDefault(date, 0) + time);
                    studentObj.setAttendance(dateWiseAttendance);
                }
            }
            br.close();
        } catch (IOException e)  {
            e.printStackTrace();
        }
    }

    /**
     * Displays the result of adding the attendance data to the roster. Tells how many students the
     * data was loaded for, and if additional attendees were found.
     *
     * @param asuriteMissingInRoster
     */
    public void displayAttendanceResult(Map<String, Map<String, Integer>> asuriteMissingInRoster) {
        JDialog dialog = new JDialog(new JFrame(), "Attendance Results");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        JScrollPane pane = new JScrollPane(panel);
        pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        if (!asuriteMissingInRoster.isEmpty()) {
            for (Map.Entry<String, Map<String, Integer>> e : asuriteMissingInRoster.entrySet()) {
                if (e.getValue().size()  == 0)
                    continue;

                int num = 0;
                if (datewiseStudents.get(e.getKey()) != null)
                    num = datewiseStudents.get(e.getKey());

                JLabel label = new JLabel("On " + e.getKey() +
                        ", data loaded for "+num+" students",
                        SwingConstants.CENTER);
                panel.add(label);
                panel.add(new JLabel(e.getValue().size() + " additional attendee(s) were found:", SwingConstants.CENTER));
                JTable table = addTable(e.getValue());
                JTableHeader header = table.getTableHeader();
                header.setSize(panel.getWidth(), 15);
                header.setBackground(Color.white);
                panel.add(header);
                panel.add(table);
            }
        }

        dialog.add(pane);
        dialog.setLocationRelativeTo(null);
        dialog.setSize(600, 400);
        dialog.setVisible(true);
    }


    /**
     * This method holds logic to create JTable with Students asurite id and time of meeting.
     * @param asuriteMissingInRoster key value pair of students' asurite id and time of meeting
    */
    private JTable addTable(Map<String, Integer> asuriteMissingInRoster) {
        String[] columnNames = new String[]{"Asurite Id", "Time Joined"};
        String[][] data = new String[asuriteMissingInRoster.size()][columnNames.length];
        int i = 0;
        for(Map.Entry<String, Integer> entry : asuriteMissingInRoster.entrySet()) {
            String[] info = new String[2];
            info[0] = entry.getKey();
            info[1] = String.valueOf(entry.getValue());
            data[i++] = info;
        }

        JTable table = new JTable(data, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        return table;
    }
}
