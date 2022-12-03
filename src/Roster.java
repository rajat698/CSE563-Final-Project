import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JFileChooser;

/**
+ * This Roster class consists of functionalities such as uploading a
+ * roster file, parsing the roster file, and visualizing the roster data
+ * on the screen.
+ */
public class Roster extends JFileChooser {

    private JFileChooser fileChooser = new JFileChooser();
    JTable table;
    List<Student> students;
    String[][] returndata;
    String[] headers;

    /**
    * This  method is used to add the functionality of File
    * Chooser.
    * @param table is the table used to display information 
    * of roster.
    * @return List of Student objects.
    */
    public List<Student> loadRosterData(JTable table) {

        this.table = table;
        int result = fileChooser.showOpenDialog(getParent());

        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String filePath = selectedFile.getAbsolutePath();

            if(fileName.endsWith(".csv")) {
                return loadFileData(filePath);
            }
            else 
                return null;
        }

        return null;
    }

    /**
    * This is a helper method which is used to parse the roster data.
    * @param filePathString is the path of the file selected to upload.
    * @return List of Student objects.
    */
    public List<Student> loadFileData(String filePathString) {

        String line = "";  
        String splitBy = ","; 
        students = new ArrayList<>();

        try  {  
            BufferedReader br = new BufferedReader(new FileReader(filePathString));  
            while ((line = br.readLine()) != null) {
                String[] studentInfo = line.split(splitBy);
                Student student = new Student(studentInfo[0],studentInfo[1],studentInfo[2],studentInfo[3]);
                students.add(student);
            }
            br.close();
        }   
        catch (IOException e)  {  
            e.printStackTrace();  
        }    

        return students;
    }

    /**
    * This method is used to visualize the roster data into a table
    * format.
    * @param students is the List of Student objects.
    * @param numCol is the number of columns to display in the table.
    * @param columnNames are the names of columns in the table.
    * @param columnMap is the mapping of date with their corresponding
    * column number.
    * @return a scrollpane
    */
    public JScrollPane visualizeRoster(List<Student> students, int numCol, String[] columnNames,
                                       Map<String, Integer> columnMap) {
        String[][] data = new String[students.size()][numCol];

        for(int i= 0;i < students.size(); ++i) {
            String[] info = new String[numCol];
            Student studentObj = students.get(i);
            info[0] = studentObj.getID();
            info[1] = studentObj.getFirstName();
            info[2] = studentObj.getLastName();
            info[3] = studentObj.getASURITE();

            Map<String, Integer> entry = studentObj.getAttendance();
            for (Map.Entry<String, Integer> columns : columnMap.entrySet()) {
                int columnNum = columns.getValue();
                if (entry.containsKey(columns.getKey()))
                    info[columnNum] = String.valueOf(entry.get(columns.getKey()));
                else
                    info[columnNum] = String.valueOf(0);
            }

            data[i] = info;
        }
        headers = columnNames;
        returndata = data;
        table = new JTable(data, columnNames);
        table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF);
     
        JScrollPane sp = new JScrollPane(table);
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        return sp;
    }

    /**
    * This method is used to get the data of the students.
    * @return a 2d-array of student information.
    */
    public String[][] getData() {
        return returndata;
    }

    /**
    * This method is used to get the column names of the
    * table.
    * @return an array of column names
    */
    public String[] getHeaders() {
        return headers;
    }
}