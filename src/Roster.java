import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JFileChooser;


public class Roster extends JFileChooser{

    private JFileChooser fileChooser = new JFileChooser();
    JTable table;
    List<Student> students;

    public List<Student> loadRosterData(JTable table) {

        this.table = table;
        int result = fileChooser.showOpenDialog(getParent());

        if (result == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String filePath = selectedFile.getAbsolutePath().toString();

            if(fileName.endsWith(".csv")) {
                return loadFileData(filePath);
            }
            else 
                return null;
        }

        return null;
    }

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

    public JScrollPane visualizeRoster(List<Student> students) {

        String[][] data = new String[students.size()][4];

        for(int i=0;i<students.size();i++) {
            String[] info = new String[4];
            info[0] = students.get(i).getID();
            info[1] = students.get(i).getFirstName();
            info[2] = students.get(i).getLastName();
            info[3] = students.get(i).getASURITE();

            data[i] = info;
        }
     
        // Column Names
        String[] columnNames = { "ID", "First Name", "Last Name","ASURITE"};
     
        table = new JTable(data, columnNames);
        table.setBounds(30, 40, 200, 300);
     
        JScrollPane sp = new JScrollPane(table);
        sp.setPreferredSize(new Dimension(600, 600));
        sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        return sp;
    }
}
