import javafx.util.Pair;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * A Node represents a group of 2 houses at a point in the road.
 * This approach is suitable because houses across the road costs nothing
 * but going to neighbor house costs something. So it is better to convert
 * across facing houses as one group and join the 2 group houses with an edge.
 *
 * @author Ali Nawaz Maan <a.maan@uqconnect.edu.au>
 *
 */
public class Node {

    private String name;
    private double distanceFromSource = Integer.MAX_VALUE;
    private boolean visited = false;
    private ArrayList<Edge> edges = new ArrayList<>();
    private Pair<String, String> houses;
    private Node prevNodeInPath;
    private String roadName = "Road1";

    /**
     * Resetting the node for next query in the query file.
     * We don't have to construct the entire graph again and
     * set the nodes.
     * Just reset every node and we can run another query on the same graph.
     */
    public void resetNode() {

        this.distanceFromSource = Integer.MAX_VALUE;
        this.visited = false;
        this.prevNodeInPath = null;
    }

    /*
     Getter and setter methods for the Node - House group
     */
    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        if (houses == null) {
            houseGen(name);
        }
    }

    public Node getPrevNodeInPath() {
        return prevNodeInPath;
    }

    public void setPrevNodeInPath(Node prevNodeInPath) {
        this.prevNodeInPath = prevNodeInPath;
    }

    public double getDistanceFromSource() {
        return distanceFromSource;
    }

    public void setDistanceFromSource(double distanceFromSource) {
        this.distanceFromSource = distanceFromSource;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public String getHouse1() {
        return houses.getKey();
    }
    public String getHouse2() {
        return houses.getValue();
    }

    /**
     * House groups generator method. When a name for house group is passes as setName,
     * this method is called to generate the house numbers based on the group number.
     * Also, it gets the road name from the house group name and set the road name of house group.
     *
     * @param name String house group name
     */
    private void houseGen(String name) {

        try {
            int nodeNumber = (NumberFormat.getInstance().parse(name)).intValue();
            int length = String.valueOf(nodeNumber).length();
            String result = "";
            for (int i = length; i < name.length(); i++) {
                Character c = name.charAt(i);
                result += c;
            }
            houses = new Pair<>(((nodeNumber*2)-1) + result, (nodeNumber*2)+result);
            setRoadName(result);

        }catch (Exception e) {
            houses = new Pair<>("Junction", "Junction");
            setRoadName(name);
        }

    }

}
