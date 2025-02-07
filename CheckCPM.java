import java.io.FileNotFoundException;

public class CheckCPM {


    public static void main(String[] args) throws FileNotFoundException {
        Project project = new Project();
        project.read_project("LPP_1.rcp");
        project.calculate_cpm();

        System.out.println("CPM: " + project.finish_node.earliest_finish);
    }
}
