import java.util.List;

public class Node {

    public int name;
    public int duration;
    public int earliest_start;
    public int earliest_finish;

    public List<Node> predecessors;
    public List<Node> successors;

    public List<Integer> renewable_resource_requirements;

    public Node() {
    }
}
