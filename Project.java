import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Project {

    public int number_of_jobs;
    public int number_of_nondummy_jobs;

    public int number_of_renewable_resources;
    public List<Node> nodes;
    public Node start_node;
    public Node finish_node;
    public List<Integer> renewable_resource_availabilities;
    public String instance_name;

    public Project() {
    }

    public void read_project(String filename) throws FileNotFoundException {
        Path current_path = Paths.get(System.getProperty("user.dir"));
        Path instance_path = Paths.get(current_path.toString(), "instances", filename);
        Scanner scanner = new Scanner(new File(instance_path.toString()));

        String current_line = scanner.nextLine();
        Scanner line_scanner = new Scanner(current_line);
        number_of_jobs = line_scanner.nextInt();
        number_of_nondummy_jobs = number_of_jobs - 2;

        nodes = new ArrayList<>();

        for (int i = 0; i < this.number_of_jobs; i++) {
            Node new_node = new Node();
            new_node.name = i;
            nodes.add(new_node);
        }

        start_node = nodes.get(0);
        finish_node = nodes.get(nodes.size() - 1);

        number_of_renewable_resources = line_scanner.nextInt();

        current_line = scanner.nextLine();
        line_scanner = new Scanner(current_line);

        this.renewable_resource_availabilities = new ArrayList<>();

        for (int i = 0; i < number_of_renewable_resources; i++) {
            renewable_resource_availabilities.add(line_scanner.nextInt());
        }

        int i = 0;

        while (scanner.hasNext()) {

            Node current_node = nodes.get(i);
            current_node.duration = scanner.nextInt();
            current_node.renewable_resource_requirements = new ArrayList<>();

            for (int j = 0; j < number_of_renewable_resources; j++) {
                current_node.renewable_resource_requirements.add(scanner.nextInt());
            }

            int no_of_successors = scanner.nextInt();
            current_node.successors = new ArrayList<>();
            current_node.predecessors = new ArrayList<>();

            for (int j = 0; j < no_of_successors; j++) {
                int succ_name = scanner.nextInt() - 1;
                Node succ = nodes.get(succ_name);
                current_node.successors.add(succ);
            }

            i++;
        }


        // set predecessors:
        for (Node n : nodes) {
            for (Node s : n.successors) {
                s.predecessors.add(n);
            }
        }
    }

    public void calculate_cpm() {
        start_node.earliest_start = 0;
        start_node.earliest_finish = start_node.earliest_start + start_node.duration;

        for (Node node : nodes) {
            if (node == start_node) {
            } else {
                node.earliest_start = Collections.max(node.predecessors.stream().map(pred -> pred.earliest_finish).toList());
                node.earliest_finish = node.earliest_start + node.duration;
            }
        }

    }
}
